package com.ninos.bets;

import com.ninos.bets.model.Bet;
import com.ninos.bets.model.BetCancelRequest;
import com.ninos.bets.model.BetPlaceRequest;
import com.ninos.bets.model.BetsResponse;
import com.ninos.bets.model.ErrorType;
import com.ninos.bets.model.NoResultsFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ninos.bets.BetsUtils.buildErrorResponse;
import static com.ninos.bets.BetsUtils.buildErrors;
import static com.ninos.bets.BetsUtils.buildGenericBetsResponse;
import static com.ninos.bets.BetsUtils.buildNoErrorResponse;

@RestController
@RequestMapping(value = "/bets", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api
public final class BetsEndpoint {

	private static Logger logger = LoggerFactory.getLogger(BetsEndpoint.class);

	private final Bets bets;

	@Inject
	public BetsEndpoint(
		Bets bets
	) {
		this.bets = bets;
	}

	@GetMapping
	@ApiOperation(value = "get the history of all bets", response = BetsResponse.class)
	public BetsResponse getAll() {
		return buildNoErrorResponse(bets::getAll);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "get information of a single bet", response = BetsResponse.class)
	public BetsResponse getById(@PathVariable("id") Long id) {
		List<Bet> betList = new ArrayList<>();
		betList.add(bets.getById(id));
		return buildNoErrorResponse(() -> betList);
	}

	@PostMapping
	@ApiOperation(value = "place a bet", response = BetsResponse.class)
	public BetsResponse save(@RequestBody BetPlaceRequest betPlaceRequest) {
		return buildGenericBetsResponse(betPlaceRequest.toBet(), bets::save);
	}

	@DeleteMapping
	@ApiOperation(value = "cancel a bet", response = BetsResponse.class)
	public BetsResponse cancel(@RequestBody BetCancelRequest betCancelRequest) {
		return buildGenericBetsResponse(betCancelRequest.toBet(), bets::cancel);
	}

	@GetMapping(value = "/placed")
	@ApiOperation(value = "get all placed and not cancelled, not settled bets", response = BetsResponse.class)
	public BetsResponse getActive() {
		return buildNoErrorResponse(bets::getActive);
	}

	@GetMapping(value = "/cancelled")
	@ApiOperation(value = "get all cancelled bets", response = BetsResponse.class)
	public BetsResponse getCancelled() {
		return buildNoErrorResponse(bets::getCancelled);
	}

	@GetMapping(value = "/settled")
	@ApiOperation(value = "get all settled events", response = BetsResponse.class)
	public BetsResponse getSettled() {
		return buildNoErrorResponse(bets::getSettled);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BetsResponse handleIllegalArgument(IllegalArgumentException e) {
		logger.error("Received an illegal argument exception: {}", e.getMessage(), e);
		return buildErrorResponse(() -> buildErrors(ErrorType.BAD_REQUEST, e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BetsResponse handleGeneralException(Exception e) {
		String exceptionId = UUID.randomUUID().toString();
		logger.error("{} Received an exception: {}", exceptionId, e.getMessage(), e);
		return buildErrorResponse(() -> buildErrors(ErrorType.UNKNOWN_ERROR, exceptionId));
	}

	@ExceptionHandler(NoResultsFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public BetsResponse handleNoResultsFound(NoResultsFoundException e) {
		String exceptionId = UUID.randomUUID().toString();
		logger.info("{} No results found", exceptionId, e);
		return buildErrorResponse(() -> buildErrors(ErrorType.NO_RESULT_FOUND, exceptionId));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BetsResponse handleJsonParseException(HttpMessageNotReadableException e) {
		logger.error("Received an illegal argument exception: {}", e.getCause().getCause().getMessage(), e);
		return buildErrorResponse(() -> buildErrors(ErrorType.BAD_REQUEST, e.getCause().getCause().getMessage()));
	}
}
