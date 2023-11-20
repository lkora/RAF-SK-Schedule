package schedule.classroom;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a classroom.
 * Value-based (equals and hashCode are generated for the fields, and don't use reference equality).
 */
@Getter
@Setter
@NoArgsConstructor
public final class Classroom {


	/**
	 * The name of the classroom.
	 */
	private String name;

	/**
	 * Whether the classroom has a projector.
	 */
	private boolean projector;
	/**
	 * The number of seats in the classroom.
	 */
	private Integer noSpaces;
	/**
	 * The number of computers in the classroom.
	 */
	private Integer noComputers;

	/**
	 * @param name        the name of the classroom
	 * @param projector   whether the classroom has a projector
	 * @param noSpaces    number of seats
	 * @param noComputers number of computers
	 */
	public Classroom(String name, Boolean projector, Integer noSpaces, Integer noComputers) {
		this.name = name;
		this.projector = projector;
		this.noSpaces = noSpaces;
		this.noComputers = noComputers;
	}

	/**
	 * Initializes the list of classrooms.
	 * Only the first call to this method has an effect.
	 * Others will instantly return.
	 *
	 * @param classrooms the list of classrooms
	 */
	public static void initialize(List<Classroom> classrooms) {
		Holder.init(classrooms);
	}

	/**
	 * Finds a classroom from a name.
	 *
	 * @param name The name of the classroom
	 * @return If exists, returns the classroom with the given name, otherwise returns empty optional
	 */
	public static Optional<Classroom> forName(String name) {
		return Holder.classrooms.stream().filter(c -> c.getName().equals(name)).findFirst();
	}

	/**
	 * Value-based equality.
	 *
	 * @param obj the object to compare
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Classroom) obj;
		return Objects.equals(this.name, that.name) &&
				Objects.equals(this.projector, that.projector) &&
				Objects.equals(this.noSpaces, that.noSpaces) &&
				Objects.equals(this.noComputers, that.noComputers);
	}

	/**
	 * Value-based hash code.
	 *
	 * @return the hash code of this object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(name, projector, noSpaces, noComputers);
	}

	@Override
	public String toString() {
		return "Classroom[" +
				"name=" + name + ", " +
				"projector=" + projector + ", " +
				"noSpaces=" + noSpaces + ", " +
				"noComputers=" + noComputers + ']';
	}

	private static class Holder {
		@Getter
		private static final List<Classroom> classrooms = new ArrayList<>();
		private static boolean init = false;

		static void init(List<Classroom> rooms) {
			if (init) {
				return;
			}
			classrooms.addAll(rooms);
			init = true;
		}
	}

}

