package schedule.manager.service.provided.csv.mappers;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class DayMapper {

    private static final Map<String, DayOfWeek> ABBREVIATION_TO_DAY_MAP = new HashMap<>();
    private static final Map<DayOfWeek, String> DAY_TO_ABBREVIATION_MAP = new HashMap<>();

    static {
        ABBREVIATION_TO_DAY_MAP.put("PON", DayOfWeek.MONDAY);
        ABBREVIATION_TO_DAY_MAP.put("UTO", DayOfWeek.TUESDAY);
        ABBREVIATION_TO_DAY_MAP.put("SRE", DayOfWeek.WEDNESDAY);
        ABBREVIATION_TO_DAY_MAP.put("ČET", DayOfWeek.THURSDAY);
        ABBREVIATION_TO_DAY_MAP.put("PET", DayOfWeek.FRIDAY);
        ABBREVIATION_TO_DAY_MAP.put("SUB", DayOfWeek.SATURDAY);
        ABBREVIATION_TO_DAY_MAP.put("NED", DayOfWeek.SUNDAY);

        for (Map.Entry<String, DayOfWeek> entry : ABBREVIATION_TO_DAY_MAP.entrySet()) {
            DAY_TO_ABBREVIATION_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * Maps a day abbreviation to the corresponding DayOfWeek value.
     *
     * @param abbreviation The day abbreviation (e.g., "PON").
     * @return The corresponding DayOfWeek value.
     * @throws IllegalArgumentException if the abbreviation is not recognized.
     */
    public static DayOfWeek mapToDayOfWeek(String abbreviation) {
        DayOfWeek dayOfWeek = ABBREVIATION_TO_DAY_MAP.get(abbreviation);
        if (dayOfWeek == null) {
            throw new IllegalArgumentException("Unknown day abbreviation: " + abbreviation);
        }
        return dayOfWeek;
    }

    /**
     * Maps a DayOfWeek value to its corresponding day abbreviation.
     *
     * @param dayOfWeek The DayOfWeek value.
     * @return The corresponding day abbreviation.
     * @throws IllegalArgumentException if the DayOfWeek value is not recognized.
     */
    public static String mapToAbbreviation(DayOfWeek dayOfWeek) {
        String abbreviation = DAY_TO_ABBREVIATION_MAP.get(dayOfWeek);
        if (abbreviation == null) {
            throw new IllegalArgumentException("Unknown DayOfWeek value: " + dayOfWeek);
        }
        return abbreviation;
    }
}
