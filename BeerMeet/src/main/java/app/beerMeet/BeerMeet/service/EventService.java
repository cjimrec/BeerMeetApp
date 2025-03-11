package app.beerMeet.BeerMeet.service;

import app.beerMeet.BeerMeet.entity.Event;
import app.beerMeet.BeerMeet.entity.User;
import app.beerMeet.BeerMeet.repository.EventRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    
    @Autowired
    private EventRespository eventRepository;

    public Event createEvent(Event event, User user) {
        if (user == null || !user.getIsAdmin()) {
            throw new RuntimeException("❌ No tienes permiso para crear eventos.");
        }
        event.setUser(user);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventsByUser(User user) {
        return eventRepository.findByUser(user);
    }

    public Event updateEvent(Long id, Event updatedEvent, User user) {
        if (user == null || !user.getIsAdmin()) {
            throw new RuntimeException("❌ No tienes permiso para actualizar eventos.");
        }

        Optional<Event> existingEventOpt = eventRepository.findById(id);
        if (existingEventOpt.isPresent()) {
            Event existingEvent = existingEventOpt.get();
            existingEvent.setTitle(updatedEvent.getTitle());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setLocation(updatedEvent.getLocation());
            return eventRepository.save(existingEvent);
        }
        throw new RuntimeException("❌ Evento no encontrado con ID: " + id);
    }

    public void deleteEvent(Long id, User user) {
        if (user == null || !user.getIsAdmin()) {
            throw new RuntimeException("❌ No tienes permiso para eliminar eventos.");
        }

        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new RuntimeException("❌ Evento no encontrado con ID: " + id);
        }
    }
}