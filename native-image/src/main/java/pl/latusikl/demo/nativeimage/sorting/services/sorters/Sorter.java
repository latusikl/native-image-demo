package pl.latusikl.demo.nativeimage.sorting.services.sorters;


import pl.latusikl.demo.nativeimage.sorting.services.models.SortingStrategy;

public interface Sorter
{

	SortingStrategy getSortingStrategy();

	int[] sortAscending(final int[] array);

	int[] sortDescending(final int[] array);

}
