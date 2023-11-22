package schedule.manager.service.provided.json.mappers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import schedule.classroom.Classroom;
import schedule.common.ValidityPeriod;
import schedule.lecture.Lecture;
import schedule.lecture.type.LectureType;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A class responsible for deserializing a JSON representation of a Lecture object.
 * <p>
 * This class is a subclass of {@code JsonDeserializer<Lecture>} and overrides the deserialize
 * method to perform the deserialization process.
 */
public class LectureDeserializer extends JsonDeserializer<Lecture> {
	/**
	 * Deserialize JSON representation of a Lecture object.
	 *
	 * @param jp      JSON parser used to read the JSON representation
	 * @param context deserialization context
	 * @return deserialized Lecture object
	 * @throws IOException if an I/O error occurs during deserialization
	 */
	@Override
	public Lecture deserialize(JsonParser jp, DeserializationContext context) throws IOException {
		JsonNode node = jp.getCodec().readTree(jp);
		Lecture lecture = new Lecture();

		lecture.setSubject(node.get("Predmet").asText());
		lecture.setType(LectureType.valueOf(node.get("Tip").asText()));
		lecture.setProfessor(node.get("Nastavnik").asText());
		lecture.setGroups(new HashSet<>(Arrays.asList(node.get("Grupe").asText().split(","))));
		lecture.setDay(DayOfWeek.valueOf(node.get("Dan").asText()));
		String[] times = node.get("Termin").asText().split("-");
		lecture.setStart(LocalTime.parse(times[0]));
		lecture.setEnd(LocalTime.parse(times[1]));
		lecture.setClassroom(Classroom.forName(node.get("Učionica").asText()).orElse(null));
		String validFrom = node.get("validFrom").asText();
		String validTo = node.get("validTo").asText();
		lecture.setValidityPeriod(new ValidityPeriod(LocalDate.parse(validFrom), LocalDate.parse(validTo)));
		return lecture;
	}
}
