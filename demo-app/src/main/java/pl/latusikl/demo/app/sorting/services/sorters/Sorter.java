package pl.latusikl.demo.app.sorting.services.sorters;


import pl.latusikl.demo.app.sorting.services.models.SortingStrategy;

public interface Sorter
{

	SortingStrategy getSortingStrategy();

	int[] sortAscending(final int[] array);

	int[] sortDescending(final int[] array);

}
