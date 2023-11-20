package schedule.filter;

import schedule.lecture.Lecture;

public record ProfessorFilter(String requirement) implements Filter {
	@Override
	public boolean test(Lecture lecture) {
		return lecture.getProfessor().equals(requirement);
	}
}
