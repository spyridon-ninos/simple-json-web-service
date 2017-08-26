package com.ninos.bets;

import com.ninos.bets.model.Bet;
import com.ninos.bets.model.NoResultsFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Supplier;

@Component
public class Bets {

	private static Logger logger = LoggerFactory.getLogger(Bets.class);

	private SettlementEngine settlementEngine;
	private BetsRepository betsRepository;

	@Inject
	public Bets(
		SettlementEngine settlementEngine,
		BetsRepository betsRepository
	) {
		this.settlementEngine = settlementEngine;
		this.betsRepository = betsRepository;
	}

	public List<Bet> getAll() {
		return getBets(betsRepository::getHistory);
	}

	public Bet getById(Long id) {
		return betsRepository.getById(id);
	}

	public Bet save(Bet bet) {
		Bet savedBet = betsRepository.save(bet);
		settlementEngine.schedule(savedBet.getId());
		return savedBet;
	}

	public Bet cancel(Bet bet) {
		return betsRepository.cancel(bet.getId());
	}

	public List<Bet> getCancelled() {
		return getBets(betsRepository::getCancelled);
	}

	public List<Bet> getSettled() {
		return getBets(betsRepository::getSettled);
	}

	public List<Bet> getActive() {
		return getBets(betsRepository::getPlaced);
	}

	private List<Bet> getBets(Supplier<List<Bet>> betSupplier) {
		List<Bet> betList = betSupplier.get();

		if (betList.isEmpty()) {
			throw new NoResultsFoundException();
		}

		return betList;
	}
}
