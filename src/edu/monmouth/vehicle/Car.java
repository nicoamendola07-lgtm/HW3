package edu.monmouth.vehicle;

public class Car implements Vehicle {
	public PowerTypes powerSource;
	public int engineSize;
	
	public Car(PowerTypes powerSource,int engineSize) throws CarException {
		setPowerSource(powerSource);
		setEngineSize(engineSize);
	}
	
	//SETTERS
	public void setPowerSource(PowerTypes powerSource) {
		this.powerSource = powerSource;
	}
	public void setEngineSize(int size) throws CarException {
		if (VehicleConstants.MIN_ENG_SIZE > size || size > VehicleConstants.MAX_ENG_SIZE) {
			throw new CarException("Illegal engine size: "+size); 
		}
		this.engineSize=size;
	}
	
	//GETTERS
	public PowerTypes getPowerSource() {
		return powerSource;
	}
	public int getEngineSize() {
		return engineSize;
	}
	
	//INTERFACE-BOUND
	public void go() {
		System.out.println("Car Accelerates");
	}
	public void stop() {
		System.out.println("Car Brakes");
	}
	public PowerTypes powerBy() {
		System.out.println("Re-Fueling car with "+getPowerSource());
		return powerSource;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (!(other instanceof Car)) { return false; }
		
		Car otherCar = (Car) other;
		if (otherCar.getEngineSize()==this.engineSize && otherCar.getPowerSource()==this.powerSource) { return true; }
		
		return false;
	}
	@Override
	public String toString() {
		return "[Car's Power Source: "+getPowerSource()+" | Car's Engine Size: "+getEngineSize()+"]";
	}
	@Override
	public int hashCode() {
		String stringEngSize = Integer.toString(engineSize);
		int result = powerSource.hashCode();
		result = result+stringEngSize.hashCode() *31;
		return result;
	}
	
}