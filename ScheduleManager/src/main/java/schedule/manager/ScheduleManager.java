package schedule.manager;

import csv.ScheduleManagerCSV;
import json.ScheduleManagerJSON;
import schedule.lecture.Lecture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScheduleManager {

    public ScheduleManager() {
    }

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

}

