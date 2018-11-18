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
	@Test
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
	@Test
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
	@Test
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
public void testuniqueFirstNameBirthDate_error(){
	
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F01", 1));
	List<String> children = new ArrayList<String>();
	children.add("I01");
	children.add("I02");
	family.setProperty(PropertyType.children, new Property(children, 2));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I01", 3));
	individual.setProperty(PropertyType.name, new Property("Sophia /Smith/", 4));
	individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 02, 20), 5));
	IList.add(individual);
	individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I02", 6));
	individual.setProperty(PropertyType.name, new Property("Sophia /Johnson/", 7));
	individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 02, 20), 8));
	IList.add(individual);
	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstNameBirthDate(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F01");
	error.setMessage("More than one child in a family have the same first name Sophia and birthday 1992-02-20");
	assertEquals(error.toString(), errors.get(0).toString());
	}
@Test
public void testuniqueFirstNameBirthDate_Success_1(){
	
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F01", 1));
	List<String> children = new ArrayList<String>();
	children.add("I01");
	children.add("I02");
	family.setProperty(PropertyType.children, new Property(children, 2));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I01", 3));
	individual.setProperty(PropertyType.name, new Property("Sophia /Smith/", 4));
	individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1996, 03, 22), 5));
	IList.add(individual);
	individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I02", 6));
	individual.setProperty(PropertyType.name, new Property("Johnson /Smith/", 7));
	individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 02, 20), 8));
	IList.add(individual);
	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstNameBirthDate(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F01");
	assertEquals(0, errors.size());
	}
@Test
public void testuniqueFirstNameBirthDate_Success_2(){
	
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F01", 1));
	List<String> children = new ArrayList<String>();
	children.add("I01");
	children.add("I02");
	family.setProperty(PropertyType.children, new Property(children, 2));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I01", 3));
	individual.setProperty(PropertyType.name, new Property("Johnson /Smith/", 4));
	individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1996, 03, 22), 5));
	IList.add(individual);
	individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I02", 6));
	individual.setProperty(PropertyType.name, new Property("Johnson /Smith/", 7));
	individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 02, 20), 8));
	IList.add(individual);
	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstNameBirthDate(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F01");
	assertEquals(0, errors.size());
	}
@Test
public void testuniqueFirstNameBirthDate_Success_3(){
	
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();

	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F01", 1));
	List<String> children = new ArrayList<String>();
	children.add("I01");
	children.add("I02");
	family.setProperty(PropertyType.children, new Property(children, 2));
	FList.add(family);
	Record individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I01", 3));
	individual.setProperty(PropertyType.name, new Property("Sophia /Smith/", 4));
	individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 02, 20), 5));
	IList.add(individual);
	individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I02", 6));
	individual.setProperty(PropertyType.name, new Property("Johnson /Smith/", 7));
	individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 02, 20), 8));
	IList.add(individual);
	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueFirstNameBirthDate(p);

	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US25");
	error.setLineNumber(1);
	error.setId("F01");
	assertEquals(0, errors.size());
	}
@Test
public void testuniqueIds_Success_1(){
	
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();
	
	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F01", 1));
	family.setProperty(PropertyType.id, new Property("F02", 2));
	FList.add(family);
	
	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueIds(p);
	
	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US22");
	error.setLineNumber(1);
	error.setId("F01");
	assertEquals(0, errors.size());
}

@Test
public void testuniqueIds_Success_2(){
	
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();
	
	Record individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I01", 1));
	individual.setProperty(PropertyType.id, new Property("I02", 2));
	IList.add(individual);
	
	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueIds(p);
	
	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.INDIVIDUAL);
	error.setUserStoryNumber("US22");
	error.setLineNumber(1);
	error.setId("F01");
	assertEquals(0, errors.size());
}
@Test
public void testuniqueids_error_1(){
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();
	
	Record family = new Record();
	family.setProperty(PropertyType.id, new Property("F01", 1));
	FList.add(family);
	family = new Record();
	family.setProperty(PropertyType.id, new Property("F01", 2));
	FList.add(family);
	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueIds(p);
	
	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.FAMILY);
	error.setUserStoryNumber("US22");
	error.setLineNumber(1);
	error.setId("F01");
	error.setMessage("More than one families have same IDs F01");
	assertEquals(error.toString(), errors.get(0).toString());
}

@Test
public void testuniqueids_error_2(){
	List<Record> IList = new ArrayList<Record>();
	List<Record> FList = new ArrayList<Record>();
	
	Record individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I01", 1));
	IList.add(individual);
	individual = new Record();
	individual.setProperty(PropertyType.id, new Property("I01", 2));
	IList.add(individual);
	Parser p = new Parser(IList, FList);
	List<Error> errors = US_Uniqueness.uniqueIds(p);
	
	Error error = new Error();
	error.setErrorType(ErrorType.ERROR);
	error.setRecordType(RecordType.INDIVIDUAL);
	error.setUserStoryNumber("US22");
	error.setLineNumber(1);
	error.setId("I01");
	error.setMessage("More than one Individuals have same IDs I01");
	assertEquals(error.toString(), errors.get(0).toString());
}
}	
