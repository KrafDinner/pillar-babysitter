package com.wcknapp.pillar;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class CalculationServiceTest {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:ma");
	private CalculationService uut;
	private ValidationService mockValidationService;
	
	@Before
	public void setUp() {
		uut = new CalculationService();
		mockValidationService = EasyMock.createMock(ValidationService.class);
		uut.setValidationService(mockValidationService);
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
		assertEquals(0, uut.calculateNightWage(LocalTime.parse("12:00AM", FORMATTER), 
				LocalTime.parse("3:00AM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateNightWageReturns8ForBedTimeOf11PM() {
		assertEquals(8, uut.calculateNightWage(LocalTime.parse("11:00PM", FORMATTER), 
				LocalTime.parse("3:00AM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateNightWageReturns8ForBedTimeOf8PMAndEndTimeOf9PM() {
		assertEquals(8, uut.calculateNightWage(LocalTime.parse("8:00PM", FORMATTER), 
				LocalTime.parse("9:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateNightWageReturns0ForBedTimeOf8PMAndEndTimeOf8PM() {
		assertEquals(0, uut.calculateNightWage(LocalTime.parse("8:00PM", FORMATTER), 
				LocalTime.parse("8:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateMorningWageReturns0ForMidnightEndTime() {
		assertEquals(0, uut.calculateMorningWage(LocalTime.MIDNIGHT));
	}
	
	@Test
	public void ensureCalculateMorningWageReturns32ForEndTimeOf2AM() {
		assertEquals(32, uut.calculateMorningWage(LocalTime.parse("2:00AM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateMorningWageReturns0ForEndTimeBeforeMidnight() {
		assertEquals(0, uut.calculateMorningWage(LocalTime.parse("10:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureCalculateWagesReturns0ForAllInputsEqual() {
		//This test case is for zero hours worked
		assertEquals(0, uut.calculateWages("5:00PM", "5:00PM", "5:00PM"));
	}
	
	@Test
	public void ensureCalculateWagesReturns0IfInputIsInvalid() {
		EasyMock.expect(mockValidationService.validateShift("500PM", "10:00PM", "3:00AM")).andReturn(false);
		
		EasyMock.replay(mockValidationService);
		long actualValue = uut.calculateWages("500PM", "10:00PM", "3:00AM");
		EasyMock.verify(mockValidationService);
		
		assertEquals(0, actualValue);
	}
	
	@Test
	public void ensureCalculateWagesReturns12ForOnlyOneHourBeforeBedTime() {
		EasyMock.expect(mockValidationService.validateShift("6:00PM", "7:00PM", "7:00PM")).andReturn(true);
		
		EasyMock.replay(mockValidationService);
		long actualValue = uut.calculateWages("6:00PM", "7:00PM", "7:00PM");
		EasyMock.verify(mockValidationService);
		
		assertEquals(12, actualValue);
	}
	
	@Test
	public void ensureCalculateWagesReturns8ForNoTimeBeforeBedTimeAndOneHourAfter() {
		EasyMock.expect(mockValidationService.validateShift("6:00PM", "6:00PM", "7:00PM")).andReturn(true);
		
		EasyMock.replay(mockValidationService);
		long actualValue = uut.calculateWages("6:00PM", "6:00PM", "7:00PM");
		EasyMock.verify(mockValidationService);
		
		assertEquals(8, actualValue);
	}
	
	@Test
	public void ensureCalculateWagesReturns16ForNoTimeBeforeMidnightButOneHourAfter() {
		EasyMock.expect(mockValidationService.validateShift("12:00AM", "12:00AM", "1:00AM")).andReturn(true);
		
		EasyMock.replay(mockValidationService);
		long actualValue = uut.calculateWages("12:00AM", "12:00AM", "1:00AM");
		EasyMock.verify(mockValidationService);
		
		assertEquals(16, actualValue);
	}
}
