package schedule.manager;

import schedule.classroom.Classroom;
import schedule.classroom.ClassroomRegistry;
import schedule.lecture.Lecture;
import schedule.manager.service.provided.csv.ScheduleManagerCSV;
import schedule.manager.service.provided.json.ScheduleManagerJSON;
import schedule.manager.service.provided.json.mappers.ClassroomMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * The ScheduleManager class is responsible for loading, writing, and manipulating schedules.
 */
public class ScheduleManager {

    /**
     * Creates a new instance of ScheduleManager.
     */
    public ScheduleManager() {
    }

    /**
     * Loads a schedule from a file and returns a list of lectures.
     *
     * @param path The path of the file to load the schedule from.
     * @param configPath The path of the configuration file.
     * @return A list of lectures representing the loaded schedule.
     * @throws FileNotFoundException If the file specified by the path does not exist.
     * @throws Exception If an error occurs while loading the schedule.
     */
    public List<Lecture> loadSchedule(String path, String configPath) throws Exception {
        File file = new File(path);
        if (!file.exists()) { throw new FileNotFoundException(); }

        Optional<String> fileTypeExtension = getExtensionByStringHandling(file.getName());
        if (fileTypeExtension.isPresent()) {
            FileType fileType = getSupportedFileType(fileTypeExtension.get());
            switch (fileType) {
                case CSV -> {
                    return new ScheduleManagerCSV().loadData(path, configPath);
                }
                case JSON -> {
                    return new ScheduleManagerJSON().loadData(path, configPath);
                }
            }
        }

        return new ArrayList<>();
    }

    /**
     * Loads Classroom configurations from an external file
     *
     * @param path The path for classroom details JSON file, this file should be configured as following:
     *     {
     *         "classroom": "Raf10 (a)",
     *         "projector": false,
     *         "no_spaces": 23,
     *         "computers": 47
     *     }
     * @throws IOException If an I/O error occurs during the data loading process.
     */
    public void initializeClassrooms(String path) throws IOException {
        List<Classroom> classrooms;
        var mapper = new ClassroomMapper();
        classrooms = new ArrayList<>(mapper.getClassrooms(path));

        ClassroomRegistry.initialize(classrooms);
    }


    /**
     * Writes the schedule of lectures to an external file in either CSV or JSON format.
     * The file format is determined based on the extension of the file name.
     *
     * @param lectures The list of lectures to be exported to the file.
     * @param path The path for the output file.
     * @throws Exception If an error occurs during the file writing process.
     */
    public void writeSchedule(List<Lecture> lectures, String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Could not create file");
            }
        }

        Optional<String> fileTypeExtension = getExtensionByStringHandling(file.getName());
        if (fileTypeExtension.isPresent()) {
            FileType fileType = getSupportedFileType(fileTypeExtension.get());
            switch (fileType) {
                case CSV -> new ScheduleManagerCSV().exportData(lectures, path);
                case JSON -> new ScheduleManagerJSON().exportData(lectures, path);
            }
        }

    }

    private Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private FileType getSupportedFileType(String fileType) throws Exception {
        switch(fileType.toLowerCase()) {
            case "csv" -> { return FileType.CSV; }
            case "json" -> { return FileType.JSON; }
            default -> throw new FileTypeNotSupportedException("File Type is not supported. File needs to end in either .csv or .json");
        }
    }


    /**
     * @param lectureList The list of lectures to sort.
     */
    public void sort(List<Lecture> lectureList) {
        lectureList.sort(new Sorter());
    }

    private static class Sorter implements Comparator<Lecture> {
        @Override
        public int compare(Lecture first, Lecture second) {
            if (first == second)
                return 0;
            if (first == null)
                return -1;
            if (second == null)
                return 1;
            if (first.getDay() != second.getDay()) {
	            return first.getDay().compareTo(second.getDay());
            }
            if (first.getStart().equals(second.getStart())) {
	            return first.getEnd().compareTo(second.getEnd());
            }
            return first.getStart().compareTo(second.getStart());
        }
    }
}

