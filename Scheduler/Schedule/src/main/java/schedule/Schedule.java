package schedule;

import lombok.Getter;
import lombok.Setter;
import schedule.classroom.Classroom;
import schedule.common.ValidityPeriod;
import schedule.exclusions.Exclusion;
import schedule.filter.Filter;
import schedule.lecture.Lecture;
import schedule.manager.ScheduleManager;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: Load exceptions
// TODO: Add sorting functionalities

/**
 * This class represents a schedule of lectures.
 * Expected usage:
 * <pre>
 *     Schedule schedule = new Schedule();
 *     schedule.initializeValidityPeriod("path/to/validity/period.properties");
 *     schedule.loadClassroomAmenities("path/to/classroom/amenities.json");
 *     schedule.loadSchedule("path/to/schedule.csv", "path/to/config.json");
 * </pre>
 * <p>
 * <p>
 * }
 */
public class Schedule {

	/**
	 * A list of filters to apply to the schedule.
	 */
	private final List<Filter> filters = new ArrayList<>();

	/**
	 * A list of exclusions for the schedule.
	 */
	private final List<Exclusion> exclusions = new ArrayList<>();

	/**
	 * The manager responsible for loading and writing the schedule.
	 */
	private final ScheduleManager manager;
	/**
	 * A list of lectures in the schedule.
	 */
	@Getter
	private List<Lecture> lectures = new ArrayList<>();
	/**
	 * The validity period of the schedule.
	 */
	@Getter
	@Setter
	private ValidityPeriod validityPeriod;

	/**
	 * Constructor for the Schedule class.
	 * Initializes the default ScheduleManager.
	 */
	public Schedule() {
		manager = new ScheduleManager();
	}

	/**
	 * Constructs a Schedule object with the specified ScheduleManager.
	 *
	 * @param manager the ScheduleManager to associate with this Schedule
	 */
	public Schedule(ScheduleManager manager) {
		this.manager = manager;
	}

	/**
	 * Sets the validity period for this Schedule.
	 *
	 * @param validityPeriod the validity period to be set for the Schedule
	 */
	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
		manager.setValidityPeriod(validityPeriod);
	}

	/**
	 * Loads the schedule from a given path and configuration.
	 *
	 * @param path   The path to the schedule file.
	 * @param config The configuration for loading the schedule.
	 * @throws Exception If an error occurs while loading the schedule.
	 * @apiNote The call to {@code loadClassroomAmenities} must be made before calling this method.
	 */
	public void loadSchedule(String path, String config) throws Exception {
		lectures = manager.loadSchedule(path, config);
	}

	/**
	 * Loads Classroom configurations from an external file
	 *
	 * @param path The path for classroom details JSON file, this file should be configured as following:
	 *             {
	 *             "classroom": "Raf10 (a)",
	 *             "projector": false,
	 *             "no_spaces": 23,
	 *             "computers": 47
	 *             }
	 * @throws IOException If an I/O error occurs during the data loading process.
	 */
	public void loadClassroomAmenities(String path) throws Exception {
		manager.initializeClassrooms(path);
	}

	/**
	 * Initializes the validity period for classroom reservations from an external file
	 *
	 * @param path The path for the validity period .properties file, this file should be configured as following:
	 *             validity-period.start=2021-03-01
	 *             validity-period.end=2021-06-30
	 * @throws IOException If an I/O error occurs during the data loading process.
	 */
	public void initializeValidityPeriod(String path) throws Exception {
		validityPeriod = manager.initializeValidityPeriod(path);
	}

	/**
	 * Exports the current schedule to a given path.
	 *
	 * @param path The path where the schedule should be written.
	 * @throws Exception If an error occurs while writing the schedule.
	 */
	public void exportSchedule(String path) throws Exception {
		manager.writeSchedule(lectures, path);
	}

	/**
	 * Exports the filtered schedule to a given path.
	 *
	 * @param path The path where the filtered schedule should be written.
	 * @throws Exception If an error occurs while writing the schedule.
	 */
	public void exportFilteredSchedule(String path) throws Exception {
		manager.writeSchedule(filtered(), path);
	}


	/**
	 * Adds a filter to the list of filters.
	 *
	 * @param filter The filter to be added.
	 */
	public void addFilter(Filter filter) {
		filters.add(filter);
	}


	/**
	 * Removes a filter from the list of filters.
	 *
	 * @param filter The filter to remove.
	 */
	public void removeFilter(Filter filter) {
		filters.remove(filter);
	}

	/**
	 * Removes all filters from the collection.
	 */
	public void clearFilters() {
		filters.clear();
	}

	/**
	 * Returns a string representing all the filter names in the collection.
	 * Each filter name is separated by a newline character.
	 *
	 * @return a string representing all the filter names
	 */
	public String getFiltersString() {
		return filters.stream().map(Filter::getName).collect(Collectors.joining("\n"));
	}

	/**
	 * @return A list of lectures filtered by the current filters.
	 */
	public List<Lecture> filtered() {
		return lectures.stream().filter(filters.stream().reduce(Filter::and).orElse(x -> true)).toList();
	}

	/**
	 * Adds an exclusion to the current list of exclusions.
	 *
	 * @param exclusion The exclusion to be added. Cannot be null.
	 */
	public void addExclusion(Exclusion exclusion) {
		exclusions.add(exclusion);
	}

	/**
	 * Removes an exclusion from the current list of exclusions.
	 *
	 * @param exclusion The exclusion to be removed. Cannot be null.
	 */
	public void removeExclusion(Exclusion exclusion) {
		exclusions.remove(exclusion);
	}

	/**
	 * Clears the current list of exclusions.
	 */
	public void clearExclusions() {
		exclusions.clear();
	}

	/**
	 * Checks if the given lecture is excluded based on the current list of exclusions.
	 *
	 * @param lecture The lecture to check for exclusion.
	 * @return True if the lecture is excluded, false otherwise.
	 */
	public boolean isExcluded(Lecture lecture) {
		return exclusions.stream().anyMatch(exclusion -> exclusion.test(lecture));
	}

	/**
	 * Adds a lecture to the current list of lectures if there is a free spot available.
	 *
	 * @param lecture The lecture to add.
	 * @return True if the lecture is successfully added, false otherwise.
	 */
	public boolean addLecture(Lecture lecture) {
		Objects.requireNonNull(lecture, "Lecture cannot be null");

		if (!hasFreeSpot(lecture.getValidityPeriod(), lecture.getClassroom())) {
			return false;
		}

		return lectures.add(lecture);
	}

	/**
	 * Removes the given lecture from the list of lectures.
	 *
	 * @param lecture The lecture to be removed.
	 * @return True if the lecture was successfully removed, false otherwise.
	 */
	public boolean removeLecture(Lecture lecture) {
		// Validate input
		Objects.requireNonNull(lecture, "Lecture cannot be null");

		return lectures.remove(lecture);
	}


	/**
	 * Moves the given lecture to a different time slot.
	 * Note: The lecture must already be in the schedule.
	 *
	 * @param lecture The lecture to be moved.
	 * @param start   New start time of the lecture
	 * @param end     New end time of the lecture
	 * @return True if the lecture is successfully moved.
	 * @throws IllegalArgumentException If the lecture is not in schedule
	 */
	public boolean moveLecture(Lecture lecture, LocalTime start, LocalTime end) {
		// Validate input
		Objects.requireNonNull(lecture, "Lecture cannot be null");
		if (!lectures.contains(lecture)) {
			throw new IllegalArgumentException("Lecture not in schedule");
		}
		// Remove the old lecture and add the updated lecture
		if (removeLecture(lecture)) {
			Lecture updatedLecture = lecture.withTimes(start, end);
			return addLecture(updatedLecture);
		}
		return false;
	}

	private boolean hasFreeSpot(ValidityPeriod validityPeriod, Classroom classroom) {
		return lectures.stream().filter(lecture -> lecture.getClassroom().equals(classroom)).noneMatch(lecture -> lecture.getValidityPeriod().overlaps(validityPeriod));
	}

	/**
	 * Sorts the lectures in the schedule.
	 */
	public void sort() {
		manager.sort(lectures);
	}
}
