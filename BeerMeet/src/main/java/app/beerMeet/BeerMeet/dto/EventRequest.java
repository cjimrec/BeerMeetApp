package app.beerMeet.BeerMeet.dto;

public class EventRequest {
    private String title;
    private String description;
    private String location;
    private String userEmail; // 🔥 Esto permite identificar quién lo creó

    // Constructor vacío (Spring lo necesita)
    public EventRequest() {}

    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
