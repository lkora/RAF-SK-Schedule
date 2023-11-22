import schedule.Schedule;
import schedule.classroom.Classroom;
import schedule.common.ValidityPeriod;
import schedule.filter.GroupFilter;
import schedule.lecture.Lecture;
import schedule.lecture.type.LectureType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

/**
 * The Test class is the entry point of the program.
 * It demonstrates the usage of the Schedule class to read and manipulate schedules.
 * <p>
 * Usage:
 * 1. Create a new instance of the Schedule class.
 * 2. Use the loadSchedule() method to load a schedule from a CSV file, along with a mapping configuration file.
 * 3. Optionally, create a GroupFilter object to filter the schedule by a specific group.
 * 4. Sort the schedule using the sort() method.
 * 5. Add the filter to the schedule using the addFilter() method.
 * 6. Use the filtered() method to obtain a filtered view of the schedule.
 * 7. Iterate over the filtered schedule and print each lecture using the forEach() method.
 * <p>
 * Example:
 * <p>
 * public static void main(String[] args) {
 * Schedule schedule = new Schedule();
 * try {
 * schedule.loadSchedule("Schedule/src/main/resources/collection/csv/schedule.csv",
 * "Schedule/src/main/resources/collection/csv/mapping.cfg");
 * GroupFilter filter = new GroupFilter("306");
 * schedule.sort();
 * schedule.addFilter(filter);
 * schedule.filtered().forEach(System.out::println);
 * } catch (Exception e) {
 * e.printStackTrace();
 * }
 * }
 * <p>
 * Note: The example code has been commented out to prevent accidental execution.
 */
public class Test {


    /**
     * The main method of the program.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
//        var manager = new ScheduleManagerWeekly();
//        Schedule schedule = new Schedule(manager);
        Schedule schedule = new Schedule();
        schedule.setValidityPeriod(new ValidityPeriod(LocalDate.now().minusDays(30), LocalDate.now().plusDays(140)));
        // READ CSV -> WRITE JSON -> READ JSON -> WRITE CSV
        try {
            schedule.loadClassroomAmenities("Schedule/src/test/resources/classrooms.json");
            schedule.loadSchedule("Schedule/src/test/resources/csv/schedule.csv", "Schedule/src/test/resources/csv/mapping.cfg");
            GroupFilter filter = new GroupFilter("306");

            var list = List.of("306", "303", "302a");
            Lecture first = new Lecture("Example", LectureType.OTHER, "N J Redzic", new HashSet<>(list), DayOfWeek.MONDAY, LocalTime.of(0, 15), LocalTime.of(8, 0), new ValidityPeriod(LocalDate.of(2023, 11, 21), LocalDate.of(2023, 12, 31)), Classroom.forName("Raf03 (u)").orElseThrow());
            System.out.println(schedule.addLecture(first));
            Lecture second = new Lecture("Example", LectureType.OTHER, "N J Redzic", new HashSet<>(list), DayOfWeek.MONDAY, LocalTime.of(7, 15), LocalTime.of(8, 59), new ValidityPeriod(LocalDate.of(2023, 12, 21), LocalDate.of(2023, 12, 31)), Classroom.forName("Raf03 (u)").orElseThrow());
            System.out.println(first);
            System.out.println(second);
            System.out.println(schedule.addLecture(second));
            schedule.sort();
            schedule.addFilter(filter);
            schedule.filtered().forEach(System.out::println);
//            manager.writeSchedule(schedule.filtered(), "Schedule/src/test/resources/csv/schedule_export_306.pdf");
            schedule.exportSchedule("Schedule/src/test/resources/csv/schedule_export.pdf");
            schedule.exportSchedule("Schedule/src/test/resources/csv/schedule_export.json");

//            schedule.getLectures().forEFilterach(System.out::println);
//            schedule.exportSchedule("Schedule/src/main/resources/collection/csv/schedule_export.json");
//
//            schedule.loadSchedule("Schedule/src/main/resources/collection/csv/schedule_export.json", null);
//            schedule.getLectures().forEach(System.err::println);
//            schedule.exportSchedule("Schedule/src/main/resources/collection/csv/schedule_export.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
