package schedule.lecture;

import lombok.*;
import schedule.classroom.Classroom;
import schedule.common.ValidityPeriod;
import schedule.lecture.type.LectureType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a lecture in a schedule.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
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
	 * Represents the validity period of a Lecture.
	 * It specifies the start and end dates of the period for which the lecture is valid.
	 * I.e., for how long a lecture is held.
	 */
	private ValidityPeriod validityPeriod;

	/**
	 * The classroom where the lecture is held.
	 */
	private Classroom classroom;

	/**
	 * Returns a new Lecture object with the start and end times updated.
	 * Used for moving lectures to a different time slot.
	 * @param start the new start time for the Lecture
	 * @param end   the new end time for the Lecture
	 * @return a new Lecture object with updated start and end times
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
				this.validityPeriod,
				this.classroom);
	}

}
