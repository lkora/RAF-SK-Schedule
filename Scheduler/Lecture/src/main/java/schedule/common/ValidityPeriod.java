package schedule.common;

import java.time.LocalDate;


/**
 * The {@link ValidityPeriod} record represents a period of validity,
 * defined by a start date ("valid from") and an end date ("valid to").
 */
public record ValidityPeriod(LocalDate validFrom, LocalDate validTo) {
}