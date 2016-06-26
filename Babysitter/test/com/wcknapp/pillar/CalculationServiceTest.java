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
		assertEquals(0, uut.calculateEveningRate(LocalTime.parse("5:00PM", FORMATTER), 
				LocalTime.parse("5:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateEveningRateReturns12ForOneHourWorked() {
		assertEquals(12, uut.calculateEveningRate(LocalTime.parse("5:00PM", FORMATTER),
				LocalTime.parse("6:00PM", FORMATTER)));
	}
}
