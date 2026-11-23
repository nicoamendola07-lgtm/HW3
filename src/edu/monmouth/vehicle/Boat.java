package edu.monmouth.vehicle;

public class Boat implements Vehicle {
	public PowerTypes powerSource;
	public int boatLength;
	
	public Boat(PowerTypes powerSource,int boatLength) throws BoatException {
		setPowerSource(powerSource);
		setBoatLength(boatLength);
	}
	
	//SETTERS
	public void setPowerSource(PowerTypes powerSource) {
		this.powerSource = powerSource;
	}
	public void setBoatLength(int len) throws BoatException {
		if (VehicleConstants.MIN_BOAT_LEN > len || len > VehicleConstants.MAX_BOAT_LEN) {
			throw new BoatException("Illegal boat length: "+len); 
		}
		this.boatLength=len;
	}
	
	//GETTERS
	public PowerTypes getPowerSource() {
		return powerSource;
	}
	public int getBoatLength() {
		return boatLength;
	}
	
	//INTERFACE-BOUND
	public void go() {
		System.out.println("Boat Sails The Sea");
	}
	public void stop() {
		System.out.println("Boat Drops It's Anchor");
	}
	public PowerTypes powerBy() {
		System.out.println("Re-Fueling boat with "+getPowerSource());
		return powerSource;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other==null) { return false; }
		if (!(other instanceof Boat)) { return false; }
		
		Boat otherBoat = (Boat) other;
		if (otherBoat.getBoatLength() == this.boatLength && otherBoat.getPowerSource() == this.powerSource) { return true; }
		
		return false;
	}
	@Override
	public String toString() {
		return "[Boat's Power Source: "+getPowerSource()+" | Boat's Length: "+getBoatLength()+"]";
	}
	@Override
	public int hashCode() {
		String stringBoatLen = Integer.toString(boatLength);
		int result = stringBoatLen.hashCode();
		result = result+powerSource.hashCode() *31;
		return result;
	}
}
