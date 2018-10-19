package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_BirthDeathDates {

	// US03
	@Test
	public void testBirthBeforeDeath() {
		List<Record> IList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 6, 23), 2));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2012, 2, 7), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 4));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1886, 4, 12), 5));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(1880, 1, 22), 6));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 7));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2005, 7, 20), 8));
		IList.add(individual);

		Parser p = new Parser(IList, null);
		List<Error> errors = US_BirthDeathDates.birthBeforeDeath(p);
		
		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US03");
		error.setLineNumber(6);
		error.setId("I02");
		error.setMessage("Death Date 1880-01-22 occurs before Birth Date 1886-04-12");

		assertEquals(error.toString(),errors.get(0).toString());
	}

}
