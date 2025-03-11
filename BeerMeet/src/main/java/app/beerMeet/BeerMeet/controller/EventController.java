package app.beerMeet.BeerMeet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import app.beerMeet.BeerMeet.dto.EventRequest;
import app.beerMeet.BeerMeet.entity.Event;
import app.beerMeet.BeerMeet.repository.EventRespository;
import app.beerMeet.BeerMeet.service.EventService;
import app.beerMeet.BeerMeet.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final EventRespository eventRepository;

    public EventController(EventService eventService, UserService userService, EventRespository eventRepository) {
        this.eventService = eventService;
        this.userService = userService;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventRequest request, Authentication authentication) {
        String userEmail = authentication.getName(); // Obtiene el email autenticado
        System.out.println("✅ Usuario autenticado intentando crear evento: " + userEmail);

        // 🚨 Bypass: Si el usuario es "admin@beermeet.com", siempre puede crear eventos
        if (userEmail.equals("admin@beermeet.com")) {
            System.out.println("🔓 Usuario admin@beermeet.com autorizado para crear evento.");

            Event event = new Event();
            event.setTitle(request.getTitle());
            event.setDescription(request.getDescription());
            event.setLocation(request.getLocation());

            eventRepository.save(event);
            return ResponseEntity.ok(event);
        }

        // ❌ Si no es admin@beermeet.com, se bloquea la creación de eventos
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("❌ No tienes permiso para crear eventos.");
    }
}


