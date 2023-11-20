package json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import json.mappers.ClassroomMapper;
import json.mappers.LectureDeserializer;
import json.mappers.LectureSerializer;
import schedule.classroom.Classroom;
import schedule.lecture.Lecture;
import schedule.manager.service.ScheduleManagerService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("DuplicatedCode")
public class ScheduleManagerJSON implements ScheduleManagerService {

    private final ObjectMapper objectMapper;

    public ScheduleManagerJSON() {
        objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Lecture.class, new LectureDeserializer());
        module.addDeserializer(Classroom.class, new ClassroomMapper.ClassroomDeserializer());
        module.addSerializer(Lecture.class, new LectureSerializer());

        objectMapper.registerModule(module);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        initializeClassrooms();
    }

    @Override
    public List<Lecture> loadData(String path, String configPath) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if (file.createNewFile()) {
                throw new IOException("File not found");
            }
        }

	    return objectMapper.readValue(file, new TypeReference<>() {
	    });
    }

    @Override
    public boolean exportData(List<Lecture> lectures, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if (file.createNewFile()) {
                throw new IOException("File not found");
            }
        }

        FileWriter writer = new FileWriter(file);
        String json = objectMapper.writeValueAsString(lectures);
        writer.write(json);

        writer.close();
        return true;
    }

    @Override
    public void initializeClassrooms() {
        List<Classroom> classrooms;
        try {
            var mapper = new ClassroomMapper();
            classrooms = new ArrayList<>(mapper.getClassrooms("Schedule/src/main/resources/classrooms.json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Classroom.initialize(classrooms);
    }
}
