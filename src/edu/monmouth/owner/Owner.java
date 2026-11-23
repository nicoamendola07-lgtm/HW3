package edu.monmouth.owner;

public class Owner {
	private String name;
	private int yearOfBirth;
	
	public Owner(String name,int yearOfBirth) throws OwnerException {
		setName(name);
		setYearOfBirth(yearOfBirth);
	}
	
	//SETTERS
	public void setName(String name) throws OwnerException {
		if (name==null || !(name.replace(" ","").chars().allMatch(Character::isLetter)) ) throw new OwnerException("Ensure name contains characters only! "+name);
		else this.name=name;
	}
	public void setYearOfBirth(int yearOfBirth) throws OwnerException {
		if (yearOfBirth < 1000) throw new OwnerException("Year of birth must be 1000 or >. "+yearOfBirth);
		this.yearOfBirth=yearOfBirth;
	}
	
	//GETTERS
	public String getName() {
		return name;
	}
	public int getYearOfBirth() {
		return yearOfBirth;
	}
	
	@Override
	public String toString() {
		return "["+name+' '+yearOfBirth+"]"; 
	}
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Owner)) return false;
		
		Owner ownerO = (Owner) o;
		if (ownerO.getName().equals(name) && ownerO.getYearOfBirth()==yearOfBirth) return true;
		else return false;
	}
	@Override
	public int hashCode() {
		String stringYOB = Integer.toString(yearOfBirth);
		int result = stringYOB.hashCode();
		result = 31*result+name.hashCode();
		return result;
	}
}
