package schedule.filter;

import schedule.lecture.Lecture;

/**
 * A class that represents a filter based on the subject of a lecture.
 * This filter checks if the subject of the lecture contains a specified requirement.
 *
 * @param requirement the requirement to be checked against the subject of the lecture
 */
public record SubjectFilter(String requirement) implements Filter {
	@Override
	public boolean test(Lecture lecture) {
		return lecture.getSubject().toLowerCase().contains(requirement.toLowerCase());
	}
}
