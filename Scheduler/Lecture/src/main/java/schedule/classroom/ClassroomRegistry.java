package schedule.classroom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The ClassroomRegistry class provides methods to manage and retrieve Classroom objects.
 */
public final class ClassroomRegistry {

	private static class Holder {
		private static final List<Classroom> CLASSROOMS = new ArrayList<>();
	}

	/**
	 * Initializes the list of classrooms if it is empty.
	 *
	 * @param rooms the list of classrooms to be added to the list
	 */
	public static void initialize(List<Classroom> rooms) {
		if (Holder.CLASSROOMS.isEmpty()) {
			Holder.CLASSROOMS.addAll(rooms);
		}
	}

	/**
	 * Returns an optional Classroom object with the given name.
	 *
	 * @param name the name of the classroom to search for
	 * @return an optional containing a Classroom object if found, otherwise an empty optional
	 */
	public static Optional<Classroom> forName(String name) {
		return Holder.CLASSROOMS.stream().filter(c -> c.getName().equals(name)).findFirst();
	}
}