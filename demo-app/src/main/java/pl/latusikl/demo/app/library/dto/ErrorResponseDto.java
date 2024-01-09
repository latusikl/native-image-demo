package pl.latusikl.demo.app.library.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Value;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Value
@Builder
public class ErrorResponseDto
{
	private String message;
	private String additionalInformation;
	private LocalDateTime serverTime;
	private List<ValidationErrorDto> validationErrors;
}
