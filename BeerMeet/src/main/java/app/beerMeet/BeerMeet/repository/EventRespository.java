package app.beerMeet.BeerMeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.beerMeet.BeerMeet.entity.Event;
import app.beerMeet.BeerMeet.entity.User;

public interface EventRespository extends JpaRepository<Event, Long>{
	List<Event> findByUser(User user);
}
