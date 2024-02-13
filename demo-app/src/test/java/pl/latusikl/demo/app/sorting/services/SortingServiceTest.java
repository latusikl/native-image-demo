package pl.latusikl.demo.app.sorting.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import pl.latusikl.demo.app.sorting.dto.SortingRequestDto;
import pl.latusikl.demo.app.sorting.dto.SortingResponseDto;
import pl.latusikl.demo.app.sorting.services.models.SortingStrategy;
import pl.latusikl.demo.app.sorting.services.sorters.Sorter;

@DisabledInNativeImage
class SortingServiceTest
{

	private final Sorter heapSort = mock(Sorter.class);
	private final Sorter quickSort = mock(Sorter.class);
	private final Sorter mergeSort = mock(Sorter.class);

	private SortingService subject;

	@BeforeEach
	void setup()
	{
		when(heapSort.getSortingStrategy()).thenReturn(SortingStrategy.HEAP_SORT);
		when(quickSort.getSortingStrategy()).thenReturn(SortingStrategy.QUICKSORT);
		when(mergeSort.getSortingStrategy()).thenReturn(SortingStrategy.MERGE_SORT);

		subject = new SortingService(List.of(heapSort, mergeSort, quickSort));
	}

	static Stream<Arguments> shouldUseProperSortingStrategyToSortAscendingProvider()
	{
		return Stream.of(
				Arguments.of(SortingStrategy.MERGE_SORT, new int[]{ 1, 0, 0 }),
				Arguments.of(SortingStrategy.QUICKSORT, new int[]{ 0, 1, 0 }),
				Arguments.of(SortingStrategy.HEAP_SORT, new int[]{ 0, 0, 1 })
		);
	}

	@ParameterizedTest
	@MethodSource("shouldUseProperSortingStrategyToSortAscendingProvider")
	void shouldUseProperSortingStrategyToSortAscending(SortingStrategy sortingStrategy, int[] sorterCalls)
	{
		int[] arrayToSort = new int[]{ 8, 4, 5, 6 };
		List<Integer> arrayToSortBoxed = List.of(8, 4, 5, 6);
		int[] sortedArray = new int[]{ 4, 5, 6, 8 };
		List<Integer> sortedArrayBoxed = List.of(4, 5, 6, 8);
		SortingRequestDto sortingRequest = new SortingRequestDto(arrayToSortBoxed, sortingStrategy);

		when(mergeSort.sortAscending(arrayToSort)).thenReturn(sortedArray);
		when(quickSort.sortAscending(arrayToSort)).thenReturn(sortedArray);
		when(heapSort.sortAscending(arrayToSort)).thenReturn(sortedArray);

		SortingResponseDto sortingResponseDto = subject.sortAscending(sortingRequest);

		assertEquals(sortedArrayBoxed, sortingResponseDto.getSortedData());

		verify(mergeSort, times(sorterCalls[0])).sortAscending(arrayToSort);
		verify(quickSort, times(sorterCalls[1])).sortAscending(arrayToSort);
		verify(heapSort, times(sorterCalls[2])).sortAscending(arrayToSort);
	}
}
