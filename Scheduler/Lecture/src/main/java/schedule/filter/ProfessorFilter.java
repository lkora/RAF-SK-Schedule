package schedule.filter;

import schedule.lecture.Lecture;

/**
 * Represents a filter that checks if a lecture's professor matches a requirement.
 * <p>
 *     Returns true if the lecture's professor contains the requirement.
 * <p>
 * Implements the Filter interface.
 */
public record ProfessorFilter(String requirement) implements Filter {
	@Override
	public boolean test(Lecture lecture) {
		return lecture.getProfessor().toLowerCase().contains(requirement.toLowerCase());
	}
	@Override
	public String getName() {
		return "professor_" + requirement;
	}
}
