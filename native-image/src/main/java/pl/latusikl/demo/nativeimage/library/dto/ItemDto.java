package pl.latusikl.demo.nativeimage.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemDto implements BaseDto
{
	@NotBlank(message = "Item name is required")
	private final String name;

	@NotBlank(message = "Author name cannot be empty")
	private final String authorName;

	@NotBlank(message = "Author surname cannot be empty")
	private final String authorSurname;

	@Min(value = 1950, message = "Invalid publication year only yeas starting from 1950 are accepted.")
	@Max(value = 2100, message = "Invalid publication year only yeas starting from 1950 are accepted.")
	private final Integer publicationYear;

}
