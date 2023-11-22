package schedule.filter;

import schedule.lecture.Lecture;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Interface for filtering lectures.
 * <p>
 *     This interface extends {@link Predicate} and is used to filter lectures by a given requirement.
 * @see Predicate
 */
@FunctionalInterface
public interface Filter extends Predicate<Lecture> {

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
}
