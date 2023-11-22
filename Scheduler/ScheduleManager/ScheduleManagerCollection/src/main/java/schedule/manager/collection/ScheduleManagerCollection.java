package schedule.manager.collection;

import schedule.common.ValidityPeriod;
import schedule.lecture.Lecture;
import schedule.manager.ScheduleManager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ScheduleManagerCollection is a subclass of ScheduleManager that overrides the loadSchedule method.
 * It adds functionality to sort the lectures and copy lectures for multiple days.
 */
public class ScheduleManagerCollection extends ScheduleManager {
	@Override
	public List<Lecture> loadSchedule(String path, String configPath) throws Exception {
		List<Lecture> lectures = super.loadSchedule(path, configPath);
		return copyLectures(lectures);
	}

	private List<Lecture> copyLectures(List<Lecture> lectures) {
		var from = validityPeriod.validFrom();
		var to = validityPeriod.validTo();

		return IntStream.iterate(0, i -> i + 1)
				.limit(ChronoUnit.DAYS.between(from, to))
				.mapToObj(from::plusDays)
				.flatMap(date -> lectures.stream()
						.filter(lecture -> lecture.getDay().equals(date.getDayOfWeek()))
						.map(lecture -> copyLecture(lecture, date)))
				.collect(Collectors.toList());
	}
	private Lecture copyLecture(Lecture lecture, LocalDate date) {
		return new Lecture(
				lecture.getSubject(),
				lecture.getType(),
				lecture.getProfessor(),
				new HashSet<>(lecture.getGroups()),
				lecture.getDay(),
				lecture.getStart(),
				lecture.getEnd(),
				new ValidityPeriod(date, date.plusDays(1)),
				lecture.getClassroom()
		);
	}
}
