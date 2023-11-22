package schedule.common;

import java.time.LocalDate;


/**
 * The {@link ValidityPeriod} record represents a period of validity,
 * defined by a start date ("valid from") and an end date ("valid to").
 */
public record ValidityPeriod(LocalDate validFrom, LocalDate validTo) {

	/**
	 * Checks if a given date is within the valid range.
	 *
	 * @param date The date to be checked.
	 * @return true if the given date is within the valid range, false otherwise.
	 */
	public boolean dateIsIn(LocalDate date) {
		return date.isAfter(validFrom) && date.isBefore(validTo);
	}

	/**
	 * Checks if the current validity period overlaps with another validity period.
	 *
	 * @param other The other validity period to check for overlap.
	 * @return true if the current validity period overlaps with the other validity period, false otherwise.
	 */
	public boolean overlaps(ValidityPeriod other) {
		return (this.validFrom.isBefore(other.validTo) && this.validTo.isAfter(other.validFrom));
	}
}