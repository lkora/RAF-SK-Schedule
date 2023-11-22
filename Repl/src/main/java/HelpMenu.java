public enum HelpMenu {
    START,
    AVAILABLE_COMMANDS;


    @Override
    public String toString() {
        switch (this) {
            case START -> {
                return "This is a REPL applet implemented for scheduler testing.\n" +
                        "Entered commands should follow this format <command> <argument1> <argument2>...";
            }
            case AVAILABLE_COMMANDS -> {
                StringBuilder sb = new StringBuilder();
                sb.append("Available commands:\n");
                for (Command command : Command.values()) {
                    sb.append(command).append("\n");
                }
                return sb.toString();
            }

            default -> {
                return "";
            }
        }
    }

    private enum Command {
        IMPORT,
        EXPORT,
        FILTER_ADD,
        FILTER_REMOVE,
        HELP,
        PRINT,
        EXCLUDE_ADD,
        EXCLUDE_REMOVE,
        QUIT;

        @Override
        public String toString() {
            switch (this) {
                case IMPORT -> {
                    return "\t- i <pathToSchedule> <pathToClassroomDetails> <scheduleConfigPath>? - Imports a schedule from a file\n" +
                            "\t\t\t <pathToSchedule> - File path to the schedule\n" +
                            "\t\t\t <pathToClassroomDetails> - File path to classroom amenities configuration JSON file. File content structure example:\n" +
                            "\t\t\t\t{\n" +
                            "\t\t\t\t        \"classroom\": \"Raf10 (a)\",\n" +
                            "\t\t\t\t        \"projector\": false,\n" +
                            "\t\t\t\t        \"no_spaces\": 23,\n" +
                            "\t\t\t\t        \"computers\": 47\n" +
                            "\t\t\t\t}\n" +
                            "\t\t\t <scheduleConfigPath>? - OPTIONAL - Custom mapping config for reading CSV fields in format '<index> <CSVFiledName> <validLectureFieldName>'";
                }
                case EXPORT -> {
                    return "\t- e <pathToOutputFile> - Output file path, expects .json or .csv file to write to.";
                }
                case FILTER_ADD -> {
                    return "\t- f add <filterType> <argument> - Adds a search filter which will modify the lecture list. List of supported filters:\n" +
                            "\t\t\t f add group <groupName> - Filters lectures by group name - # creates filter: group_<groupName>\n" +
                            "\t\t\t f add professor <professorName> - Filters lectures held by the provided professor - # creates filter: professor_<professorName>";
                }

                case FILTER_REMOVE -> {
                    return "\t- f remove <filterType>_<argument> - Removes the filter from of type <filterType> and with the argument <argument>\n" +
                            "\t\tf remove * - Removes all filters";
                }

                case EXCLUDE_ADD -> {
                    return "\t- x add <name> <pathToExclusionsFile> - Add exclusions file to the schedule";
                }

                case EXCLUDE_REMOVE -> {
                    return "\t- x remove <name> - Remove the exclusion list saved under the <name>";
                }

                case HELP -> {
                    return "\t- h - Prints all available options and their usage";
                }

                case PRINT -> {
                    return "\t- p - Prints the schedule with added filters and exclusions";
                }

                case QUIT -> {
                    return "\t- q - Quits the program";
                }
                default -> {
                    return "";
                }

            }
        }
    }
}
