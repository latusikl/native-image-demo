package pl.latusikl.demo.nativeimage.sorting.services.sorters;

import org.springframework.stereotype.Component;

import pl.latusikl.demo.nativeimage.sorting.services.models.SortingStrategy;
import pl.latusikl.demo.nativeimage.sorting.services.sorters.heap.MaxIntHeap;


@Component
public class HeapSort implements Sorter
{

	@Override
	public SortingStrategy getSortingStrategy()
	{
		return SortingStrategy.HEAP_SORT;
	}

	@Override
	public int[] sortAscending(final int[] array)
	{
		final var heap = createHeap(array);
		final var heapSize = heap.getHeapSize();
		for (int i = heapSize - 1; i >= 0; i--)
		{
			array[i] = heap.pop();
		}
		return array;
	}

	@Override
	public int[] sortDescending(final int[] array)
	{
		final var heap = createHeap(array);
		final var heapSize = heap.getHeapSize();
		for (int i = 0; i < heapSize; i++)
		{
			array[i] = heap.pop();
		}
		return array;
	}

	private MaxIntHeap createHeap(final int[] array)
	{
		final var heap = new MaxIntHeap();
		for (final var elem : array)
		{
			heap.add(elem);
		}
		return heap;
	}

}
