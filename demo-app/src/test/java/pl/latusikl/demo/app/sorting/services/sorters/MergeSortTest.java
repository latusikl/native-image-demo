package pl.latusikl.demo.app.sorting.services.sorters;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import pl.latusikl.demo.app.sorting.services.models.SortingStrategy;

//Rewritten by ChatGPT4 based on MergeSortSpec
class MergeSortTest
{
	private MergeSort subject;

	@BeforeEach
	public void setup()
	{
		subject = new MergeSort();
	}

	@Test
	void shouldReturnProperSortingStrategy()
	{
		SortingStrategy result = subject.getSortingStrategy();
		assertEquals(SortingStrategy.MERGE_SORT, result);
	}

	static Stream<Arguments> shouldSortArrayAscendingProvider()
	{
		return Stream.of(
				Arguments.of(new int[]{ 4, 2, 1, 9, 7, 5 }, new int[]{ 1, 2, 4, 5, 7, 9 }),
				Arguments.of(new int[]{ 1 }, new int[]{ 1 }),
				Arguments.of(new int[]{ 2, 1 }, new int[]{ 1, 2 }),
				Arguments.of(new int[]{ 1, 2, 3, 4, 5 }, new int[]{ 1, 2, 3, 4, 5 }),
				Arguments.of(new int[]{ 5, 4, 3, 2, 1 }, new int[]{ 1, 2, 3, 4, 5 }),
				Arguments.of(new int[]{ 7, -10, -5, 15, 25, 2 }, new int[]{ -10, -5, 2, 7, 15, 25 }),
				Arguments.of(new int[]{ 12, 15, 66, -10, 222, 666, 111, 15, -30, 3, 6, 12 },
						new int[]{ -30, -10, 3, 6, 12, 12, 15, 15, 66, 111, 222, 666 })

		);
	}

	@ParameterizedTest
	@MethodSource("shouldSortArrayAscendingProvider")
	void shouldSortArrayAscending(int[] arrayToSort, int[] expectedResult)
	{
		int[] result = subject.sortAscending(arrayToSort);
		assertArrayEquals(expectedResult, result);
	}

	static Stream<Arguments> shouldSortArrayDescendingProvider()
	{
		return Stream.of(
				Arguments.of(new int[]{ 4, 2, 1, 9, 7, 5 }, new int[]{ 9, 7, 5, 4, 2, 1 }),
				Arguments.of(new int[]{ 1 }, new int[]{ 1 }),
				Arguments.of(new int[]{ 2, 1 }, new int[]{ 2, 1 }),
				Arguments.of(new int[]{ 1, 2, 3, 4, 5 }, new int[]{ 5, 4, 3, 2, 1 }),
				Arguments.of(new int[]{ 5, 4, 3, 2, 1 }, new int[]{ 5, 4, 3, 2, 1 }),
				Arguments.of(new int[]{ 7, -10, -5, 15, 25, 2 }, new int[]{ 25, 15, 7, 2, -5, -10 }),
				Arguments.of(new int[]{}, new int[]{})
		);
	}

	@ParameterizedTest
	@MethodSource("shouldSortArrayDescendingProvider")
	void shouldSortArrayDescending(int[] arrayToSort, int[] expectedResult)
	{
		int[] result = subject.sortDescending(arrayToSort);
		assertArrayEquals(expectedResult, result);
	}
}
