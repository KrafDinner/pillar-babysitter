package com.wcknapp.pillar;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalculationService {
	private static final int EVENING_RATE = 12;

	protected long calculateEveningRate(LocalTime startTime, LocalTime bedTime) {
		return ChronoUnit.HOURS.between(startTime, bedTime) * EVENING_RATE;
	}

	public long calculateNightWage(LocalTime bedTime) {
		return 0;
	}

}
