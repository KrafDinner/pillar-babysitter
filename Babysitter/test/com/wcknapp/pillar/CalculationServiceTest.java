package com.wcknapp.pillar;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class CalculationServiceTest {
	private CalculationService uut;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:ma");
	
	@Before
	public void setUp() {
		uut = new CalculationService();
	}
	
	@Test
	public void ensureCalculateEveningRateReturns0ForZeroHoursWorked() {
		assertEquals(0, uut.calculateEveningWage(LocalTime.parse("5:00PM", FORMATTER), 
				LocalTime.parse("5:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateEveningRateReturns12ForOneHourWorked() {
		assertEquals(12, uut.calculateEveningWage(LocalTime.parse("5:00PM", FORMATTER),
				LocalTime.parse("6:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateNightWageReturns0ForBedTimeOfMidnight() {
		assertEquals(0, uut.calculateNightWage(LocalTime.parse("12:00AM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateNightWageReturns8ForBedTimeOf11PM() {
		assertEquals(8, uut.calculateNightWage(LocalTime.parse("11:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateMorningWageReturns0ForMidnightEndTime() {
		assertEquals(0, uut.calculateMorningWage(LocalTime.MIDNIGHT));
	}
	
	@Test
	public void ensureCalculateMorningWageReturns32ForEndTimeOf2AM() {
		assertEquals(32, uut.calculateMorningWage(LocalTime.parse("2:00AM", FORMATTER)));
	}
}
