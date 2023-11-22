package schedule.exclusions;

import schedule.lecture.Lecture;

import java.time.LocalDate;

/**
 * A record representing a holiday exclusion.
 * This class implements the Exclusion interface.
 */
public record HolidayExclusion(String name, LocalDate date) implements Exclusion {
	public static ExclusionType getType() {
		return ExclusionType.HOLIDAY;
	}

	@Override
	public boolean test(Lecture lecture) {
		return lecture.getValidityPeriod().dateIsIn(date);
	}

}
