package schedule.filter;

import schedule.lecture.Lecture;

/**
 * A class that implements the Filter interface to filter lectures based on the classroom name requirement.
 */
public record ClassroomNameFilter(String requirement) implements Filter {
	@Override
	public boolean test(Lecture lecture) {
		return lecture.getClassroom().getName().toLowerCase().contains(requirement.toLowerCase());
	}
}
