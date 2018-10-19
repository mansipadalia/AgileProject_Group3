package GEDCOM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_GeneralDates {

	// US01
	public static List<Error> datesBeforeCurrentDate(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getIndividualList()) {

			LocalDate birthDate = i.getProperty(PropertyType.birthday) != null
					? (LocalDate) i.getProperty(PropertyType.birthday).getValue()
					: null;
			LocalDate deathDate = i.getProperty(PropertyType.death) != null
					? (LocalDate) i.getProperty(PropertyType.death).getValue()
					: null;

			if (birthDate != null && birthDate.isAfter(LocalDate.now())) {

				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.INDIVIDUAL);
				error.setUserStoryNumber("US01");
				error.setLineNumber(i.getProperty(PropertyType.birthday).getLineNumber());
				error.setId((String) i.getProperty(PropertyType.id).getValue());
				error.setMessage("Birth Date (" + birthDate + ") occurs in the future");
				errors.add(error);
			}
			if (deathDate != null && deathDate.isAfter(LocalDate.now())) {

				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.INDIVIDUAL);
				error.setUserStoryNumber("US01");
				error.setLineNumber(i.getProperty(PropertyType.death).getLineNumber());
				error.setId((String) i.getProperty(PropertyType.id).getValue());
				error.setMessage("Death Date (" + deathDate + ") occurs in the future");
				errors.add(error);
			}
		}

		for (Record i : p.getFamilyList()) {

			LocalDate marriedDate = i.getProperty(PropertyType.married) != null
					? (LocalDate) i.getProperty(PropertyType.married).getValue()
					: null;
			LocalDate divorcedDate = i.getProperty(PropertyType.divorced) != null
					? (LocalDate) i.getProperty(PropertyType.divorced).getValue()
					: null;

			if (marriedDate != null && marriedDate.isAfter(LocalDate.now())) {

				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.FAMILY);
				error.setUserStoryNumber("US01");
				error.setLineNumber(i.getProperty(PropertyType.married).getLineNumber());
				error.setId((String) i.getProperty(PropertyType.id).getValue());
				error.setMessage("Marriage Date (" + marriedDate + ") occurs in the future");
				errors.add(error);
			}
			if (divorcedDate != null && divorcedDate.isAfter(LocalDate.now())) {

				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.FAMILY);
				error.setUserStoryNumber("US01");
				error.setLineNumber(i.getProperty(PropertyType.divorced).getLineNumber());
				error.setId((String) i.getProperty(PropertyType.id).getValue());
				error.setMessage("Divorce Date (" + divorcedDate + ") occurs in the future");
				errors.add(error);

			}
		}

		return errors;
	}

}
