package com.wcknapp.pillar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalculationService {
	private static final int EVENING_RATE = 12;
	private static final int NIGHT_RATE = 8;

	protected long calculateEveningWage(LocalTime startTime, LocalTime bedTime) {
		return ChronoUnit.HOURS.between(startTime, bedTime) * EVENING_RATE;
	}

	protected long calculateNightWage(LocalTime bedTime) {
		if (bedTime.equals(LocalTime.MIDNIGHT)) {
			return 0;
		}
		
		LocalDateTime startDate = bedTime.atDate(LocalDate.now());
		LocalDateTime endDate = LocalTime.MIDNIGHT.atDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
		return ChronoUnit.HOURS.between(startDate, endDate) * NIGHT_RATE;
	}

	protected long calculateMorningWage(LocalTime endTime) {
		return 0;
	}
}
