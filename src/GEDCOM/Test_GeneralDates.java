package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_GeneralDates {

	// US01
	@Test
	public void testdatesBeforeCurrentDate_birthDate() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2019, 4, 18), 2));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_GeneralDates.datesBeforeCurrentDate(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US01");
		error.setLineNumber(2);
		error.setId("I02");
		error.setMessage("Birth Date (2019-04-18) occurs in the future");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US01
	@Test
	public void testdatesBeforeCurrentDate_deathDate() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 1));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2019, 4, 18), 2));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_GeneralDates.datesBeforeCurrentDate(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US01");
		error.setLineNumber(2);
		error.setId("I02");
		error.setMessage("Death Date (2019-04-18) occurs in the future");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US01
	@Test
	public void testdatesBeforeCurrentDate_marriageDate() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.husbandID, new Property("I01", 2));
		family.setProperty(PropertyType.wifeID, new Property("I02", 3));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2020, 10, 7), 4));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_GeneralDates.datesBeforeCurrentDate(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US01");
		error.setLineNumber(4);
		error.setId("F01");
		error.setMessage("Marriage Date (2020-10-07) occurs in the future");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US01
	@Test
	public void testdatesBeforeCurrentDate_divorceDate() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.husbandID, new Property("I01", 2));
		family.setProperty(PropertyType.wifeID, new Property("I02", 3));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2020, 10, 7), 4));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_GeneralDates.datesBeforeCurrentDate(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US01");
		error.setLineNumber(4);
		error.setId("F01");
		error.setMessage("Divorce Date (2020-10-07) occurs in the future");

		assertEquals(error.toString(), errors.get(0).toString());
	}

}
