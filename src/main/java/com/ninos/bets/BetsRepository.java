package com.ninos.bets;

import com.ninos.bets.model.Bet;

import java.util.List;

public interface BetsRepository {
	List<Bet> getPlaced();
	List<Bet> getSettled();
	List<Bet> getCancelled();
	List<Bet> getHistory();
	Bet getById(Long id);
	Bet save(Bet bet);
	Bet cancel(Long id);
	Bet settle(Long id, boolean punterWon);
}
