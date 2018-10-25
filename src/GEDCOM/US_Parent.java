package GEDCOM;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_Parent {
	// US08
	public static List<Error> birthBeforeMarriageOfParents(Parser p) {

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

			if (i.getProperty(PropertyType.children) != null) {
				Object[] ChildrenIdList = ((List<String>) i.getProperty(PropertyType.children).getValue()).toArray();
				String[] ChildrenIds = Arrays.copyOf(ChildrenIdList, ChildrenIdList.length, String[].class);

				for (int j = 0; j < ChildrenIds.length; j++) {

					String id_1 = ChildrenIds[j];
					Predicate<Record> byiId = x -> x.getProperty(PropertyType.id).getValue().equals(id_1);
					List<Record> resi = p.getIndividualList().stream().filter(byiId)
							.collect(Collectors.<Record>toList());
					LocalDate bdate_j = resi.get(0).getProperty(PropertyType.birthday) != null
							? (LocalDate) resi.get(0).getProperty(PropertyType.birthday).getValue()
							: null;
					if (marriageDate != null && bdate_j != null && (marriageDate.isAfter(bdate_j))) {

						error = new Error();
						error.setErrorType(ErrorType.ERROR);
						error.setRecordType(RecordType.FAMILY);
						error.setUserStoryNumber("US08");
						error.setLineNumber(i.getProperty(PropertyType.married).getLineNumber());
						error.setId(familyID);
						error.setMessage("Difference between birthdate (" + bdate_j + ") and marriage Date ("
								+ marriageDate + ") is not valid");
						errors.add(error);
					}
					if (divorceDate != null && bdate_j != null && (bdate_j.isAfter(divorceDate))) {
						long diffMonth = ChronoUnit.DAYS.between(divorceDate, bdate_j);

						if (diffMonth > (9 * 30)) {
							error = new Error();
							error.setErrorType(ErrorType.ERROR);
							error.setRecordType(RecordType.FAMILY);
							error.setUserStoryNumber("US08");
							error.setLineNumber(i.getProperty(PropertyType.divorced).getLineNumber());
							error.setId(id_1);
							error.setMessage("Difference between birthdate (" + bdate_j + ") and divorce Date ("
									+ divorceDate + ") is not valid");
							errors.add(error);
						}
					}

				}
			}
		}
		return errors;
	}

	// US09
	public static List<Error> birthBeforeDeathOfParents(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();
		LocalDate deathDate_W=null, deathDate_H=null;

		for (Record i : p.getFamilyList()) {
			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;
			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;

			if (husbandID != null) {
				Predicate<Record> byHId = x -> x.getProperty(PropertyType.id).getValue().equals(husbandID);
				List<Record> res_h = p.getIndividualList().stream().filter(byHId).collect(Collectors.<Record>toList());
				deathDate_H = res_h.get(0).getProperty(PropertyType.death) != null
						? (LocalDate) res_h.get(0).getProperty(PropertyType.death).getValue()
						: null;
			}

			if (wifeID != null) {
				Predicate<Record> byWId = x -> x.getProperty(PropertyType.id).getValue().equals(wifeID);
				List<Record> res_w = p.getIndividualList().stream().filter(byWId).collect(Collectors.<Record>toList());
				deathDate_W = res_w.get(0).getProperty(PropertyType.death) != null
						? (LocalDate) res_w.get(0).getProperty(PropertyType.death).getValue()
						: null;
			}

			if (i.getProperty(PropertyType.children) != null) {
				Object[] ChildrenIdList = ((List<String>) i.getProperty(PropertyType.children).getValue()).toArray();
				String[] ChildrenIds = Arrays.copyOf(ChildrenIdList, ChildrenIdList.length, String[].class);

				for (int j = 0; j < ChildrenIds.length; j++) {

					String id = ChildrenIds[j];
					Predicate<Record> byiId = x -> x.getProperty(PropertyType.id).getValue().equals(id);
					List<Record> resi = p.getIndividualList().stream().filter(byiId)
							.collect(Collectors.<Record>toList());
					LocalDate bdate_j = resi.get(0).getProperty(PropertyType.birthday) != null
							? (LocalDate) resi.get(0).getProperty(PropertyType.birthday).getValue()
							: null;
					if (bdate_j != null && deathDate_W != null && (bdate_j.isAfter(deathDate_W))) {

						error = new Error();
						error.setErrorType(ErrorType.ERROR);
						error.setRecordType(RecordType.INDIVIDUAL);
						error.setUserStoryNumber("US09");
						error.setLineNumber(resi.get(0).getProperty(PropertyType.birthday).getLineNumber());
						error.setId(ChildrenIds[j]);
						error.setMessage("Birth date (" + bdate_j + ") after death of Mother (ID: " + wifeID + ") ("
								+ deathDate_W + ")");
						errors.add(error);
					}
					if (bdate_j != null && deathDate_H != null && (bdate_j.isAfter(deathDate_H))) {
						long diffMonth = ChronoUnit.DAYS.between(deathDate_H, bdate_j);

						if (diffMonth > (9 * 30)) {
							error = new Error();
							error.setErrorType(ErrorType.ERROR);
							error.setRecordType(RecordType.INDIVIDUAL);
							error.setUserStoryNumber("US09");
							error.setLineNumber(resi.get(0).getProperty(PropertyType.birthday).getLineNumber());
							error.setId(ChildrenIds[j]);
							error.setMessage(
									"Birth date (" + bdate_j + ") after more than 9 months of death of Father (ID: "
											+ husbandID + ") (" + deathDate_H + ")");
							errors.add(error);
						}
					}
				}
			}
		}
		return errors;
	}
}
