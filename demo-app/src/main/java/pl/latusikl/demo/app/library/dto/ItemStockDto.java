package pl.latusikl.demo.app.library.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemStockDto implements BaseDto
{
	@org.hibernate.validator.constraints.UUID(message = "Invalid format of item id")
	private final String itemId;

	@Min(value = 0, message = "Item count must be greater or equal 0")
	@Max(value = 100, message = "Item count is too large")
	private final Integer itemCount;

	private final Integer availableItemsCount;

	private final Integer borrowedItemsCount;
}
