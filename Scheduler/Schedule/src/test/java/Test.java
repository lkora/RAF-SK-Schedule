import schedule.Schedule;
import schedule.filter.GroupFilter;

/**

 The Test class is the entry point of the program.
 It demonstrates the usage of the Schedule class to read and manipulate schedules.

 Usage:
 1. Create a new instance of the Schedule class.
 2. Use the loadSchedule() method to load a schedule from a CSV file, along with a mapping configuration file.
 3. Optionally, create a GroupFilter object to filter the schedule by a specific group.
 4. Sort the schedule using the sort() method.
 5. Add the filter to the schedule using the addFilter() method.
 6. Use the filtered() method to obtain a filtered view of the schedule.
 7. Iterate over the filtered schedule and print each lecture using the forEach() method.

 Example:

 public static void main(String[] args) {
 Schedule schedule = new Schedule();
 try {
 schedule.loadSchedule("Schedule/src/main/resources/collection/csv/schedule.csv",
 "Schedule/src/main/resources/collection/csv/mapping.cfg");
 GroupFilter filter = new GroupFilter("306");
 schedule.sort();
 schedule.addFilter(filter);
 schedule.filtered().forEach(System.out::println);
 } catch (Exception e) {
 e.printStackTrace();
 }
 }

 Note: The example code has been commented out to prevent accidental execution.

 */
public class Test {


    /**
     * The main method of the program.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
	    Schedule schedule = new Schedule();
        // READ CSV -> WRITE JSON -> READ JSON -> WRITE CSV
        try {
            schedule.loadClassroomAmenities("Schedule/src/test/resources/classrooms.json");
            schedule.loadSchedule("Schedule/src/test/resources/csv/schedule.csv",
                    "Schedule/src/test/resources/csv/mapping.cfg");
            GroupFilter filter = new GroupFilter("306");
            schedule.sort();
            schedule.addFilter(filter);
            schedule.filtered().forEach(System.out::println);
            schedule.exportSchedule("Schedule/src/test/resources/csv/schedule_export.pdf");
//            schedule.getLectures().forEach(System.out::println);
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
