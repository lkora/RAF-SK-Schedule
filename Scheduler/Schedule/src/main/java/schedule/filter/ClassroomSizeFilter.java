package schedule.filter;

import schedule.lecture.Lecture;

/**
 * This class represents a filter that checks if a lecture's classroom size meets a certain requirement.
 * It implements the Filter interface.
 */
public record ClassroomSizeFilter(Integer requirement) implements Filter {
	@Override
	public boolean test(Lecture lecture) {
		return lecture.getClassroom().getNoSpaces() >= requirement;
	}
}
