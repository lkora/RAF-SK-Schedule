package schedule.filter;

import schedule.lecture.Lecture;

/**
 * Represents a filter used to filter classrooms based on the number of computers they have.
 */
public record ClassroomComputerFilter(Integer requirement) implements Filter {
	@Override
	public boolean test(Lecture lecture) {
		return lecture.getClassroom().getNoComputers() >= requirement;
	}

	@Override
	public String getName() {
		return "classroom_computer_" + requirement;
	}
}
