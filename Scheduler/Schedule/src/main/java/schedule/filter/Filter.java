package schedule.filter;

import schedule.lecture.Lecture;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Interface for filtering lectures.
 * <p>
 * This interface extends {@link Predicate} and is used to filter lectures by a given requirement.
 *
 * @see Predicate
 */
@FunctionalInterface
public interface Filter extends Predicate<Lecture> {
	/**
	 * Creates a new Filter object based on the given type and requirement.
	 *
	 * @param type        the type of filter to create
	 * @param requirement the requirement for the filter
	 * @return a new Filter object
	 */
	static Filter of(FilterType type, String requirement) {
		return switch (type) {
			case SUBJECT -> new SubjectFilter(requirement);
			case PROFESSOR -> new ProfessorFilter(requirement);
			case GROUP -> new GroupFilter(requirement);
			case CLASSROOM_PROJECTOR -> new ClassroomProjectorFilter(requirement);
			case CLASSROOM_COMPUTER -> new ClassroomComputerFilter(Integer.parseInt(requirement));
			case CLASSROOM_NAME -> new ClassroomNameFilter(requirement);
			case CLASSROOM_SIZE -> new ClassroomSizeFilter(Integer.parseInt(requirement));
		};
	}

	@Override
	boolean test(Lecture lecture);

	/**
	 * Returns the name of the class.
	 *
	 * @return The name of the class.
	 */
	default String getName() {
		return getClass().getSimpleName();
	}

	/**
	 * Applies the filter to a Lecture object.
	 *
	 * @param lecture The Lecture object to apply the filter on.
	 * @return True if the Lecture object passes the filter, false otherwise.
	 */
	default boolean filter(Lecture lecture) {
		return test(lecture);
	}

	/**
	 * Returns a new Filter object that represents the logical AND of this filter and another filter.
	 *
	 * @param other The other filter to apply the logical AND operation with.
	 * @return A new Filter object that represents the logical AND of this filter and the other filter.
	 * @throws NullPointerException if the other filter is null.
	 */
	@Override
	default Filter and(Predicate<? super Lecture> other) {
		Objects.requireNonNull(other);
		return (t) -> test(t) && other.test(t);
	}

	/**
	 * Enum representing different types of filters that the library provides an implementation for.
	 * The user can implement other types of filters by subclassing or with lambdas.
	 */
	enum FilterType {
		SUBJECT, PROFESSOR, GROUP, CLASSROOM_PROJECTOR,
		CLASSROOM_COMPUTER, CLASSROOM_NAME, CLASSROOM_SIZE
	}
}
