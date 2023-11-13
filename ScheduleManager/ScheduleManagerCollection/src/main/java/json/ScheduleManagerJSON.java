package json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import json.mappers.LectureDeserializer;
import json.mappers.LectureSerializer;
import schedule.lecture.Lecture;
import schedule.manager.service.ScheduleManagerService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class ScheduleManagerJSON implements ScheduleManagerService {

    private ObjectMapper objectMapper;

    public ScheduleManagerJSON() {
        objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Lecture.class, new LectureDeserializer());
        module.addSerializer(Lecture.class, new LectureSerializer());

        objectMapper.registerModule(module);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public List<Lecture> loadData(String path, String configPath) throws IOException {
        File file = new File(path);
        if (!file.exists()) { file.createNewFile(); }

        List<Lecture> lectures = objectMapper.readValue(file, new TypeReference<List<Lecture>>() {});
        return lectures;
    }

    @Override
    public boolean exportData(List<Lecture> lectures, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) { file.createNewFile(); }

        FileWriter writer = new FileWriter(file);
        String json = objectMapper.writeValueAsString(lectures);
        writer.write(json);

        writer.close();
        return true;
    }

}
