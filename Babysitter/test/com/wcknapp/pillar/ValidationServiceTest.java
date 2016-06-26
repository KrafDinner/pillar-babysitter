package com.wcknapp.pillar;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidationServiceTest {
	ValidationService uut;
	
	@Test
	public void ensureValidateTimeReturnsFalseWhenInvalidTimeIsPassed() {
		uut = new ValidationService();
		assertFalse(uut.validateTime("5"));
	}

}
