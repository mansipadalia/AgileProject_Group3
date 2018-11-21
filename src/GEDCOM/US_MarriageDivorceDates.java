package GEDCOM;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_MarriageDivorceDates {

	// US02
	public static List<Error> birthBeforeMarriage(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getFamilyList()) {
			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;
			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;

			String[] IDs = new String[] { husbandID, wifeID };

			for (String id : IDs) {

				Predicate<Record> byId = x -> x.getProperty(PropertyType.id).getValue().equals(id);
				List<Record> res = p.getIndividualList().stream().filter(byId).collect(Collectors.<Record>toList());

				if (id != null && ((LocalDate) res.get(0).getProperty(PropertyType.birthday).getValue())
						.isAfter((LocalDate) i.getProperty(PropertyType.married).getValue())) {

					error = new Error();
					error.setErrorType(ErrorType.ERROR);
					error.setRecordType(RecordType.INDIVIDUAL);
					error.setUserStoryNumber("US02");
					error.setLineNumber(i.getProperty(PropertyType.married).getLineNumber());
					error.setId(id);
					error.setMessage("Marriage Date " + (LocalDate) i.getProperty(PropertyType.married).getValue()
							+ " (" + i.getProperty(PropertyType.id).getValue() + ") occurs before Birth Date "
							+ (LocalDate) res.get(0).getProperty(PropertyType.birthday).getValue());
					errors.add(error);
				}
			}
		}
		return errors;
	}

	// US04
	public static List<Error> marriageBeforeDivorce(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getFamilyList()) {
			String familyID = (String) i.getProperty(PropertyType.id).getValue();

			LocalDate divorceDate = i.getProperty(PropertyType.divorced) != null
					? (LocalDate) i.getProperty(PropertyType.divorced).getValue()
					: null;
			LocalDate marriageDate = i.getProperty(PropertyType.married) != null
					? (LocalDate) i.getProperty(PropertyType.married).getValue()
					: null;

			if (divorceDate != null && marriageDate != null
					&& (marriageDate.isAfter(divorceDate) || marriageDate.equals(divorceDate))) {

				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.FAMILY);
				error.setUserStoryNumber("US04");
				error.setLineNumber(i.getProperty(PropertyType.divorced).getLineNumber());
				error.setId(familyID);
				error.setMessage("Divorce Date " + divorceDate + " occurs before Marriage Date " + marriageDate);
				errors.add(error);
			}
		}
		return errors;
	}

	// US 05
	public static List<Error> marriageBeforeDeath(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getFamilyList()) {
			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;
			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;
					
			LocalDate marriageDate = i.getProperty(PropertyType.married) != null
					? (LocalDate) i.getProperty(PropertyType.married).getValue()
					: null;

			String[] IDs = new String[] { husbandID, wifeID };

			for (String id : IDs) {

				Predicate<Record> byId = x -> x.getProperty(PropertyType.id).getValue().equals(id);
				List<Record> res = p.getIndividualList().stream().filter(byId).collect(Collectors.<Record>toList());

				if (id != null) {
					LocalDate DeathDate = res.get(0).getProperty(PropertyType.death) != null
							? (LocalDate) res.get(0).getProperty(PropertyType.death).getValue()
							: null;

					if (DeathDate != null && marriageDate != null
							&& (marriageDate).isAfter(DeathDate)) {

						error = new Error();
						error.setErrorType(ErrorType.ERROR);
						error.setRecordType(RecordType.INDIVIDUAL);
						error.setUserStoryNumber("US05");
						error.setLineNumber(i.getProperty(PropertyType.married).getLineNumber());
						error.setId((String) res.get(0).getProperty(PropertyType.id).getValue());
						error.setMessage("Marriage Date " + marriageDate + " (" + i.getProperty(PropertyType.id).getValue() 
								+ ") occurs after Death Date " + DeathDate);
						errors.add(error);
					}
				}
			}

		}
		return errors;
	}
	
	// US 10
	public static List<Error> marriageAfter14(Parser p) {

			List<Error> errors = new ArrayList<Error>();
			Error error = new Error();

			for (Record i : p.getFamilyList()) {
				String husbandID = i.getProperty(PropertyType.husbandID) != null
						? (String) i.getProperty(PropertyType.husbandID).getValue()
						: null;
				String wifeID = i.getProperty(PropertyType.wifeID) != null
						? (String) i.getProperty(PropertyType.wifeID).getValue()
						: null;
						
				LocalDate marriageDate = i.getProperty(PropertyType.married) != null
						? (LocalDate) i.getProperty(PropertyType.married).getValue()
						: null;

				String[] IDs = new String[] { husbandID, wifeID };

				for (String id : IDs) {

					Predicate<Record> byId = x -> x.getProperty(PropertyType.id).getValue().equals(id);
					List<Record> res = p.getIndividualList().stream().filter(byId).collect(Collectors.<Record>toList());

					if (id != null) {
						LocalDate birthDate = res.get(0).getProperty(PropertyType.birthday) != null
								? (LocalDate) res.get(0).getProperty(PropertyType.birthday).getValue()
								: null;

						//if (birthDate != null && marriageDate != null && (Period.between(birthDate, marriageDate).getYears()) <= 14) {
						if (birthDate != null && marriageDate != null && (birthDate.isBefore(marriageDate)) 
							&& (Period.between(birthDate, marriageDate).getYears()) <= 14) {
						
							error = new Error();
							error.setErrorType(ErrorType.ERROR);
							error.setRecordType(RecordType.FAMILY);
							error.setUserStoryNumber("US10");
							error.setLineNumber(i.getProperty(PropertyType.married).getLineNumber());
							error.setId((String) i.getProperty(PropertyType.id).getValue());
							error.setMessage("Marriage " + (LocalDate) i.getProperty(PropertyType.married).getValue()
									+ " (" + i.getProperty(PropertyType.id).getValue() + ") occurs before spouse (Birth Date: " + birthDate + ") is 14 years old.");
							errors.add(error);
						}
					}
				}

			}
			return errors;
		}
	
	//US 06
	public static List<Error> divorceBeforeDeath(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getFamilyList()) {
			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;
			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;
					
			LocalDate divorceDate = i.getProperty(PropertyType.divorced) != null
					? (LocalDate) i.getProperty(PropertyType.divorced).getValue()
					: null;

			String[] IDs = new String[] { husbandID, wifeID };

			for (String id : IDs) {

				Predicate<Record> byId = x -> x.getProperty(PropertyType.id).getValue().equals(id);
				List<Record> res = p.getIndividualList().stream().filter(byId).collect(Collectors.<Record>toList());

				if (id != null) {
					LocalDate DeathDate = res.get(0).getProperty(PropertyType.death) != null
							? (LocalDate) res.get(0).getProperty(PropertyType.death).getValue()
							: null;

					if (DeathDate != null && divorceDate != null
							&& (divorceDate).isAfter(DeathDate)) {

						error = new Error();
						error.setErrorType(ErrorType.ERROR);
						error.setRecordType(RecordType.FAMILY);
						error.setUserStoryNumber("US06");
						error.setLineNumber(i.getProperty(PropertyType.divorced).getLineNumber());
						error.setId((String) i.getProperty(PropertyType.id).getValue());
						error.setMessage("Divorce Date " + divorceDate + " (" + i.getProperty(PropertyType.id).getValue() 
								+ ") occurs after Death Date " + DeathDate + " of Individual " + res.get(0).getProperty(PropertyType.id).getValue());
						errors.add(error);
					}
				}
			}

		}
		return errors;
	}
	
	
}
