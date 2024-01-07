package pl.latusikl.demo.nativeimage.sorting.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pl.latusikl.demo.nativeimage.sorting.dto.SortingRequestDto;
import pl.latusikl.demo.nativeimage.sorting.dto.SortingResponseDto;
import pl.latusikl.demo.nativeimage.sorting.services.SortingService;
import pl.latusikl.demo.nativeimage.sorting.services.models.SortingDirection;


@Validated
@RestController
@RequestMapping(value = "/sorters")
@RequiredArgsConstructor
public class SortingController
{

	private final SortingService sortingService;

	@PostMapping(path = "/sort", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public SortingResponseDto sortNumbers(
			@RequestParam(name = "direction", defaultValue = "ASCENDING") final SortingDirection direction,
			@RequestBody @Valid final SortingRequestDto sortingRequestDto)
	{
		return switch (direction)
		{
			case ASCENDING -> sortingService.sortAscending(sortingRequestDto);
			case DESCENDING -> sortingService.sortDescending(sortingRequestDto);
		};
	}
}
