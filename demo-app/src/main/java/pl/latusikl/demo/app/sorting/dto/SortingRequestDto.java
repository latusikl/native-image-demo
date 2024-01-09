package pl.latusikl.demo.app.sorting.dto;

import java.util.Collection;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.latusikl.demo.app.sorting.services.models.SortingStrategy;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class SortingRequestDto
{
	@NotNull
	private final Collection<Integer> data;
	@NotNull
	private final SortingStrategy strategy;
}
