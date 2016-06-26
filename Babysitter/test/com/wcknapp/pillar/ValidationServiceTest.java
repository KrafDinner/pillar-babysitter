package com.wcknapp.pillar;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidationServiceTest {
	ValidationService uut;
	
	@Test
	public void ensureValidateTimeReturnsFalseWhenSimpleNumberIsPassed() {
		uut = new ValidationService();
		assertFalse(uut.validateTime("5"));
	}

	@Test
	public void ensureValidateTimeReturnsFalseWhenTimeIsMissingAMPM() {
		uut = new ValidationService();
		assertFalse(uut.validateTime("5:00"));
	}
	
	@Test
	public void ensureValidateTimeReturnsFalseWhenInvalidAMTimeIsPassed() {
		uut = new ValidationService();
		assertFalse(uut.validateTime("13:00AM"));
	}
	
	@Test
	public void ensureValidateTimeReturnsTrueWhenValidPMTimeIsPassed() {
		uut = new ValidationService();
		assertTrue(uut.validateTime("5:00PM"));
	}
	
	@Test
	public void ensureValidateTimeReturnsTrueWhenValidAMTimeIsPassed() {
		uut = new ValidationService();
		assertTrue(uut.validateTime("3:00AM"));
	}
}
