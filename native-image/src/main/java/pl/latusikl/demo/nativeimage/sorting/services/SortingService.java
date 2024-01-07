package pl.latusikl.demo.nativeimage.sorting.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import pl.latusikl.demo.nativeimage.sorting.dto.SortingRequestDto;
import pl.latusikl.demo.nativeimage.sorting.dto.SortingResponseDto;
import pl.latusikl.demo.nativeimage.sorting.services.models.SortingStrategy;
import pl.latusikl.demo.nativeimage.sorting.services.sorters.Sorter;

@Service
public class SortingService
{
	private final Map<SortingStrategy, Sorter> sortersMap;

	public SortingService(final List<Sorter> sorters)
	{
		sortersMap = new EnumMap<>(SortingStrategy.class);
		sorters.forEach(sorter -> sortersMap.put(sorter.getSortingStrategy(), sorter));
	}

	public SortingResponseDto sortAscending(final SortingRequestDto sortingRequestDto)
	{
		final int[] sortedArray = sortAscending(sortingRequestDto.getStrategy(), toPrimitive(sortingRequestDto.getData()));
		return new SortingResponseDto(toCollection(sortedArray));
	}

	public SortingResponseDto sortDescending(final SortingRequestDto sortingRequestDto)
	{
		final int[] sortedArray = sortDescending(sortingRequestDto.getStrategy(), toPrimitive(sortingRequestDto.getData()));
		return new SortingResponseDto(toCollection(sortedArray));
	}

	private int[] toPrimitive(final Collection<Integer> data)
	{
		return data.stream()
		           .filter(Objects::nonNull)
		           .mapToInt(Integer::intValue)
		           .toArray();
	}

	private Collection<Integer> toCollection(int[] array)
	{
		return Arrays.stream(array)
		             .boxed()
		             .toList();
	}


	private int[] sortAscending(final SortingStrategy sortingStrategy, final int[] data)
	{
		return sortersMap.get(sortingStrategy)
		                 .sortAscending(data);
	}

	private int[] sortDescending(final SortingStrategy sortingStrategy, final int[] data)
	{
		return sortersMap.get(sortingStrategy)
		                 .sortDescending(data);
	}

}
