package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_Uniqueness {
	
	// US23
	@Test
	public void testuniqueNameBirthDateError() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.name, new Property("Sophia Smith", 2));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 4));
		individual.setProperty(PropertyType.name, new Property("Sophia Smith", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 8));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Uniqueness.uniqueNameBirthDate(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US23");
		error.setLineNumber(1);
		error.setId("I01");
		error.setMessage("Name Sophia Smith and  Birthday 1992-02-20 is same as 4: I02: Sophia Smith and Birthday 1992-02-20");
		assertEquals(error.toString(), errors.get(0).toString());
	}
	
	public void testuniqueNameBirthDateSuccess_1() {

		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.name, new Property("Sophia Smith", 2));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1990, 1, 22), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 4));
		individual.setProperty(PropertyType.name, new Property("John Smith", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 6));
		IList.add(individual);
		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Uniqueness.uniqueNameBirthDate(p);
		assertEquals(0, errors.size());
	}

	public void testuniqueNameBirthDateSuccess_2() {

		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.name, new Property("Sophia Smith", 2));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1990, 1, 22), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 4));
		individual.setProperty(PropertyType.name, new Property("Sophia Smith", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 6));
		IList.add(individual);
		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Uniqueness.uniqueNameBirthDate(p);
		assertEquals(0, errors.size());
	}
	
	public void testuniqueNameBirthDateSuccess_3() {

		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.name, new Property("Sophia Smith", 2));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 4));
		individual.setProperty(PropertyType.name, new Property("John Smith", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 6));
		IList.add(individual);
		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Uniqueness.uniqueNameBirthDate(p);
		assertEquals(0, errors.size());
	}


//US25

@Test
public void testuniqueFirstName_WithoutchildrenError() {
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F05", 1));
	family.setProperty(PropertyType.husbandID, new Property("I01", 2));
	family.setProperty(PropertyType.husbandName, new Property("Steffi Johnson", 3));
	family.setProperty(PropertyType.wifeID, new Property("I02", 4));
	family.setProperty(PropertyType.wifeName, new Property("Steffi Smith", 5));
	FList.add(family);

	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstName(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(2);
	error.setId("F05");
	error.setMessage("Husband's First Name Steffi is same as Wife's First Name Steffi");
	assertEquals(error.toString(), errors.get(0).toString());
}
@Test
public void testuniqueFirstName_WithoutchildrenErrorSuccess() {
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F05", 1));
	family.setProperty(PropertyType.husbandID, new Property("I01", 2));
	family.setProperty(PropertyType.husbandName, new Property("Johnson Smith", 3));
	family.setProperty(PropertyType.wifeID, new Property("I02", 4));
	family.setProperty(PropertyType.wifeName, new Property("Steffi Smith", 5));
	FList.add(family);

	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstName(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(2);
	error.setId("F05");
	assertEquals(0, errors.size());
}

public void testuniqueFirstName_withChildrenError_01() {
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F02", 1));
	
	family.setProperty(PropertyType.husbandName, new Property("Sophia Johnson", 2));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.name, new Property("Sophia Smith", 4));
	IList.add(individual);
	

	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstName(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F02");
	error.setMessage("More than one individual in a family have the same name Sophia");
	assertEquals(error.toString(), errors.get(0).toString());
}

public void testuniqueFirstName_withChildrenSuccess_01() {
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F02", 1));
	
	family.setProperty(PropertyType.husbandName, new Property("Smith Johnson", 2));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.name, new Property("Sophia Smith", 4));
	IList.add(individual);
	

	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstName(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F02");
	assertEquals(0, errors.size());
	
}

public void testuniqueFirstName_withChildrenError_02() {
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F02", 1));
	
	family.setProperty(PropertyType.wifeName, new Property("Sophia Johnson", 2));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.name, new Property("Sophia Smith", 4));
	IList.add(individual);
	

	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstName(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F02");
	error.setMessage("More than one individual in a family have the same name Sophia");
	assertEquals(error.toString(), errors.get(0).toString());
}

public void testuniqueFirstName_withChildrenSuccess_02() {
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F02", 1));
	
	family.setProperty(PropertyType.wifeName, new Property("Smith Johnson", 2));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.name, new Property("Sophia Smith", 4));
	IList.add(individual);
	

	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstName(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F02");
	assertEquals(0, errors.size());
}

public void testuniqueFirstName_withChildrenError_03() {
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F07", 1));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.name, new Property("Alex Smith", 2));
	IList.add(individual);
	Record individual1 = new Record();
	individual1.setProperty(PropertyType.name, new Property("Alex Smith", 3));
	IList.add(individual1);
	

	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstName(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F07");
	error.setMessage("More than one individual in a family have the same name Alex");
	assertEquals(error.toString(), errors.get(0).toString());
}

public void testuniqueFirstName_withChildrenErrorSuccess_03() {
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F07", 1));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.name, new Property("Smith Alex", 2));
	IList.add(individual);
	Record individual1 = new Record();
	individual1.setProperty(PropertyType.name, new Property("Alex Smith", 3));
	IList.add(individual1);
	

	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstName(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F07");
	assertEquals(0, errors.size());
	
}

}	
