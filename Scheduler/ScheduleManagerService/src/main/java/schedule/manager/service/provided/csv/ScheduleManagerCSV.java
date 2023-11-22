package schedule.manager.service.provided.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import schedule.classroom.Classroom;
import schedule.lecture.Lecture;
import schedule.lecture.type.LectureType;
import schedule.manager.service.ScheduleManagerService;
import schedule.manager.service.provided.csv.mappers.ConfigMapping;
import schedule.manager.service.provided.csv.mappers.DayMapper;
import schedule.manager.service.provided.csv.mappers.LectureTypeMapper;
import schedule.manager.service.provided.csv.mappers.TimePeriodMapper;

import java.io.*;
import java.util.*;

@SuppressWarnings("DuplicatedCode")
public class ScheduleManagerCSV implements ScheduleManagerService {

	public ScheduleManagerCSV() {
	}

	private static List<ConfigMapping> readConfig(String filePath) throws FileNotFoundException {
		List<ConfigMapping> mappings = new ArrayList<>();

		File file = new File(filePath);
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] splitLine = line.split(" ", 3);

			mappings.add(new ConfigMapping(Integer.valueOf(splitLine[0]), splitLine[1], splitLine[2]));
		}

		scanner.close();

		return mappings;
	}

	@Override
	public List<Lecture> loadData(String filePath, String configPath) throws IOException {
		return loadLecturesFromCSV(filePath, configPath);
	}

	@Override
	public boolean exportData(List<Lecture> lectures, String path) throws IOException {
		return writeDataToCSV(lectures, path);
	}

	private List<Lecture> loadLecturesFromCSV(String filePath, String configPath) throws IOException {
		List<Lecture> lectures = new ArrayList<>();

		List<ConfigMapping> columnMappings = readConfig(configPath);
		Map<Integer, String> mappings = new HashMap<>();
		for (ConfigMapping configMapping : columnMappings) {
			mappings.put(configMapping.getIndex(), configMapping.getOriginal());
		}

		FileReader fileReader = new FileReader(filePath);
		CSVParser parser = CSVFormat.Builder.create(CSVFormat.DEFAULT).setHeader().setSkipHeaderRecord(true).build().parse(fileReader);

		for (CSVRecord record : parser) {
			Lecture lecture = new Lecture();
			for (ConfigMapping entry : columnMappings) {
				mapColumnValue(lecture, entry, record, mappings);
			}
			lectures.add(lecture);
		}

		return lectures;
	}

	private Lecture mapColumnValue(Lecture lecture, ConfigMapping entry, CSVRecord record, Map<Integer, String> mappings) {
		int columnIndex = entry.getIndex();

		switch (mappings.get(columnIndex)) {
			case "name" -> lecture.setSubject(record.get(columnIndex));
			case "type" -> {
				String typeString = record.get(columnIndex);
				LectureType lectureType = LectureTypeMapper.mapToLectureType(typeString);
				lecture.setType(lectureType);
			}
			case "professor" -> lecture.setProfessor(record.get(columnIndex));
			case "groups" -> {
				String groupsString = record.get(columnIndex);
				Set<String> groups = new HashSet<>(Arrays.asList(groupsString.split("\\s*,\\s*")));
				lecture.setGroups(groups);
			}
			case "day" -> {
				String dayAbbreviation = record.get(columnIndex).trim();
				lecture.setDay(DayMapper.mapToDayOfWeek(dayAbbreviation.replaceAll("[\\s ]*", "").trim()));
			}
			case "timeRange" -> {
				String timeRangeString = record.get(columnIndex);
				TimePeriodMapper.TimeRange timeRange = TimePeriodMapper.parseTimeString(timeRangeString);
				lecture.setStart(timeRange.getStartTime());
				lecture.setEnd(timeRange.getEndTime());
			}
			case "classroom" -> lecture.setClassroom(Classroom.forName(record.get(columnIndex)).orElse(null));
		}

		return lecture;
	}

	private boolean writeDataToCSV(List<Lecture> lectures, String path) throws IOException {
		FileWriter fileWriter = new FileWriter(path);
		CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.Builder.create().setHeader("name", "type", "professor", "groups", "day", "timeRange", "classroom").build());

		for (Lecture lecture : lectures) {
			String timeRange = TimePeriodMapper.formatTimeRange(new TimePeriodMapper.TimeRange(lecture.getStart(), lecture.getEnd()));
			csvPrinter.printRecord(lecture.getSubject(), LectureTypeMapper.lectureTypeToString(lecture.getType()), lecture.getProfessor(), String.join(", ", lecture.getGroups()), DayMapper.mapToAbbreviation(lecture.getDay()), timeRange, lecture.getClassroom().getName());
		}

		csvPrinter.close();
		fileWriter.close();

		return true;
	}

}
