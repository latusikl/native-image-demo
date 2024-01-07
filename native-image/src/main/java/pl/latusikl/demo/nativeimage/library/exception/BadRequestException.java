package pl.latusikl.demo.nativeimage.library.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractRuntimeException
{
	public BadRequestException(final String message)
	{
		super(message, HttpStatus.BAD_REQUEST);
	}
}
