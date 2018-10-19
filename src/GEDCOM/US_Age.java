package GEDCOM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_Age {

	// US07
	public static List<Error> lessThanOneFiftyAge(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getIndividualList()) {

			LocalDate birthDate = i.getProperty(PropertyType.birthday) != null
					? (LocalDate) i.getProperty(PropertyType.birthday).getValue()
					: null;

			if (birthDate!=null && (int) i.getProperty(PropertyType.age).getValue() > 150) {

				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.INDIVIDUAL);
				error.setUserStoryNumber("US07");
				error.setLineNumber(i.getProperty(PropertyType.birthday).getLineNumber());
				error.setId((String) i.getProperty(PropertyType.id).getValue());
				error.setMessage("More than 150 years old, Birth Date (" + birthDate + ")");
				errors.add(error);
			}
		}
		return errors;
	}

}
