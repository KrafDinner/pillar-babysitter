package com.wcknapp.pillar;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ValidationService {
	private static final String TIME_FORMAT = "^(?:0?[1-9]|1[0-2]):00[AP]M$";
	
	/**
	 * Validate the time format for the purpose of the kata.
	 * Times must be strings of the format hh:mm[AP]M
	 * 
	 * @param inputTime Time to be validated by the rules
	 * @return
	 */
	public boolean validateTime(String inputTime) {
		return Pattern.compile(TIME_FORMAT).matcher(inputTime).matches();
	}
	
	/**
	 * Validates that the starting time is not before 5 PM
	 * 
	 * @param startTime The start time of the babysitting shift
	 * @return
	 */
	public boolean validateStartTime(String startTime) {
		boolean result = false;
		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:ma");
		try {
			LocalTime localStartTime = LocalTime.parse(startTime, timeFormatter);
			LocalTime validStartTime = LocalTime.parse("5:00PM", timeFormatter);
			
			result = localStartTime.isAfter(validStartTime) || localStartTime.equals(validStartTime);
		} catch (DateTimeParseException e) {
			result = false;
		}
		
		return result;
	}

	/**
	 * Validates that the bed time is not before 5 and not after midnight.
	 * 
	 * @param bedTime The time the babysitter must put the child to bed.
	 * @return
	 */
	public boolean validateBedTime(String bedTime) {
		boolean result = false;
		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:ma");
		LocalTime localBedTime = LocalTime.parse(bedTime, timeFormatter);
		LocalTime validStartTime = LocalTime.parse("5:00PM", timeFormatter);
		
		if (localBedTime.isBefore(validStartTime)) {
			result = false;
		} else {
			result = true;
		}
		
		
		return result;
	}

}
