import schedule.Schedule;
import schedule.filter.Filter;
import schedule.manager.collection.ScheduleManagerCollection;
import schedule.manager.weekly.ScheduleManagerWeekly;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final ScheduleManagerCollection collection = new ScheduleManagerCollection();
    private final ScheduleManagerWeekly weekly = new ScheduleManagerWeekly();
    private Schedule schedule = null;
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main mainInstance = new Main();

        mainInstance.printInitialHelp();
        mainInstance.initHandle();
        mainInstance.handleCommands();
    }

    private void printInitialHelp() {
        System.out.println(HelpMenu.START);
        System.out.println(HelpMenu.AVAILABLE_COMMANDS);
        System.out.println(HelpMenu.INIT);
    }

    private void initHandle() {
        System.out.print("#> ");
        String input = scanner.nextLine();
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        initSwitch(command);
    }

    private void initSwitch(String command) {
        switch (command) {
            case "weekly":
                schedule = new Schedule(weekly);
                break;
            case "collection":
                schedule = new Schedule(collection);
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private void handleCommands() {
        String input;
        System.out.print("#> ");
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            if ("q".equals(input)) {
                System.out.println("Exiting...");
                break;
            }
            eval(input);
            System.out.print("#> ");
        }
        scanner.close();
    }

    private void eval(String input) {
        input = input.trim().replaceAll("\\s+", " ");
        String[] parts = input.split(" ", 4);
        String command = parts[0].toLowerCase();
        String argument1 = parts.length > 1 ? parts[1] : null;
        String argument2 = parts.length > 2 ? parts[2] : null;
        String argument3 = parts.length > 3 ? parts[3] : null;

        switch (command) {
            case "i":
                this.schedule = new Schedule();
                importSchedule(argument1, argument2, argument3);
                break;
            case "e":
                exportSchedule(argument1);
                break;
            case "f":
                if ("add".equals(argument1)) {
                    addFilter(argument2, argument3);
                } else if ("remove".equals(argument1)) {
                    removeFilter(argument2);
                }
                break;
            case "x":
                if ("add".equals(argument1)) {
                    addExclusion(argument2, argument3);
                } else if ("remove".equals(argument1)) {
                    removeExclusion(argument2);
                }
                break;
            case "h":
                printHelp();
                break;
            case "p":
                printSchedule();
                break;
            case "q":
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }

    private void removeExclusion(String name) {
        // TODO: Implement exclusion removal by exclusion name, schedule should have a hashmap of exclusion names with the corresponding exclusions
    }

    private void addExclusion(String name, String exclusionsPath) {

    }
    private Optional<Filter> createFilter(String filterName, String requirement) {
        return Optional.of(Filter.of(filterName, requirement));
    }


    private void addFilter(String filterType, String filter) {
        if (!checkParams(filterType, filter)) {
            return;
        }

        Optional<Filter> newFilter = createFilter(filterType, filter);
	    newFilter.ifPresent(value -> schedule.addFilter(value));
    }

    private void removeFilter(String filterName) {
        if (!checkParams(filterName)) {
            return;
        }

        if (filterName.equals("*")) {
            System.out.println("Removed all filters!");
            schedule.clearFilters();
            return;
        }

        // TODO: CHECK FILTERING IMPLEMENTATION
        String[] filterConstruct = filterName.trim().split("_");
        if (filterConstruct.length == 2) {
            String filterType = filterConstruct[0];
            String filterRequirement = filterConstruct[1];

            Optional<Filter> filterToRemove = createFilter(filterType, filterRequirement);
            if (filterToRemove.isPresent()) {
                schedule.removeFilter(filterToRemove.get());
                System.out.println("Successfully removed the filter for: " + filterType + " " + filterRequirement);
            } else {
                System.err.println("Failed to remove the filter with: " + filterType + " " + filterRequirement);
            }
        } else {
            System.err.println("Bad filter formatting! Filter needs to be of format '<filterType>_<argument>'");
        }
    }



    private void importSchedule(String pathToSchedule, String pathToClassroomDetails, String scheduleConfigPath) {
        if (!checkParams(pathToSchedule, pathToClassroomDetails)) {
            return;
        }
        this.schedule = new Schedule();
        try {
            schedule.loadClassroomAmenities(pathToClassroomDetails);
            schedule.loadSchedule(pathToSchedule, scheduleConfigPath);

            System.out.println("Import successful!");
        } catch (Exception e) {
            System.err.println("Failed importing schedule with: " + e.getLocalizedMessage());
        }
    }

    private void exportSchedule(String exportPath) {
        if (!checkParams(exportPath)) {
            return;
        }
        try {
            schedule.exportSchedule(exportPath);
            System.out.println("Exported successfully to: " + exportPath);
        } catch (Exception e) {
            System.err.println("Failed exporting schedule with: " + e.getLocalizedMessage());
        }
    }

    private void printHelp() {
        System.out.println(HelpMenu.AVAILABLE_COMMANDS);
    }

    private void printSchedule() {
        System.out.println("----- Schedule ----- ");
        schedule.filtered().forEach(System.out::println);
        System.out.println("-------------------- ");
    }

    private boolean checkParams(String... args) {
        Arrays.stream(args).forEach(System.out::println);
        for (String arg : args) {
            if (arg == null) {
                System.err.println("Arguments cannot be null, check help 'h' for usage!");
                return false;
            }
        }
        return true;
    }
}

