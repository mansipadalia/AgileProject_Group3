package GEDCOM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_BirthDeathDates {

	// US03
	public static List<Error> birthBeforeDeath(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getIndividualList()) {
			LocalDate deathDate = i.getProperty(PropertyType.death) != null
					? (LocalDate) i.getProperty(PropertyType.death).getValue()
					: null;

			if (deathDate != null && ((LocalDate) i.getProperty(PropertyType.birthday).getValue()).isAfter(deathDate)) {

				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.INDIVIDUAL);
				error.setUserStoryNumber("US03");
				error.setLineNumber(i.getProperty(PropertyType.death).getLineNumber());
				error.setId((String) i.getProperty(PropertyType.id).getValue());
				error.setMessage("Death Date " + deathDate + " occurs before Birth Date "
						+ (LocalDate) i.getProperty(PropertyType.birthday).getValue());
				errors.add(error);
			}
		}
		return errors;
	}

}
