package schedule;

import schedule.manager.ScheduleManager;
import schedule.lecture.Lecture;

import java.util.ArrayList;
import java.util.List;

// TODO: Load exceptions
// TODO: Add sorting functionalities

/**
 * This class represents a schedule of lectures.
 */
public class Schedule {

    /**
     * A list of lectures in the schedule.
     */
    private List<Lecture> lectureList = new ArrayList<>();

    /**
     * The manager responsible for loading and writing the schedule.
     */
    private ScheduleManager manager;

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
     * @param path The path to the schedule file.
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
     * Returns the list of lectures in the schedule.
     *
     * @return A list of lectures.
     */
    public List<Lecture> getLectureList() {
        return lectureList;
    }
}
