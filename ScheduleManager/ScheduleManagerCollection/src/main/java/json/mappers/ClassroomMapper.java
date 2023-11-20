package json.mappers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import schedule.classroom.Classroom;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClassroomMapper {

	private final ObjectMapper objectMapper;

	public ClassroomMapper() {
		objectMapper = new ObjectMapper();

		SimpleModule module = new SimpleModule();
		module.addDeserializer(Classroom.class, new ClassroomDeserializer());
		objectMapper.registerModule(module);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public List<Classroom> getClassrooms(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			if (file.createNewFile()) {
				throw new IOException("File not found");
			}
		}

		return objectMapper.readValue(file, new TypeReference<>() {
		});
	}

	public static class ClassroomDeserializer extends JsonDeserializer<Classroom> {
		@Override
		public Classroom deserialize(JsonParser p, DeserializationContext context) throws IOException {
			JsonNode node = p.getCodec().readTree(p);
			String name = node.get("classroom").asText();
			boolean projector = node.get("projector").asBoolean();
			Integer noSpaces = node.get("no_spaces").asInt();
			Integer noComputers = node.get("computers").asInt();
			return new Classroom(name, projector, noSpaces, noComputers);
		}
	}
}
