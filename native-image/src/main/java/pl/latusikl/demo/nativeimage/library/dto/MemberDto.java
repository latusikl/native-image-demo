package pl.latusikl.demo.nativeimage.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberDto implements BaseDto
{

	@NotBlank(message = "Name is required")
	private final String name;

	@NotBlank(message = "Surname is required")
	private final String surname;

	@Email(message = "Member email is invalid")
	private final String email;

	@NotBlank(message = "ID Card Number is required")
	private final String idCardKey;

}
