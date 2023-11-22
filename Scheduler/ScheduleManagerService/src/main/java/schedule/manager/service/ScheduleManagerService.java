package schedule.manager.service;

import schedule.exporter.spec.ScheduleExporter;
import schedule.importer.spec.ScheduleImporter;

/**
 * This interface represents a Schedule Manager Service that acts as a bridge between the ScheduleExporter
 * and ScheduleImporter interfaces.
 * It provides methods to export and import schedules.
 * <p>
 * This interface does not provide any additional methods apart from those defined in the ScheduleExporter
 * and ScheduleImporter interfaces.
 * <p>
 * Implementations of this interface should provide functionality to handle exporting and importing of schedules
 * to and from different formats or data sources.
 *
 * @see ScheduleExporter
 * @see ScheduleImporter
 */
public interface ScheduleManagerService extends ScheduleExporter, ScheduleImporter {


}
