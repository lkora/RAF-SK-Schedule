package schedule.lecture;

import schedule.lecture.type.LectureType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a lecture in a schedule.
 */
public class Lecture {
    private String name;
    private LectureType type;
    private String professor;
    private Set<String> groups;
    private DayOfWeek day;
    private LocalTime start;
    private LocalTime end;
    private String classroom;

    /**
     * Constructs a Lecture object with additional information.
     *
     * @param name       The name of the lecture.
     * @param type       The type of the lecture (e.g., lecture, lab).
     * @param professor  The professor teaching the lecture.
     * @param groups     The set of groups associated with the lecture.
     * @param day        The day of the week on which the lecture occurs.
     * @param start      The start time of the lecture.
     * @param end        The end time of the lecture.
     * @param classroom  The classroom where the lecture takes place.
     */
    public Lecture(String name, LectureType type, String professor, Set<String> groups, DayOfWeek day, LocalTime start, LocalTime end, String classroom) {
        this.name = name;
        this.type = type;
        this.professor = professor;
        this.groups = groups;
        this.day = day;
        this.start = start;
        this.end = end;
        this.classroom = classroom;
    }

    /**
     * Constructs an empty Lecture
     */
    public Lecture() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LectureType getType() {
        return type;
    }

    public void setType(LectureType type) {
        this.type = type;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    /**
     * Checks if two Lecture objects are equal.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture that = (Lecture) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(professor, that.professor) &&
                Objects.equals(day, that.day) &&
                Objects.equals(classroom, that.classroom) &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }

    /**
     * Generates a hash code for the Lecture object.
     *
     * @return The hash code for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, type, professor, day, classroom, start, end);
    }


    /**
     * Returns a string representation of the Lecture object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Lecture{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", professor='" + professor + '\'' +
                ", groups=" + groups +
                ", day=" + day +
                ", start=" + start +
                ", end=" + end +
                ", classroom='" + classroom +
                '}';
    }
}
