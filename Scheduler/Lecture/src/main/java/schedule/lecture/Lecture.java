package schedule.lecture;

import lombok.*;
import schedule.classroom.Classroom;
import schedule.lecture.type.LectureType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
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
    /**
     * The lecture subject.
     */
    private String subject;
    /**
     * The lecture type.
     */
    private LectureType type;
    /**
     * The professor that holds the lecture.
     */
    private String professor;
    /**
     * The groups that attend the lecture.
     */
    private Set<String> groups;
    /**
     * The day of the week when the lecture is held.
     */
    private DayOfWeek day;
    /**
     * The start time of the lecture.
     */
    private LocalTime start;
    /**
     * The end time of the lecture.
     */
    private LocalTime end;
    /**
     * The classroom where the lecture is held.
     */

    private Classroom classroom;

    /**
     * Wither method for the 'start' and 'end' fields.
     */
    public Lecture withTimes(LocalTime start, LocalTime end) {
        return new Lecture(
                this.subject,
                this.type,
                this.professor,
                new HashSet<>(this.groups), // create a new set from existing to maintain immutability
                this.day,
                start,
                end,
                this.classroom
        );
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
        return Objects.equals(subject, that.subject) &&
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
        return Objects.hash(subject, type, professor, day, classroom, start, end);
    }


    /**
     * Returns a string representation of the Lecture object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Lecture{" +
                "name='" + subject + '\'' +
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
