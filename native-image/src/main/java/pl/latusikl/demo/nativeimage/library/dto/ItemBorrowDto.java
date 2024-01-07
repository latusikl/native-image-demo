package pl.latusikl.demo.nativeimage.library.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemBorrowDto implements BaseDto
{
	@org.hibernate.validator.constraints.UUID(message = "Invalid format of member id")
	private final String memberId;

	@org.hibernate.validator.constraints.UUID(message = "Invalid format of member id")
	private final String itemId;

	private final LocalDate startDate;

	private final LocalDate endDate;

}
