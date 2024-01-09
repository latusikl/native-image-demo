package pl.latusikl.demo.app.library.dto;

import lombok.Value;

@Value
public class ValidationErrorDto
{
	private String object;
	private String error;
}
