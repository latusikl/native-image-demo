package pl.latusikl.demo.app.sorting.services.sorters.heap;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

//Rewritten by ChatGPT4 based on MaxIntHeapSpec
class MaxIntHeapTest
{

	private MaxIntHeap subject =new MaxIntHeap();


	@Test
	void shouldReturnProperHeapSize()
	{
		List<Integer> elementsToAdd = Arrays.asList(1, 5, 7, 9);
		int expectedSize = elementsToAdd.size();

		elementsToAdd.forEach(item -> subject.add(item));
		int heapSize = subject.getHeapSize();

		assertEquals(heapSize, expectedSize);
	}

	@Test
	void shouldAddElementsToHeapAndStoreThemInProperOrder()
	{
		List<Integer> heapElements = Arrays.asList(10, 20, 11, 7, -5, 50);

		heapElements.forEach(item -> subject.add(item));

		int[] subjectItems = subject.getItems();
		assertEquals(50,subjectItems[0]);
		assertEquals(10,subjectItems[1]);
		assertEquals(20,subjectItems[2]);
		assertEquals(7,subjectItems[3]);
		assertEquals(-5,subjectItems[4]);
		assertEquals(11,subjectItems[5]);
	}

	@Test
	void shouldAlwaysPopTheBiggestElementInTheHeap()
	{
		List<Integer> heapElements = Arrays.asList(200, 100, 500);

		heapElements.forEach(item -> subject.add(item));

		int[] results = new int[]{ subject.pop(), subject.pop(), subject.pop() };

		assertArrayEquals(new int[]{ 500, 200, 100 }, results);
	}
}
