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
	 * Validates that the time is not before 5 PM
	 * This can be used to validate the start time or bed time of the shift
	 * 
	 * @param time The start time or bed time of the babysitting shift
	 * @return
	 */
	public boolean validateShiftTime(String time) {
		boolean result = false;
		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:ma");
		try {
			LocalTime localStartTime = LocalTime.parse(time, timeFormatter);
			LocalTime validStartTime = LocalTime.parse("5:00PM", timeFormatter);
			
			result = localStartTime.isAfter(validStartTime) || localStartTime.equals(validStartTime);
		} catch (DateTimeParseException e) {
			result = false;
		}
		
		return result;
	}

	/**
	 * Validates the end time is after 5 PM and before 4 AM.
	 * 
	 * @param time The ending time of the babysitting shift
	 * @return
	 */
	public boolean validateEndTime(String time) {
		boolean result = false;
		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:ma");
		try {
			LocalTime localEndTime = LocalTime.parse(time, timeFormatter);
			LocalTime validStartTime = LocalTime.parse("5:00PM", timeFormatter);
			LocalTime maxEndTime = LocalTime.parse("4:00AM", timeFormatter);
		
			result = (localEndTime.isAfter(validStartTime) && localEndTime.isBefore(LocalTime.MAX)) ||
					(localEndTime.isAfter(LocalTime.MIDNIGHT) && localEndTime.isBefore(maxEndTime)) ||
					localEndTime.equals(validStartTime) || localEndTime.equals(LocalTime.MIDNIGHT) ||
					localEndTime.equals(maxEndTime);
		} catch (DateTimeParseException e) {
			result = false;
		}
		
		return result;
	}
}
