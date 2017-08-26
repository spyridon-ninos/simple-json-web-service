package com.ninos.events.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Event {
	private Long id;
	private String home;
	private String away;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getAway() {
		return away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
