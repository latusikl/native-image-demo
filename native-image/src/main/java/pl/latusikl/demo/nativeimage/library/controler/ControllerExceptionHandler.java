package pl.latusikl.demo.nativeimage.library.controler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import pl.latusikl.demo.nativeimage.library.dto.ErrorResponseDto;
import pl.latusikl.demo.nativeimage.library.dto.ValidationErrorDto;
import pl.latusikl.demo.nativeimage.exception.AbstractRuntimeException;

@ControllerAdvice
public class ControllerExceptionHandler
{
	private static final String PARAM_ERROR_MSG = "Error occurred during parameter validation";
	private static final String SEE_VALIDATION_ERRORS_MSG = "Check the validation errors for concrete reason";
	private static final String INVALID_ARG_TYPE_MSG = "Specified parameter has invalid value";

	private static final String VALIDATION_ERROR_MSG = "Specified request has validation error/errors";
	private static final Function<ObjectError, ValidationErrorDto> OBJECT_ERROR_MAPPER = objectError -> {
		final String errorMsg = objectError.getDefaultMessage();
		final String fieldWithError = ((FieldError) objectError).getField();
		return new ValidationErrorDto(fieldWithError, errorMsg);
	};

	private static final Function<ConstraintViolation<?>, ValidationErrorDto> CONSTRAINT_ERROR_MAPPER = constraintViolation -> {
		final String errorMsg = constraintViolation.getMessage();
		final String fieldWithError = constraintViolation.getPropertyPath().toString();
		return new ValidationErrorDto(fieldWithError, errorMsg);
	};

	@ExceptionHandler(AbstractRuntimeException.class)
	public ResponseEntity<ErrorResponseDto> handleLocationNotFound(final AbstractRuntimeException exception)
	{
		final var errorBody = ErrorResponseDto.builder()
		                                      .message(exception.getMessage())
		                                      .additionalInformation(exception.getAdditionalInformation())
		                                      .serverTime(LocalDateTime.now())
		                                      .build();

		return ResponseEntity.status(exception.getHttpStatus())
		                     .body(errorBody);
	}


	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponseDto> handleValidationException(final MethodArgumentNotValidException exception)
	{
		final List<ValidationErrorDto> validationErrors = exception.getBindingResult()
		                                                           .getAllErrors()
		                                                           .stream()
		                                                           .map(OBJECT_ERROR_MAPPER)
		                                                           .toList();

		final var errorBody = ErrorResponseDto.builder()
		                                      .message(PARAM_ERROR_MSG)
		                                      .additionalInformation(SEE_VALIDATION_ERRORS_MSG)
		                                      .serverTime(LocalDateTime.now())
		                                      .validationErrors(validationErrors)
		                                      .build();

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
		                     .body(errorBody);
	}

	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponseDto> handleValidationException(final MethodArgumentTypeMismatchException exception)
	{
		final var errorBody = ErrorResponseDto.builder()
		                                      .message(INVALID_ARG_TYPE_MSG)
		                                      .build();

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
		                     .body(errorBody);
	}


	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ErrorResponseDto> handleConstraintViolationException(final ConstraintViolationException e)
	{
		final List<ValidationErrorDto> validationErrors = e.getConstraintViolations().stream()
		                                                   .map(CONSTRAINT_ERROR_MAPPER)
		                                                   .toList();
		final var errorBody = ErrorResponseDto.builder()
		                                      .message(VALIDATION_ERROR_MSG)
		                                      .additionalInformation(SEE_VALIDATION_ERRORS_MSG)
		                                      .serverTime(LocalDateTime.now())
		                                      .validationErrors(validationErrors)
		                                      .build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                     .body(errorBody);
	}

}
