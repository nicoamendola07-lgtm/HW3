package edu.monmouth.hw3;
import java.io.*;
import java.util.*;
import edu.monmouth.vehicle.*;
import edu.monmouth.owner.*;

public class HW3 {

	public static void main(String[] args) {
		
		Properties prop = new Properties();
		
		// ESTABLISH property KEYS
		String fileName_key = "InFile";
		String key_val_delimiter_key="KeyValueDelimiter";
		String owner_vehicle_delimiter_key="DelimiterBetweenOverAndVehicle";
		String LogFileName_key = "LogFileName";
		
		// INITIALIZE property VALS
		String fileName_val="";
		String key_val_delimiter_val="";
		String owner_vehicle_delimiter_val="";
		String LogFileName_val = "";
		
		
		//LOAD INPUT STREAM
		try (InputStream in = new FileInputStream(args[HW3Constants.PROPFILE])) {
			prop.load(in);
			
			// ASSIGN INFILE NAME  (data set file)
			fileName_val = prop.getProperty(fileName_key);
			if (fileName_val==null) {
				System.err.println("Missing Data File: "+fileName_key);
				System.exit(HW3Constants.REDIRECTFAILURE);
			}
			// ASSIGN KEY VAL DELIMITER
			key_val_delimiter_val = prop.getProperty(key_val_delimiter_key);
			if (key_val_delimiter_val==null) {
				System.err.println("Missing Key/Val Delimiter: "+key_val_delimiter_key);
				System.exit(HW3Constants.REDIRECTFAILURE);
			}
			// ASSIGN OWNER VEHICLE  DELIMITER
			owner_vehicle_delimiter_val = prop.getProperty(owner_vehicle_delimiter_key);
			if (owner_vehicle_delimiter_val==null) {
				System.err.println("Missing Owner/Vehciel Delimiter: "+owner_vehicle_delimiter_key);
				System.exit(HW3Constants.REDIRECTFAILURE);
			}
			
			LogFileName_val = prop.getProperty(LogFileName_key);
			if (LogFileName_val==null) {
				System.err.println("Missing Outfile: "+LogFileName_key);
				System.exit(HW3Constants.REDIRECTFAILURE);
			}
		
		} catch (FileNotFoundException e) {
			System.err.println("Cannot locate properties file: "+e.getMessage());
			e.printStackTrace();
			System.exit(HW3Constants.REDIRECTFAILURE);
		} catch (IOException e) {
			System.err.println("Cannot locate properties file: "+e.getMessage());
			e.printStackTrace();
			System.exit(HW3Constants.REDIRECTFAILURE);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Cannot locate properties file: "+e.getMessage());
			e.printStackTrace();
			System.exit(HW3Constants.REDIRECTFAILURE);
		}
		
		//PRINT ALL PROPERTIES -- key + val
		prop.list(System.out);
		System.out.println();
		
		//CREATE HASHMAP
		Map<Owner,Vehicle> ownersAndVehicles = new HashMap<>();
	
		//OPEN THE DATA FILE & LOOP THRU EACH LINE
		try (Scanner sc = new Scanner(new FileInputStream(fileName_val))) {
			while (sc.hasNextLine()) {
				//Trim + check if empty
				String line = sc.nextLine().trim();
				if (line.isEmpty()) continue; 
				
				//SPLIT OWNER & VEHICLE (check #parts)
				String[] ownerVsVehicle = line.split(owner_vehicle_delimiter_val);
				if (ownerVsVehicle.length != HW3Constants.NUM_OWNERVSVEHICLE) {
					System.err.println("Incomplete Line!");
					continue;
				}
				
				//SPLIT OWNER AT DELIMITER (check #parts)
				String[] ownerDetails = ownerVsVehicle[HW3Constants.OWNERINFO].split(key_val_delimiter_val);
				if (ownerDetails.length != HW3Constants.REQ_OWNER_ARGS) {
					System.err.println("Incomplete owner details!");
					continue;
				}
				
				//SPLIT VEHICLE AT DELIMITER (check #parts)
				String[] vehicleDetails = ownerVsVehicle[HW3Constants.VEHICLEINFO].split(key_val_delimiter_val);
				if (vehicleDetails.length != HW3Constants.REQ_VEHICLE_ARGS) {
					System.err.println("Incomplete vehicle details!");
					continue;
				}
				
				// TRY TO BUILD OWNER
				Owner newOwner=null;
				try {
					String name = ownerDetails[HW3Constants.OWNER_NAME].trim();
					int yearOfBirth = Integer.parseInt(ownerDetails[HW3Constants.OWNER_YOB]);
					newOwner = new Owner(name,yearOfBirth);
				} catch (OwnerException e) {
					System.err.println("Skipping bad line! "+e.getMessage());
				}
				
				Vehicle newVehicle=null;
				try {
					char c = vehicleDetails[HW3Constants.VEHICLETYPE].trim().charAt(0);
					PowerTypes pt = PowerTypes.fromString(vehicleDetails[HW3Constants.VEHICLEPOWERSOURCE].trim());
					int val = Integer.parseInt(vehicleDetails[HW3Constants.VEHICLEVAL].trim());
					
					if (c=='c') newVehicle = new Car(pt,val);
					else if (c=='b') newVehicle = new Boat(pt,val);
					else System.err.println("Unknown type");
				} catch (CarException | BoatException | NumberFormatException | StringIndexOutOfBoundsException e) {
					System.err.println("Skipping bad line "+e.getMessage());
				}
				
				if (newOwner != null && newVehicle != null) ownersAndVehicles.put(newOwner, newVehicle);
				
			}//while -- (file has more data)
			
		} catch (FileNotFoundException ioe) {
			System.err.println("Couldn't locate the data file: "+fileName_val);
			System.exit(HW3Constants.REDIRECTFAILURE);
		}
		
		//PRINT HASHMAP DETAILS
		System.out.println("Total Pairs Mapped: "+ownersAndVehicles.size());
		for (Map.Entry<Owner, Vehicle> entry:ownersAndVehicles.entrySet()) {
			System.out.println("Key: "+entry.getKey()+ " Value: "+entry.getValue());
		}
		
		//MAKE REAL & FAKE OWNERS
		Owner exists=null;
		Owner DNE=null;
		try {
			exists = new Owner("Alan Turing",1912); 
			DNE = new Owner("Nico A",2007);
		} catch (OwnerException e) {
			System.err.println("Cannot make owner "+e.getMessage());
		}
		
		System.out.println("\nREAL OWNER:");
		//SEARCH & REMOVE REAL OWNER
		System.out.println(ownersAndVehicles.containsKey(exists));
		System.out.println(ownersAndVehicles.remove(exists));
		
		System.out.println("\nFAKE OWNER: ");
		//SEARCH & REMOVE FAKE OWNER
		System.out.println(ownersAndVehicles.containsKey(DNE));
		System.out.println(ownersAndVehicles.remove(DNE));
		
		//REDIRECT STDERR & STDOUT to logfile
		initialize(LogFileName_val);
	}//main
	
	public static void initialize(String LogFileName_val) {
		try {
			PrintStream st = new PrintStream(LogFileName_val);
			//System.setOut(st);
			System.setErr(st);
		} catch (FileNotFoundException ioe) {
			System.err.println("Cannot redirect stderr & stdout: "+ioe.getMessage());
			ioe.printStackTrace();
			System.exit(HW3Constants.REDIRECTFAILURE);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Cannot redirect stderr & stdout: "+e.getMessage());
			e.printStackTrace();
			System.exit(HW3Constants.REDIRECTFAILURE);
		}
	}
	
}//class
