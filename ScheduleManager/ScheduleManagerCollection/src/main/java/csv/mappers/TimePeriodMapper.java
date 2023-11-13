package csv.mappers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A utility class for parsing time strings and creating time ranges.
 */
public class TimePeriodMapper {

    /**
     * Parses a time string and creates a time range.
     *
     * @param input The input string representing the time range (e.g., "15:15-17").
     * @return A TimeRange object representing the parsed start and end times.
     */
    public static TimeRange parseTimeString(String input) {
        // Split the input string into start and end parts
        String[] parts = input.split("-");
        String startTimeString = parts[0];
        String endTimeString = parts[1];

        // Define a formatter for parsing time with or without minutes
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");

        // Parse the start time
        LocalTime startTime = LocalTime.parse(startTimeString, formatter);

        // Parse the end time (add default minutes if not specified)
        LocalTime endTime;
        if (endTimeString.contains(":")) {
            endTime = LocalTime.parse(endTimeString, formatter);
        } else {
            // If minutes are not specified, use 00 as the default
            endTime = LocalTime.parse(endTimeString + ":00", formatter);
        }

        return new TimeRange(startTime, endTime);
    }

    /**
     * Formats a TimeRange object to the original time range format (hh:mm-hh).
     *
     * @param timeRange The TimeRange object to be formatted.
     * @return The formatted time range string.
     */
    public static String formatTimeRange(TimeRange timeRange) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String startTimeString = timeRange.getStartTime().format(formatter);
        String endTimeString = timeRange.getEndTime().format(formatter);

        return startTimeString + "-" + endTimeString;
    }

    /**
     * A simple class to represent a time range with start and end times.
     */
    public static class TimeRange {

        private final LocalTime startTime;
        private final LocalTime endTime;

        /**
         * Constructs a TimeRange object with specified start and end times.
         *
         * @param startTime The start time of the range.
         * @param endTime   The end time of the range.
         */
        public TimeRange(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        /**
         * Gets the start time of the time range.
         *
         * @return The start time.
         */
        public LocalTime getStartTime() {
            return startTime;
        }

        /**
         * Gets the end time of the time range.
         *
         * @return The end time.
         */
        public LocalTime getEndTime() {
            return endTime;
        }
    }
}
