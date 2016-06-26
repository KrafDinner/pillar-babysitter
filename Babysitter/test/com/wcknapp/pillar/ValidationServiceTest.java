package com.wcknapp.pillar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidationServiceTest {
	ValidationService uut;
	
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
	public void ensureValidateTimeReturnsTrueWhenValidPMTimeIsPassed() {
		assertTrue(uut.validateTime("5:00PM"));
	}
	
	@Test
	public void ensureValidateTimeReturnsTrueWhenValidAMTimeIsPassed() {
		assertTrue(uut.validateTime("3:00AM"));
	}
	
	@Test
	public void ensureValidateStartTimeReturnsFalseIfStartTimeIsTooEarly() {
		assertFalse(uut.validateStartTime("4:00PM"));
	}
}
