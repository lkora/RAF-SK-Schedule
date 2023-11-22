package schedule.exclusions;

import schedule.common.ValidityPeriod;
import schedule.lecture.Lecture;

public record TestExclusion(String name, ValidityPeriod active) implements Exclusion {
	@Override
	public boolean test(Lecture lecture) {
		return lecture.getValidityPeriod().overlaps(active);
	}
}
