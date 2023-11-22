package schedule.exclusions;

import schedule.lecture.Lecture;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Exclusion represents a schedule exclusion. <p>
 * It provides a method to test a given Lecture object for a specific condition.
 * It Also provides a default method to create a new Exclusion that represents the logical
 * AND operation of this Exclusion and another Predicate.
 */
@FunctionalInterface
public interface Exclusion extends Predicate<Lecture> {

	/**
	 * This method tests the given Lecture object for a specific condition.
	 * @param lecture the Lecture object to be tested
	 * @return true if the Lecture object satisfies the condition, false otherwise
	 */
	@Override
	boolean test(Lecture lecture);

	/**
	 * Returns a new Predicate that represents the logical AND operation of this Exclusion and the given Predicate.
	 *
	 * @param other the Predicate to be combined with this Predicate
	 * @return a new Exclusion that represents the logical AND operation of this Exclusion and the given Predicate
	 * @throws NullPointerException if the other Predicate is null
	 */
	@Override
	default Exclusion and(Predicate<? super Lecture> other) {
		Objects.requireNonNull(other);
		return lecture -> test(lecture) && other.test(lecture);
	}
}
