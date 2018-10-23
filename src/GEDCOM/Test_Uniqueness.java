package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_Uniqueness {
	
	//US23
	@Test
	public void testuniqueNameBirthDate() {
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
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20),8 ));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Uniqueness.uniqueNameBirthDate(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US23");
		error.setLineNumber(1);
		error.setId("I01");
		error.setMessage("Name Sophia Smithand  Birthday 1992-02-20 is same as 4: I02: Sophia Smith and Birthday 1992-02-20");
		assertEquals(error.toString(), errors.get(0).toString());
	}

}
