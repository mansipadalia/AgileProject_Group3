package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_Age {

	// US07
	@Test
	public void testlessThanOneFiftyAge() {
		List<Record> IList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I20", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1800, 01, 01), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I21", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1999, 01, 01), 4));
		IList.add(individual);

		Parser p = new Parser(IList, null);
		List<Error> errors = US_Age.lessThanOneFiftyAge(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US07");
		error.setLineNumber(2);
		error.setId("I20");
		error.setMessage("More than 150 years old, Birth Date (1800-01-01)");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US07
	@Test
	public void testlessThanOneFiftyAge_BdateNull() {
		List<Record> IList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I20", 1));
		IList.add(individual);

		Parser p = new Parser(IList, null);
		List<Error> errors = US_Age.lessThanOneFiftyAge(p);

		assertEquals(0, errors.size());
	}

}
