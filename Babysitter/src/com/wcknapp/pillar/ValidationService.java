package com.wcknapp.pillar;

import java.util.regex.Pattern;

public class ValidationService {
	private static final String TIME_FORMAT = "^(?:0?[1-9]|1[0-2]):(?:[0-5][0-9])[AP]M$";
	/**
	 * Validate the time for the purpose of the kata.
	 * Times must be strings of the format hh:mm[AP]M
	 * 
	 * @param inputTime Time to be validated by the rules
	 * @return
	 */
	public boolean validateTime(String inputTime) {
		return Pattern.compile(TIME_FORMAT).matcher(inputTime).matches();
	}

}
