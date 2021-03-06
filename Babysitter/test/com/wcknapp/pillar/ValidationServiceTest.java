package com.wcknapp.pillar;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class ValidationServiceTest {
	private ValidationService uut;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:ma");
	
	@Before
	public void setUp() {
		uut = new ValidationService();
	}
	
	@Test
	public void ensureValidateTimeReturnsFalseWhenSimpleNumberIsPassed() {
		assertFalse(uut.validateTime("5"));
	}

	@Test
	public void ensureValidateTimeReturnsFalseWhenTimeIsMissingAMPM() {
		assertFalse(uut.validateTime("5:00"));
	}
	
	@Test
	public void ensureValidateTimeReturnsFalseWhenInvalidAMTimeIsPassed() {
		assertFalse(uut.validateTime("13:00AM"));
	}
	
	@Test
	public void ensureValidateTimeReturnsFalseWhenTimeIsNotOnHour() {
		assertFalse(uut.validateTime("5:30PM"));
	}
	
	@Test
	public void ensureValidateTimeReturnsTrueWhenValidPMTimeIsPassed() {
		assertTrue(uut.validateTime("5:00PM"));
	}
	
	@Test
	public void ensureValidateTimeReturnsTrueWhenValidAMTimeIsPassed() {
		assertTrue(uut.validateTime("3:00AM"));
	}
	
	@Test
	public void ensureValidateShiftTimeReturnsFalseIfStartTimeIsTooEarly() {
		assertFalse(uut.validateShiftTime(LocalTime.parse("4:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureValidateShiftTimeReturnsTrueIfTimeIsFivePM() {
		assertTrue(uut.validateShiftTime(LocalTime.parse("5:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureValidateShiftTimeReturnsTrueIfTimeIsAfterFivePMAndBeforeMidnight() {
		assertTrue(uut.validateShiftTime(LocalTime.parse("7:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureValidateEndTimeReturnsFalseIfTimeIsBeforeFivePM() {
		assertFalse(uut.validateEndTime(LocalTime.parse("4:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureValidateEndTimeReturnsFalseIfTimeIsAfterFourAM() {
		assertFalse(uut.validateEndTime(LocalTime.parse("5:00AM", FORMATTER)));
	}
	
	@Test
	public void ensureValidateEndTimeReturnsTrueIfTimeIsMidnight() {
		assertTrue(uut.validateEndTime(LocalTime.parse("12:00AM", FORMATTER)));
	}
	
	@Test
	public void ensureValidateEndTimeReturnsTrueIfTimeIsFourAM() {
		assertTrue(uut.validateEndTime(LocalTime.parse("4:00AM", FORMATTER)));
	}
	
	@Test
	public void ensureValidateEndTimeReturnsTrueIfEndTimeIsAfterFiveButBeforeMidnight() {
		assertTrue(uut.validateEndTime(LocalTime.parse("10:00PM", FORMATTER)));
	}
	
	@Test
	public void ensureValidateEndTimeReturnsTrueIfEndTimeIsAfterMidnightButBeforeFour() {
		assertTrue(uut.validateEndTime(LocalTime.parse("3:00AM", FORMATTER)));
	}
	
	@Test
	public void ensureValidateShiftReturnsFalseForEarlyStartTime() {
		assertFalse(uut.validateShift("4:00PM", "9:00PM", "3:00AM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsFalseForBedtimeAfterMidnight() {
		assertFalse(uut.validateShift("5:00PM", "1:00AM", "3:00AM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsFalseForEndTimeAfterFour() {
		assertFalse(uut.validateShift("5:00PM", "10:00PM", "6:00AM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsFalseIfStartTimeFailsParsing() {
		assertFalse(uut.validateShift("700PM", "10:00PM", "3:00AM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsFalseIfBedTimeFailsParsing() {
		assertFalse(uut.validateShift("7:00PM", "1000PM", "3:00AM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsFalseIfEndTimeFailsParsing() {
		assertFalse(uut.validateShift("7:00PM", "10:00PM", "300AM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsFalseForBedTimeBeforeStartTime() {
		assertFalse(uut.validateShift("8:00PM", "7:00PM", "2:00AM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsFalseForEndTimeBeforeBedTime() {
		assertFalse(uut.validateShift("7:00PM", "10:00PM", "9:00PM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsTrueForAllValidTimes() {
		assertTrue(uut.validateShift("5:00PM", "11:00PM", "4:00AM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsTrueIfBedAndEndTimeAreEqual() {
		assertTrue(uut.validateShift("5:00PM", "7:00PM", "7:00PM"));
	}
	
	@Test
	public void ensureValidateShiftReturnsTrueIfStartAndBedTimeAreEqual() {
		assertTrue(uut.validateShift("7:00PM", "7:00PM", "2:00AM"));
	}
}
