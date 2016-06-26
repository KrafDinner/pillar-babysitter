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
	public void ensureValidateTimeReturnsTrueWhenValidTimeIsPassed() {
		uut = new ValidationService();
		assertTrue(uut.validateTime("5:00PM"));
	}
}
