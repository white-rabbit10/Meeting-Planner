import java.util.ArrayList;
import java.util.List;

public class Calender {
    public List<Event> events;
    public int startTime;
    public int endTime;

    public int getStartTime() {
        return this.startTime;
    }
    public int getEndTime() {
        return this.endTime;
    }
    
    public Calender(int startTimem, int endTime) {
        this.startTime = startTimem;
        this.endTime = endTime;
        events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public int timeLeft() {
        int totalDuration = 0;
        for(Event event: events) {
            totalDuration += event.getDuration();
        }

        return (endTime - startTime) - totalDuration;
    }

    public List<Event> getEventsInDuration(int startTime, int endTime) {
        List<Event> eventInDuration = new ArrayList<>();
        for(Event event: events) {
            int eventStartTime = startTime + eventInDuration.stream().mapToInt(Event :: getDuration).sum();
            int eventEndTime = eventStartTime + event.getDuration();
            if(eventStartTime >= startTime && eventEndTime <= endTime) {
                eventInDuration.add(event);
            }
        }
        return eventInDuration;
    }

    // 8AM(s) - 5PM(e)
}
