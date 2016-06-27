package com.wcknapp.pillar;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CalculationService {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:ma"); 
	private static final int EVENING_RATE = 12;
	private static final int NIGHT_RATE = 8;
	private static final int MORNING_RATE = 16;
	
	private ValidationService validationService;

	/**
	 * Calculates the wages for a babysitting shift with a given start, bed, and end time
	 * 
	 * Returns 0 if validation of times fails
	 * Times must be strings of the format hh:mm[AP]M
	 * 
	 * @param startTime The start time of the shift
	 * @param bedTime The bed time for the child
	 * @param endTime The end time of the shift
	 * 
	 * @return The wage that should be charged for the evening of babysitting
	 */
	public long calculateWages(String startTime, String bedTime, String endTime) {
		if (validationService.validateShift(startTime, bedTime, endTime)) {
			LocalTime startLocalTime = LocalTime.parse(startTime, FORMATTER);
			LocalTime bedLocalTime = LocalTime.parse(bedTime, FORMATTER);
			LocalTime endLocalTime = LocalTime.parse(endTime, FORMATTER);;
			
			return calculateEveningWage(startLocalTime, bedLocalTime)
					+ calculateNightWage(bedLocalTime, endLocalTime)
					+ calculateMorningWage(endLocalTime);
		}
		return 0;
	}
	
	/**
	 * Calculate the wage between start time and bed time for the child.
	 * 
	 * @param startTime The shift start time
	 * @param bedTime The bed time for the child
	 * 
	 * @return The wage for the first period of the evening between start and bed time
	 */
	protected long calculateEveningWage(LocalTime startTime, LocalTime bedTime) {
		return HOURS.between(startTime, bedTime) * EVENING_RATE;
	}

	/**
	 * Calculate the wage between bed time and midnight OR end time - whichever is sooner.
	 * 
	 * @param bedTime The bed time of the child
	 * @param endTime The end time of the shift
	 * 
	 * @return The wage for the second period of the evening between bed time and midnight or end time 
	 */
	protected long calculateNightWage(LocalTime bedTime, LocalTime endTime) {
		if (bedTime.equals(LocalTime.MIDNIGHT) || bedTime.equals(endTime)) {
			return 0;
		}
		
		LocalDateTime startDate = bedTime.atDate(LocalDate.now());
		LocalDateTime endDate = endTime.isAfter(bedTime) ? endTime.atDate(LocalDate.now()) :
			LocalTime.MIDNIGHT.atDate(LocalDate.now().plus(1, DAYS));
		
		return HOURS.between(startDate, endDate) * NIGHT_RATE;
	}

	/**
	 * Calculates the rate between midnight and the end of the shift.
	 * If end time is before midnight, will always return 0.
	 * 
	 * @param endTime The end time of the shift
	 * 
	 * @return The wage for the third period of the evening between midnight and end time if applicable
	 */
	protected long calculateMorningWage(LocalTime endTime) {
		return endTime.isAfter(LocalTime.parse("4:00AM", FORMATTER)) ? 0 : 
			HOURS.between(LocalTime.MIDNIGHT, endTime) * MORNING_RATE;
	}

	public ValidationService getValidationService() {
		return validationService;
	}

	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}
}
