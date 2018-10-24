package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_MarriageDivorceDates {

	// US02
	@Test
	public void testBirthBeforeMarriage() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 6, 23), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1995, 4, 18), 4));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1998, 10, 7), 8));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_MarriageDivorceDates.birthBeforeMarriage(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US02");
		error.setLineNumber(8);
		error.setId("I01");
		error.setMessage("Marriage Date 1998-10-07 (F01) occurs before Birth Date 2000-06-23");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US02
	@Test
	public void testBirthBeforeMarriage_Nulls() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1998, 10, 7), 2));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_MarriageDivorceDates.birthBeforeMarriage(p);

		assertEquals(0, errors.size());
	}

	// US04
	@Test
	public void testMarriageBeforeDivorce() {

		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2009, 01, 01), 2));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2009, 01, 01), 3));
		FList.add(family);

		Parser p = new Parser(null, FList);
		
		List<Error> errorSameMarDivDates = US_MarriageDivorceDates.marriageBeforeDivorce(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US04");
		error.setLineNumber(3);
		error.setId("F01");
		error.setMessage("Divorce Date 2009-01-01 occurs before Marriage Date 2009-01-01");

		assertEquals(error.toString(), errorSameMarDivDates.get(0).toString());
		
		family = new Record();
		family.setProperty(PropertyType.id, new Property("F02", 4));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2015, 01, 01), 5));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2013, 01, 01), 6));
		FList.add(family);
		
		List<Error> errorMarBeforDiv = US_MarriageDivorceDates.marriageBeforeDivorce(p);

		Error errors = new Error();
		errors.setErrorType(ErrorType.ERROR);
		errors.setRecordType(RecordType.FAMILY);
		errors.setUserStoryNumber("US04");
		errors.setLineNumber(6);
		errors.setId("F02");
		errors.setMessage("Divorce Date 2013-01-01 occurs before Marriage Date 2015-01-01");

		assertEquals(errors.toString(), errorMarBeforDiv.get(1).toString());	
	}

	// US04
	@Test
	public void testMarriageBeforeDivorce_Nulls() {

		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		FList.add(family);

		Parser p = new Parser(null, FList);
		
		List<Error> errorId = US_MarriageDivorceDates.marriageBeforeDivorce(p);
		assertEquals(0, errorId.size());
		
		family = new Record();
		family.setProperty(PropertyType.id, new Property("F02", 2));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1996, 01, 01), 3));
		FList.add(family);

		List<Error> errorMarried = US_MarriageDivorceDates.marriageBeforeDivorce(p);
		assertEquals(0, errorMarried.size());
		
		family = new Record();
		family.setProperty(PropertyType.id, new Property("F03", 4));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1995, 01, 01), 5));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(1996, 01, 01), 6));
		FList.add(family);

		List<Error> errorDivorced = US_MarriageDivorceDates.marriageBeforeDivorce(p);
		assertEquals(0, errorDivorced.size());
	}

	// US05
	@Test
	public void testMarriageBeforeDeath() {

		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2010, 01, 01), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		IList.add(individual);

		Record family = new Record();
		
		family.setProperty(PropertyType.id, new Property("F01", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2015, 01, 01), 8));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		
		List<Error> errors = US_MarriageDivorceDates.marriageBeforeDeath(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US05");
		error.setLineNumber(8);
		error.setId("F01");
		error.setMessage("Marriage Date 2015-01-01 (F01) occurs after Death Date 2010-01-01");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US05
	@Test
	public void testMarriageBeforeDeath_Nulls() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1998, 10, 7), 2));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		
		List<Error> errors = US_MarriageDivorceDates.marriageBeforeDeath(p);
		assertEquals(0, errors.size());
	
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2016, 01, 01), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		IList.add(individual);

		family = new Record();
		family.setProperty(PropertyType.id, new Property("F02", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2015, 01, 01), 8));
		FList.add(family);

		List<Error> errorDeath = US_MarriageDivorceDates.marriageBeforeDeath(p);
		assertEquals(0, errorDeath.size());
	}
	
	// US10
	@Test
	public void testMarriageAfter14() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
		
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2003, 6, 01), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2003, 8, 01), 4));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2009, 01, 01), 8));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		
		List<Error> errors = US_MarriageDivorceDates.marriageAfter14(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US10");
		error.setLineNumber(8);
		error.setId("F01");
		error.setMessage("Marriage 2009-01-01 (F01) occurs before spouse (Birth Date: 2003-06-01) is 14 years old.");

		assertEquals(error.toString(), errors.get(0).toString());	
	}
	
	// US10
	@Test
	public void testMarriageAfter14_Nulls() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1998, 10, 7), 2));
		FList.add(family);

		Parser p = new Parser(IList, FList);
			
		List<Error> errors = US_MarriageDivorceDates.marriageAfter14(p);
		assertEquals(0, errors.size());
		
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 01, 01), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		IList.add(individual);

		family = new Record();
		family.setProperty(PropertyType.id, new Property("F02", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2015, 01, 01), 8));
		FList.add(family);

		List<Error> errorDeath = US_MarriageDivorceDates.marriageAfter14(p);
		assertEquals(0, errorDeath.size());
	}

}
