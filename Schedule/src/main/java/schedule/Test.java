package schedule;

public class Test {


    public static void main(String[] args) {
        Schedule schedule = new Schedule();

        // READ CSV -> WRITE JSON -> READ JSON -> WRITE CSV
        try {
            schedule.loadSchedule("Schedule/src/main/resources/collection/csv/schedule.csv",
                    "Schedule/src/main/resources/collection/csv/mapping.cfg");

            schedule.exportSchedule("Schedule/src/main/resources/collection/csv/schedule_export.json");

            schedule.loadSchedule("Schedule/src/main/resources/collection/csv/schedule_export.json", null);

            schedule.exportSchedule("Schedule/src/main/resources/collection/csv/schedule_export.csv");
        } catch (Exception e) {
            System.out.println("Something wrong happened!" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
