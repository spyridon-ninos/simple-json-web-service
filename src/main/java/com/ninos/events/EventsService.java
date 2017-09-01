package com.ninos.events;

import com.ninos.events.model.Event;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class EventsService {

	private EventsRepository eventsRepository;

	@Inject
	public EventsService(
		EventsRepository eventsRepository
	) {
		this.eventsRepository = eventsRepository;
	}

	public List<Event> getAll() {
		return eventsRepository.getAll();
	}

	public Event getById(Long id) {
		return eventsRepository.getById(id);
	}
}
