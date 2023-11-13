package schedule.importer.spec;


import schedule.lecture.Lecture;

import java.io.IOException;
import java.util.List;

/**
 * Interface for importing schedule data from an external file.
 */
public interface ScheduleImporter {

    /**
     * Loads schedule data from an external file.
     *
     * @param path       The path from which to load the schedule data.
     * @param configPath The path to the configuration file, if applicable.
     * @return {@code List<Lecture>} returns if the data loading is successful.
     * @throws IOException If an I/O error occurs during the data loading process.
     */
    List<Lecture> loadData(String path, String configPath) throws IOException;
}
