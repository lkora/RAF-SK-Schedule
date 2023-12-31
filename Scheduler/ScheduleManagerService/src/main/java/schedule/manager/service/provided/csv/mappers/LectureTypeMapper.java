package schedule.manager.service.provided.csv.mappers;

import schedule.lecture.type.LectureType;

/**
 * The LectureTypeMapper class provides methods to map between string representation of a lecture type
 * and corresponding LectureType enum value, and vice versa.
 */
public class LectureTypeMapper {

    /**
     * Maps a string representation of a lecture type to the corresponding LectureType enum value.
     *
     * @param typeString The string representation of the lecture type.
     * @return The corresponding LectureType enum value.
     * @throws IllegalArgumentException if the typeString is not recognized.
     */
    public static LectureType mapToLectureType(String typeString) {
        return switch (typeString) {
            case "V" -> LectureType.SEMINAR;
            case "P" -> LectureType.LECTURE;
            default -> LectureType.OTHER;
        };
    }

    // TODO: Improve mapping back other types to string (maybe save a map instead of mapping them straight to ENUM)
    /**
     * Converts a LectureType enum value to its corresponding string representation.
     *
     * @param lectureType The LectureType enum value to be converted.
     * @return The string representation of the LectureType:
     *         - "V" for SEMINAR,
     *         - "P" for LECTURE,
     *         - "D" for OTHER (default).
     */
    public static String lectureTypeToString(LectureType lectureType) {
	    return switch (lectureType) {
		    case SEMINAR -> "V";
		    case LECTURE -> "P";
		    default -> "D";
	    };
    }
}
