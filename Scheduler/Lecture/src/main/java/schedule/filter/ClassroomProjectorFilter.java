package schedule.filter;

import schedule.lecture.Lecture;

/**
 * A {@code ClassroomProjectorFilter} represents a filter that checks if a {@code Lecture} has a classroom with a projector based on a requirement.
 * It implements the {@code Filter} interface and overrides its {@code test} method.
 */
public record ClassroomProjectorFilter(String requirement) implements Filter {
	@Override
	public boolean test(Lecture lecture) {
		return lecture.getClassroom().hasProjector() == Boolean.parseBoolean(requirement);
	}

	@Override
	public String getName() {
		return "classroom_projector_" + requirement;
	}
}
