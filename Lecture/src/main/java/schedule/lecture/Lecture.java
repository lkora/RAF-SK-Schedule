package schedule.lecture;

import lombok.*;
import schedule.classroom.Classroom;
import schedule.lecture.type.LectureType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a lecture in a schedule.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Lecture {
    private String name;
    private LectureType type;
    private String professor;
    private Set<String> groups;
    private DayOfWeek day;
    private LocalTime start;
    private LocalTime end;
    private Classroom classroom;


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
