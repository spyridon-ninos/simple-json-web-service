package com.ninos.bets;

import com.ninos.bets.model.Bet;
import com.ninos.bets.model.BetsResponse;
import com.ninos.bets.model.Error;
import com.ninos.bets.model.ErrorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public final class BetsUtils {

	private BetsUtils() {
		// nothing - forbid instantiation
	}

	public static List<Error> buildErrors(ErrorType type, String message) {
		Error error = new Error();
		error.setErrorType(type);
		error.setMessage(message);

		List<Error> errorList = new ArrayList<>();
		errorList.add(error);

		return errorList;
	}

	public static BetsResponse buildNoErrorResponse(Supplier<List<Bet>> betSupplier) {
		return buildGenericBetsResponse(null, betSupplier);
	}

	public static BetsResponse buildErrorResponse(Supplier<List<Error>> errorSupplier) {
		return buildGenericBetsResponse(errorSupplier, null);
	}

	public static BetsResponse buildGenericBetsResponse(Supplier<List<Error>> errorSupplier, Supplier<List<Bet>> betSupplier) {
		BetsResponse betsResponse = new BetsResponse();
		List<Error> errorList = new ArrayList<>();
		List<Bet> betList = new ArrayList<>();

		if (errorSupplier != null) {
			errorList.addAll(errorSupplier.get());
		}

		if (betSupplier != null) {
			betList.addAll(betSupplier.get());
		}

		betsResponse.setErrors(errorList);
		betsResponse.setResponseBody(betList);

		return betsResponse;
	}

	public static BetsResponse buildGenericBetsResponse(Bet bet, Function<Bet, Bet> function) {
		BetsResponse betsResponse = new BetsResponse();
		List<Bet> betList = new ArrayList<>();

		betList.add(function.apply(bet));
		betsResponse.setResponseBody(betList);

		betsResponse.setErrors(Collections.emptyList());

		return betsResponse;
	}
}
