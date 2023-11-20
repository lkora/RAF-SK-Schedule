package json.mappers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import schedule.lecture.Lecture;
import schedule.lecture.type.LectureType;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;

public class LectureDeserializer extends JsonDeserializer<Lecture> {
    @Override
    public Lecture deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Lecture lecture = new Lecture();

        lecture.setName(node.get("Predmet").asText());
        lecture.setType(LectureType.valueOf(node.get("Tip").asText()));
        lecture.setProfessor(node.get("Nastavnik").asText());
        lecture.setGroups(new HashSet<>(Arrays.asList(node.get("Grupe").asText().split(","))));
        lecture.setDay(DayOfWeek.valueOf(node.get("Dan").asText()));
        String[] times = node.get("Termin").asText().split("-");
        lecture.setStart(LocalTime.parse(times[0]));
        lecture.setEnd(LocalTime.parse(times[1]));
        lecture.setClassroom(node.get("Učionica").asText());

        return lecture;
    }
}
