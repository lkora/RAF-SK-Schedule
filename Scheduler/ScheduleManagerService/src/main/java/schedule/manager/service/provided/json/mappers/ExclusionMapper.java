package schedule.manager.service.provided.json.mappers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import schedule.exclusions.ExamExclusion;
import schedule.exclusions.Exclusion;
import schedule.exclusions.HolidayExclusion;
import schedule.exclusions.TestExclusion;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExclusionMapper {

	private final ObjectMapper objectMapper;

	public ExclusionMapper() {
		objectMapper = new ObjectMapper();

		SimpleModule module = new SimpleModule();
		module.addDeserializer(Exclusion.class, new ExclusionDeserializer());
		objectMapper.registerModule(module);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public List<Exclusion> getExclusion(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			if (file.createNewFile()) {
				throw new IOException("File not found");
			}
		}
		return objectMapper.readValue(file, new TypeReference<>() {
		});
	}

	public static class ExclusionDeserializer extends StdDeserializer<Exclusion> {

		public ExclusionDeserializer() {
			this(null);
		}

		public ExclusionDeserializer(Class<?> vc) {
			super(vc);
		}

		@Override
		public Exclusion deserialize(JsonParser parser,
		                             DeserializationContext deserializer) throws IOException {
			ObjectCodec codec = parser.getCodec();
			JsonNode node = codec.readTree(parser);

			// use the 'type' field to determine the record type
			String typeField = node.get("type").asText();
			if ("examExclusion".equals(typeField)) {
				// deserialize ExamExclusion
				return codec.treeToValue(node, ExamExclusion.class);
			}
			if ("holidayExclusion".equals(typeField)) {
				// deserialize HolidayExclusion
				return codec.treeToValue(node, HolidayExclusion.class);
			}
			if ("testExclusion".equals(typeField)) {
				// deserialize TestExclusion
				return codec.treeToValue(node, TestExclusion.class);
			}

			throw new IllegalArgumentException("Unknown type: " + typeField);
		}
	}
}