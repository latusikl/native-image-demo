package pl.latusikl.demo.nativeimage.library.dto;

import lombok.Value;

@Value
public class ValidationErrorDto
{
	private String object;
	private String error;
}
