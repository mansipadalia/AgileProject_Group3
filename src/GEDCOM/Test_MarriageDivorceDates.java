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
	public void testMarriageBeforeDivorce_1() {

		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2009, 01, 01), 2));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2009, 01, 01), 3));
		FList.add(family);

		Parser p = new Parser(null, FList);
		List<Error> errors = US_MarriageDivorceDates.marriageBeforeDivorce(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US04");
		error.setLineNumber(3);
		error.setId("F01");
		error.setMessage("Divorce Date 2009-01-01 occurs before Marriage Date 2009-01-01");

		assertEquals(error.toString(), errors.get(0).toString());
	
	}

	// US04
	@Test
	public void testMarriageBeforeDivorce_2() {

		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F02", 1));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2015, 01, 01), 2));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2013, 01, 01), 3));
		FList.add(family);

		Parser p = new Parser(null, FList);
		List<Error> errors = US_MarriageDivorceDates.marriageBeforeDivorce(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US04");
		error.setLineNumber(3);
		error.setId("F02");
		error.setMessage("Divorce Date 2013-01-01 occurs before Marriage Date 2015-01-01");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US04
	@Test
	public void testMarriageBeforeDivorce_Nulls_1() {

		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		FList.add(family);

		Parser p = new Parser(null, FList);
		List<Error> errors = US_MarriageDivorceDates.marriageBeforeDivorce(p);

		assertEquals(0, errors.size());

	}

	// US04
	@Test
	public void testMarriageBeforeDivorce_Nulls_2() {

		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1996, 01, 01), 8));
		FList.add(family);

		Parser p = new Parser(null, FList);
		List<Error> errors = US_MarriageDivorceDates.marriageBeforeDivorce(p);

		assertEquals(0, errors.size());

	}

	// US04
	@Test
	public void testMarriageBeforeDivorce_Nulls_3() {

		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F02", 1));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(1996, 01, 01), 2));
		FList.add(family);

		Parser p = new Parser(null, FList);
		List<Error> errors = US_MarriageDivorceDates.marriageBeforeDivorce(p);

		assertEquals(0, errors.size());

	}

	// US05
	@Test
	public void testMarriageBeforeDeath_1() {

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
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US05");
		error.setLineNumber(8);
		error.setId("I01");
		error.setMessage("Marriage Date 2015-01-01 (F01) occurs after Death Date 2010-01-01");

		assertEquals(error.toString(), errors.get(0).toString());
	}
	
	// US05
		@Test
		public void testMarriageBeforeDeath_2() {

			List<Record> IList = new ArrayList<Record>();
			List<Record> FList = new ArrayList<Record>();

			Record individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I01", 1));
			individual.setProperty(PropertyType.death, new Property(LocalDate.of(2016, 01, 01), 2));
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

			assertEquals(0, errors.size());
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
	}

}
