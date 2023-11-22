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

	default String getName() {
		return getClass().getSimpleName();
	}

	default boolean filter(Lecture lecture) {
		return test(lecture);
	}

	@Override
	default Filter and(Predicate<? super Lecture> other) {
		Objects.requireNonNull(other);
		return (t) -> test(t) && other.test(t);
	}
}
