import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;
/*
 * Create the Student and Priorities classes here.
 */

enum Event {
    ENTER, SERVED
} 
 
class Student {
    private int _id;
    private String _name;
    private double _cgpa;
    public Student(int id, String name, double cgpa) {
        this._id = id;
        this._name = name;
        this._cgpa = cgpa;
    }
    
    public int getID() {
        return _id;
    }
    
    public String getName() {
        return _name;
    }
    
    public double getCGPA() {
        return _cgpa;
    }
}

class Priorities {
    public List<Student> getStudents(List<String> events) {
        PriorityQueue<Student> students = new PriorityQueue<Student>(events.size(), new StudentComparator());
        for (String event : events) {
            String[] eventsArr = event.split(" ");
            Event tipEvent = Event.valueOf(eventsArr[0]);
            if(tipEvent.equals(Event.SERVED))
                students.poll();
            else {
                students.add(new Student(Integer.parseInt(eventsArr[3]), eventsArr[1], Double.parseDouble(eventsArr[2])));
            }
        }
        ArrayList<Student> studentsList = new ArrayList<Student>(students);
        /*for (Student st : studentsList) {
            System.out.println(st.getName() + " " + st.getCGPA());
        }*/
        Collections.sort(studentsList, new StudentComparator());
        return studentsList;
    }
}

class StudentComparator implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        //System.out.println(s1.getName() + " " + s1.getCGPA() + " vs " + s2.getName() + " " + s2.getCGPA());
        if(s1.getCGPA()>s2.getCGPA())
            return -1;//descending order
        if(s2.getCGPA()>s1.getCGPA())
            return 1;//descending order
        if(s1.getName().equals(s2.getName())) {
            return s1.getID()>s2.getID()?1:-1;
        }
        return s1.getName().compareTo(s2.getName());
    }
}


public class Solution {
    private final static Scanner scan = new Scanner(System.in);
    private final static Priorities priorities = new Priorities();
    
    public static void main(String[] args) {
        int totalEvents = Integer.parseInt(scan.nextLine());    
        List<String> events = new ArrayList<>();
        
        while (totalEvents-- != 0) {
            String event = scan.nextLine();
            events.add(event);
        }
        
        List<Student> students = priorities.getStudents(events);
        
        if (students.isEmpty()) {
            System.out.println("EMPTY");
        } else {
            for (Student st: students) {
                System.out.println(st.getName());
            }
        }
    }
}