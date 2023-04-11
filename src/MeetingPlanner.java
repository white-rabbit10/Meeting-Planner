import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MeetingPlanner {

    public static final int WORK_HOURS_PER_DAY = 9;
    public static final int LUNCH = 1;
    public static final int COPORATE_EVENT_LIMIT_TO_PERSONAL = 2;
    public static final int WORK_START = 8;
    public static final int WORK_END = 17;
    public static final int LUNCH_START = 12;
    public static final int LUNCH_END = 13;

    Event[][] corporateCalender = new Event[WORK_HOURS_PER_DAY][];
    Event[][] personalCalender = new Event[WORK_HOURS_PER_DAY][];
    List<Event> declinedList = new ArrayList<>();



    public void scheduleMeeting() {
        Scanner scanner = new Scanner(System.in);
        List<Event> events = new ArrayList<>();

        while(true) {
            String input = scanner.nextLine();
            if(input.isEmpty()) {
                break;
            }
            String[] parts = input.split(",");
            String subject = parts[0];
            String type = parts[1].trim();
            // if(parts[1] == Type.Corporate.name()) 
            //     type = Type.Corporate;
            // else type = Type.Personal;
            int duration = Integer.parseInt(parts[2].trim());
            int priority = Integer.parseInt(parts[3].trim());
            Event event = new Event(subject, type, duration, priority);
            events.add(event);
        }
        // events.sort(Comparator.comparing(Event::getPriority).reversed()
        //     .thenComparing(Event::getDuration).reversed());

        for(int i=0;i<WORK_HOURS_PER_DAY;i++) {
            corporateCalender[i] = new Event[0];
            personalCalender[i] = new Event[0];
        }


        planMeeting(events);

        System.out.println("Coporate");
        printCalender(corporateCalender);
        System.out.println("Personal");
        printCalender(personalCalender);
        System.out.println("Declined List");
        for(Event event: declinedList) {
            System.out.println(event.getSubject());
        }
    }

    private void printCalender(Event[][] calender) {
        for(int i=0;i<WORK_HOURS_PER_DAY;i++) {
            System.out.println(String.format("%02d:00 - %02d:00: ", i+8,  i + 1 + 8));
            if(i < LUNCH) {
                System.out.println("       ");
            }
            for(Event event : calender[i]) {
                System.out.println(event.getSubject() + " ");
            }
            System.out.println();
        }
    }

    public void planMeeting(List<Event> events) {

        for(Event event: events) {
            // System.out.println("Event type = " + event.getType());
            if(event.getType().equals("Corporate")) {
                if(!fitEventIntoCalender(event, corporateCalender)) {
                    // System.out.println("Hello");
                    if(!fitEventIntoCalender(event, personalCalender, COPORATE_EVENT_LIMIT_TO_PERSONAL)) {
                        // System.out.println("Helo srishti");
                        declinedList.add(event);
                    }
                }
            } else if(event.getType().equals("Personal")){
                if(!fitEventIntoCalender(event, personalCalender)) {
                    declinedList.add(event);
                }
            } else {
                System.out.println(event.getSubject());
            }
        }
    }

    public boolean fitEventIntoCalender(Event event, Event[][] calender) {
        for(int i=0;i<WORK_HOURS_PER_DAY;i++) {
            if(canFitEventAt(event, calender, i)) {
                // System.out.println("Hello hello hellos");
                addToCalender(event, calender, i);
                return true;
            }
        }
        return false;
    }

    public boolean fitEventIntoCalender(Event event, Event[][] calender, int personalEventLimit) {
        int personalEventCount = getPersonalEventCount(calender);
        // if(personalEventCount >= personalEventLimit) {
        //     return false;
        // }
        for(int i=0;i<WORK_HOURS_PER_DAY;i++) {
            if(canFitEventAt(event, calender, i)) {
                addToCalender(event, calender, i);
                return true;
            }
        }
        return false;

    }

    private int getPersonalEventCount(Event[][] calender) {
        int count = 0;
        for(int i=0;i<WORK_HOURS_PER_DAY;i++) {
            for (Event event: calender[i]) {
                if(event.getType() == "Personal") {
                    count++;
                }
            }
        }
        System.out.println("Count = " + count);
        return count;
    }

    public boolean canFitEventAt(Event event, Event[][] calender, int hour) {
        if(hour + event.getDuration()/60 > WORK_HOURS_PER_DAY) {
            return false;
        }
        for(int i=hour;i<hour + event.getDuration()/60;i++) {
            if(calender[i].length > 0) {
                return false;
            }
        }
        return true;
    }

    private void addToCalender(Event event, Event[][] calender, int hour) {
        for(int i=hour;i<(hour + event.getDuration())/60;i++) {
            calender[i] = addEventToArray(calender[i], event);
        }
    }

    private Event[] addEventToArray(Event[] events, Event event) {
        Event[] newEvents = Arrays.copyOf(events, events.length + 1);
        newEvents[newEvents.length - 1] = event;
        return newEvents;
    }

}

