package pl.latusikl.demo.nativeimage.sorting.dto;

import java.util.Collection;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class SortingResponseDto
{
	private final Collection<Integer> sortedData;
}
