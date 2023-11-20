package json.mappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import schedule.lecture.Lecture;

import java.io.IOException;

public class LectureSerializer extends JsonSerializer<Lecture> {
    @Override
    public void serialize(Lecture lecture, JsonGenerator JsonGen, SerializerProvider provider) throws IOException {
        JsonGen.writeStartObject();
        JsonGen.writeStringField("Predmet", lecture.getName());
        JsonGen.writeStringField("Tip", lecture.getType().toString());
        JsonGen.writeStringField("Nastavnik", lecture.getProfessor());
        JsonGen.writeStringField("Grupe", String.join(",", lecture.getGroups()));
        JsonGen.writeStringField("Dan", lecture.getDay().toString());
        JsonGen.writeStringField("Termin", lecture.getStart().toString() + "-" + lecture.getEnd().toString());
        JsonGen.writeStringField("Učionica", lecture.getClassroom().getName());
        JsonGen.writeEndObject();
    }
}

