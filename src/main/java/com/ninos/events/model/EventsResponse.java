package com.ninos.events.model;

import com.ninos.bets.model.Response;

import java.util.List;

public class EventsResponse extends Response<List<Event>> {
	private List<Event> events;

	public List<Event> getResponseBody() {
		return events;
	}

	public void setResponseBody(List<Event> events) {
		this.events = events;
	}
}
