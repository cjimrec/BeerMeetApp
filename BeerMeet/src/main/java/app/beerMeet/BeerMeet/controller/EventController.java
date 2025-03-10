package app.beerMeet.BeerMeet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import app.beerMeet.BeerMeet.entity.Event;
import app.beerMeet.BeerMeet.entity.User;
import app.beerMeet.BeerMeet.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event, @AuthenticationPrincipal User user) {
        if (user == null || !user.getIsAdmin()) {
            return ResponseEntity.status(403).body("❌ No tienes permiso para crear eventos.");
        }

        Event savedEvent = eventService.createEvent(event, user);
        return ResponseEntity.ok(savedEvent);
    }
}
