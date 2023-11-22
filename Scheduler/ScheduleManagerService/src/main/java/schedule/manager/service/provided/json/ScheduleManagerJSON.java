package schedule.manager.service.provided.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import schedule.classroom.Classroom;
import schedule.lecture.Lecture;
import schedule.manager.service.ScheduleManagerService;
import schedule.manager.service.provided.json.mappers.ClassroomMapper;
import schedule.manager.service.provided.json.mappers.LectureDeserializer;
import schedule.manager.service.provided.json.mappers.LectureSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * Implementation of the ScheduleManagerService interface that uses JSON as the data format for loading
 * and exporting lecture data.
 */
public class ScheduleManagerJSON implements ScheduleManagerService {

    private final ObjectMapper objectMapper;

    /**
     * This method is the constructor for the ScheduleManagerJSON class. It initializes the ScheduleManagerJSON object.
     * It sets up the ObjectMapper and configures it with deserializers and serializers for Lecture and Classroom objects.
     * It also enables indentation of the JSON output.
     */
    public ScheduleManagerJSON() {
        objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Lecture.class, new LectureDeserializer());
        module.addDeserializer(Classroom.class, new ClassroomMapper.ClassroomDeserializer());
        module.addSerializer(Lecture.class, new LectureSerializer());

        objectMapper.registerModule(module);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public List<Lecture> loadData(String path, String ignored) throws IOException {
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

}
