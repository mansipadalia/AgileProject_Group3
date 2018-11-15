package GEDCOM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_Gender {

	public static List<Error> maleLastNames(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record f : p.getFamilyList()) {

			String husbandName = f.getProperty(PropertyType.husbandName) != null
					? (String) f.getProperty(PropertyType.husbandName).getValue()
					: null;

			String[] husbLastName = husbandName.split("/", 3);

			if (f.getProperty(PropertyType.children) != null) {
				@SuppressWarnings("unchecked")
				Object[] ChildrenList = ((List<String>) f.getProperty(PropertyType.children).getValue()).toArray();
				String[] ChildrenIds = Arrays.copyOf(ChildrenList, ChildrenList.length, String[].class);

				if (ChildrenIds.length >= 1) {
					for (int i = 0; i < ChildrenIds.length; i++) {

						String cId = ChildrenIds[i];

						Predicate<Record> bycId = x -> x.getProperty(PropertyType.id).getValue().equals(cId);
						List<Record> resC = p.getIndividualList().stream().filter(bycId)
								.collect(Collectors.<Record>toList());

						String sex = resC.get(0).getProperty(PropertyType.gender) != null
								? (String) resC.get(0).getProperty(PropertyType.gender).getValue()
								: null;

						if (sex.equals("M")) {

							String childrenName = resC.get(0).getProperty(PropertyType.name) != null
									? (String) resC.get(0).getProperty(PropertyType.name).getValue()
									: null;

							String[] childLastName = childrenName.split("/", 3);

							if (!(childLastName[1].equals(husbLastName[1]))) {

								error = new Error();
								error.setErrorType(ErrorType.ERROR);
								error.setRecordType(RecordType.FAMILY);
								error.setUserStoryNumber("US16");
								error.setLineNumber(resC.get(0).getProperty(PropertyType.id).getLineNumber());
								error.setId((String) f.getProperty(PropertyType.id).getValue());
								error.setMessage("Child (" + resC.get(0).getProperty(PropertyType.id).getValue()
										+ ") has last name (" + childLastName[1]
										+ ") different from his father's last name (" + husbLastName[1] + ").");
								errors.add(error);
							}
						}
					}
				}
			}
		}
		return errors;
	}

	// US21
	public static List<Error> correctGenderForRole(Parser p) {
		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getFamilyList()) {

			String familyID = (String) i.getProperty(PropertyType.id).getValue();

			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;
			Predicate<Record> byHId = x -> x.getProperty(PropertyType.id).getValue().equals(husbandID);
			List<Record> resH = p.getIndividualList().stream().filter(byHId).collect(Collectors.<Record>toList());
			String husbandGender = husbandID != null ? (resH.get(0).getProperty(PropertyType.gender) != null
					? (String) resH.get(0).getProperty(PropertyType.gender).getValue()
					: null) : null;

			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;
			Predicate<Record> byWId = x -> x.getProperty(PropertyType.id).getValue().equals(wifeID);
			List<Record> resW = p.getIndividualList().stream().filter(byWId).collect(Collectors.<Record>toList());
			String wifeGender = wifeID != null ? (resW.get(0).getProperty(PropertyType.gender) != null
					? (String) resW.get(0).getProperty(PropertyType.gender).getValue()
					: null) : null;

			if (husbandGender != null && (!husbandGender.equals("M"))) {
				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.FAMILY);
				error.setUserStoryNumber("US21");
				error.setLineNumber(resH.get(0).getProperty(PropertyType.gender).getLineNumber());
				error.setId(husbandID);
				error.setMessage("Gender - " + husbandGender + " cannot be husband for family " + familyID);
				errors.add(error);
			}

			if (wifeGender != null && (!wifeGender.equals("F"))) {
				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.FAMILY);
				error.setUserStoryNumber("US21");
				error.setLineNumber(resW.get(0).getProperty(PropertyType.gender).getLineNumber());
				error.setId(wifeID);
				error.setMessage("Gender - " + wifeGender + " cannot be wife for family " + familyID);
				errors.add(error);
			}
		}
		return errors;
	}
}
