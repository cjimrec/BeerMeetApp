package app.beerMeet.BeerMeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "app.beerMeet.BeerMeet")
public class BeerMeetApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeerMeetApplication.class, args);
    }
}
