package json.mappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import schedule.lecture.Lecture;

import java.io.IOException;

public class LectureSerializer extends JsonSerializer<Lecture> {
    @Override
    public void serialize(Lecture lecture, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("Predmet", lecture.getName());
        jgen.writeStringField("Tip", lecture.getType().toString());
        jgen.writeStringField("Nastavnik", lecture.getProfessor());
        jgen.writeStringField("Grupe", String.join(",", lecture.getGroups()));
        jgen.writeStringField("Dan", lecture.getDay().toString());
        jgen.writeStringField("Termin", lecture.getStart().toString() + "-" + lecture.getEnd().toString());
        jgen.writeStringField("Učionica", lecture.getClassroom());
        jgen.writeEndObject();
    }
}

