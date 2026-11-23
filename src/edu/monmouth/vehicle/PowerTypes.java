package edu.monmouth.vehicle;

public enum PowerTypes {
	ELECTRICITY, FUELOIL, GASOLINE, DEISEL, UNKNOWN;
	
	public static PowerTypes fromString(String msg) {
		try {
			return PowerTypes.valueOf(msg.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return UNKNOWN;
		}
	}
}
