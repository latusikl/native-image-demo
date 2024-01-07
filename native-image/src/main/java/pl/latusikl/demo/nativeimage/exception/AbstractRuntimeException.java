package pl.latusikl.demo.nativeimage.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class AbstractRuntimeException extends RuntimeException
{

	private static final String EMPTY = "";
	private final HttpStatus httpStatus;
	private final String additionalInformation;

	public AbstractRuntimeException(final String message, final HttpStatus httpStatus, final String additionalInformation)
	{
		super(message);
		this.httpStatus = httpStatus;
		this.additionalInformation = additionalInformation;
	}

	public AbstractRuntimeException(final String message, final HttpStatus httpStatus)
	{
		super(message);
		this.httpStatus = httpStatus;
		this.additionalInformation = EMPTY;
	}
}
