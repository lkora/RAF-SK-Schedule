package schedule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {


    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        // READ CSV -> WRITE JSON -> READ JSON -> WRITE CSV
        try {
            schedule.loadSchedule("Schedule/src/main/resources/collection/csv/schedule.csv",
                    "Schedule/src/main/resources/collection/csv/mapping.cfg");
            schedule.getLectureList().forEach(System.out::println);
            schedule.exportSchedule("Schedule/src/main/resources/collection/csv/schedule_export.json");

            schedule.loadSchedule("Schedule/src/main/resources/collection/csv/schedule_export.json", null);
            schedule.getLectureList().forEach(System.err::println);
            schedule.exportSchedule("Schedule/src/main/resources/collection/csv/schedule_export.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
