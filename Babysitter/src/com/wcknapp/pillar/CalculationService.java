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

	public long calculateWages(String startTime, String bedTime, String endTime) {
		if (validationService.validateShift(startTime, bedTime, endTime)) {
			LocalTime startLocalTime = LocalTime.parse(startTime, FORMATTER);
			LocalTime bedLocalTime = LocalTime.parse(bedTime, FORMATTER);
			
			return calculateEveningWage(startLocalTime, bedLocalTime);
		}
		return 0;
	}
	
	protected long calculateEveningWage(LocalTime startTime, LocalTime bedTime) {
		return HOURS.between(startTime, bedTime) * EVENING_RATE;
	}

	protected long calculateNightWage(LocalTime bedTime) {
		if (bedTime.equals(LocalTime.MIDNIGHT)) {
			return 0;
		}
		
		LocalDateTime startDate = bedTime.atDate(LocalDate.now());
		LocalDateTime endDate = LocalTime.MIDNIGHT.atDate(LocalDate.now().plus(1, DAYS));
		return HOURS.between(startDate, endDate) * NIGHT_RATE;
	}

	protected long calculateMorningWage(LocalTime endTime) {
		return HOURS.between(LocalTime.MIDNIGHT, endTime) * MORNING_RATE;
	}

	public ValidationService getValidationService() {
		return validationService;
	}

	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}
}
