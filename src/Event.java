public class Event {
    public String subject;
    public String type;
    public int duration;
    public int priority;

    public Event(String subject, String type, int duration, int priority) {
        this.subject = subject;
        this.type = type;
        this.duration = duration;
        this.priority = priority;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getType() {
        return this.type;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getPriority() {
        return this.priority;
    }

}
