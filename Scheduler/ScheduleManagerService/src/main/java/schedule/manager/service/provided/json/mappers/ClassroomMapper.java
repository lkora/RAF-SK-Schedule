package schedule.manager.service.provided.json.mappers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import schedule.classroom.Classroom;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for mapping JSON data to Classroom objects and vice versa.
 */
public class ClassroomMapper {

	private final ObjectMapper objectMapper;

	/**
	 * Initializes a new instance of the ClassroomMapper class.
	 * <p>
	 * This constructor creates an instance of the ObjectMapper class and performs the necessary initialization steps,
	 * such as registering a deserializer for the Classroom class and enabling output indentation during serialization.
	 */
	public ClassroomMapper() {
		objectMapper = new ObjectMapper();

		SimpleModule module = new SimpleModule();
		module.addDeserializer(Classroom.class, new ClassroomDeserializer());
		objectMapper.registerModule(module);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	/**
	 * Retrieves a list of Classroom objects from a specified file path.
	 * <p>
	 * This method reads the contents of the file located at the specified path and deserializes the data into a list
	 * of Classroom objects using the ObjectMapper.
	 * The file must exist; otherwise, an IOException is thrown.
	 * <p>
	 * <b>Important:</b> The file must be in a format compatible with the deserialization process.
	 * Make sure the file
	 * follows the appropriate format defined for Classroom objects.
	 *
	 * @param path the file path to read the Classroom data from
	 * @return a list of Classroom objects deserialized from the file
	 * @throws IOException if the file does not exist or cannot be created
	 */
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

	/**
	 * This class is a custom deserializer for the Classroom class.
	 * It is used to deserialize JSON data into Classroom objects.
	 */
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
