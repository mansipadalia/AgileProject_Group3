package GEDCOM;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_Gender {

	// US16
	@Test
	public void testMaleLastNames_Error() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
	
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.name, new Property("Louis /Pete/", 4));
		individual.setProperty(PropertyType.gender, new Property("M", 5));
		IList.add(individual);
		individual = new Record();
		
		Record family = new Record();
		
		family.setProperty(PropertyType.id, new Property("F01", 7));
		List<String> children = new ArrayList<String>();
		children.add("I02");
		family.setProperty(PropertyType.husbandID, new Property("I01", 8));		
		family.setProperty(PropertyType.husbandName, new Property("Matt /Frank/", 9));
		family.setProperty(PropertyType.children, new Property(children, 10));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		
		List<Error> Errors = US_Gender.maleLastNames(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US16");
		error.setLineNumber(3);
		error.setId("F01");
		error.setMessage("Child (I02) has last name (Pete) different from his father's last name (Frank).");

		assertEquals(error.toString(), Errors.get(0).toString());
	}
	
	// US16
	@Test
	public void testMaleLastNames_Valid() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
	
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.gender, new Property("M", 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 4));
		individual.setProperty(PropertyType.name, new Property("Louis /Frank/", 5));
		individual.setProperty(PropertyType.gender, new Property("M", 6));
		IList.add(individual);
		individual = new Record();
		
		Record family = new Record();
		
		family.setProperty(PropertyType.id, new Property("F01", 7));
		List<String> children = new ArrayList<String>();
		children.add("I02");
		family.setProperty(PropertyType.husbandID, new Property("I01", 8));
		family.setProperty(PropertyType.husbandName, new Property("Matt /Frank/", 9));
		family.setProperty(PropertyType.children, new Property(children, 10));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		
		List<Error> Errors = US_Gender.maleLastNames(p);
		assertEquals(0, Errors.size());
	}
}
