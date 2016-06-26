package com.wcknapp.pillar;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class ValidationService {
	private static final String TIME_FORMAT = "^(?:0?[1-9]|1[0-2]):00[AP]M$";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:ma");
	
	public boolean validateShift(String startTime, String bedTime, String endTime) {
		boolean result = false;
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:ma");
		
		if (validateTime(startTime) && validateTime(bedTime) && validateTime(endTime)) {
			LocalTime start = LocalTime.parse(startTime, timeFormatter);
			LocalTime bed = LocalTime.parse(bedTime, timeFormatter);
			LocalTime end = LocalTime.parse(endTime, timeFormatter);
			
			if (validateShiftTime(start) && validateShiftTime(bed) && validateEndTime(end)) {
				if ((start.isBefore(bed) || start.equals(bed))
						&& ((end.isAfter(bed) || end.equals(bed)) 
						|| end.isAfter(LocalTime.MIDNIGHT))) {
					result = true;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Validate the time format for the purpose of the kata.
	 * Times must be strings of the format hh:mm[AP]M
	 * 
	 * @param inputTime Time to be validated by the rules
	 * @return
	 */
	protected boolean validateTime(String inputTime) {
		return Pattern.compile(TIME_FORMAT).matcher(inputTime).matches();
	}
	
	/**
	 * Validates that the time is not before 5 PM
	 * This can be used to validate the start time or bed time of the shift
	 * 
	 * @param time The start time or bed time of the babysitting shift
	 * @return
	 */
	protected boolean validateShiftTime(LocalTime time) {
		LocalTime validStartTime = LocalTime.parse("5:00PM", FORMATTER);
			
		return time.isAfter(validStartTime) || time.equals(validStartTime);
	}

	/**
	 * Validates the end time is after 5 PM and before 4 AM.
	 * 
	 * @param time The ending time of the babysitting shift
	 * @return
	 */
	protected boolean validateEndTime(LocalTime time) {
		LocalTime validStartTime = LocalTime.parse("5:00PM", FORMATTER);
		LocalTime maxEndTime = LocalTime.parse("4:00AM", FORMATTER);
	
		return (time.isAfter(validStartTime) && time.isBefore(LocalTime.MAX)) ||
				(time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(maxEndTime)) ||
				time.equals(validStartTime) || time.equals(LocalTime.MIDNIGHT) ||
				time.equals(maxEndTime);
	}
}
