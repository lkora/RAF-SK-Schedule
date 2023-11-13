package schedule.exporter.spec;

import schedule.lecture.Lecture;

import java.io.IOException;
import java.util.List;

/**
 * Interface for exporting schedule data to an external file.
 */
public interface ScheduleExporter {

    /**
     * Exports schedule data to an external file.
     *
     * @param lectures List of lectures whose data will be saved.
     * @param path     The path where the exported data will be saved.
     * @return {@code true} if the export is successful, {@code false} otherwise.
     * @throws IOException If an I/O error occurs during the export process.
     */
    boolean exportData(List<Lecture> lectures, String path) throws IOException;
}
