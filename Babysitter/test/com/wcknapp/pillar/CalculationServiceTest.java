package com.wcknapp.pillar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CalculationServiceTest {
	private CalculationService uut;
	
	@Before
	public void setUp() {
		uut = new CalculationService();
	}
	
	@Test
	public void ensureCalculateEveningRateReturns12TimesInputHours() {
		assertEquals(0, uut.calculateEveningRate("5:00PM", "5:00PM"));
	}
	
}
