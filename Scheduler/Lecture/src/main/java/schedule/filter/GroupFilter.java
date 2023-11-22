package schedule.filter;

import schedule.lecture.Lecture;

/**
 * Filter for lectures by group.
 */
public record GroupFilter(String requirement)  implements Filter {

	@Override
	public boolean test(Lecture lecture) {
		return lecture.getGroups().contains(requirement);
	}

	@Override
	public String getName() {
		return "group_" + requirement;
	}
}