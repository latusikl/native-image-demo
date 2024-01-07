package pl.latusikl.demo.nativeimage.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends AbstractRuntimeException
{
	public InternalServerErrorException(final String message)
	{
		super(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}