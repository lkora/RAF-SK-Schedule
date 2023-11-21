package schedule;

import lombok.Getter;
import schedule.filter.Filter;
import schedule.lecture.Lecture;
import schedule.manager.ScheduleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

// TODO: Load exceptions
// TODO: Add sorting functionalities

/**
 * This class represents a schedule of lectures.
 */
public class Schedule {

	/**
	 * A list of filters to apply to the schedule.
	 */
	private final List<Filter> filters = new ArrayList<>();
	/**
	 * The manager responsible for loading and writing the schedule.
	 */
	private final ScheduleManager manager;
	/**
	 * A list of lectures in the schedule.
	 */
	@Getter
	private List<Lecture> lectureList = new ArrayList<>();
	/**
	 * Constructor for the Schedule class.
	 * Initializes the ScheduleManager.
	 */
	public Schedule() {
		manager = new ScheduleManager();
	}

	/**
	 * Loads the schedule from a given path and configuration.
	 *
	 * @param path   The path to the schedule file.
	 * @param config The configuration for loading the schedule.
	 * @throws Exception If an error occurs while loading the schedule.
	 */
	public void loadSchedule(String path, String config) throws Exception {
		lectureList = manager.loadSchedule(path, config);
	}

	/**
	 * Exports the current schedule to a given path.
	 *
	 * @param path The path where the schedule should be written.
	 * @throws Exception If an error occurs while writing the schedule.
	 */
	public void exportSchedule(String path) throws Exception {
		manager.writeSchedule(lectureList, path);
	}

	/**
	 * @param filter The filter to add.
	 */
	public void addFilter(Filter filter) {
		filters.add(filter);
	}

	/**
	 * @param filter The filter to remove.
	 */
	public void removeFilter(Filter filter) {
		filters.remove(filter);
	}
	public void clearFilters() {
		filters.clear();
	}

	/**
	 * @return A list of lectures filtered by the current filters.
	 */
	public List<Lecture> filtered() {
		return lectureList.stream().filter(filters.stream().reduce(Filter::and).orElse(x -> true)).toList();
	}

	/**
	 * Sorts the lectures in the schedule.
	 */
	public void sort() {
		manager.sort(lectureList);
	}
}
