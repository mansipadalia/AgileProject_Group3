package GEDCOM;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_Sibling {

	// US13
	@SuppressWarnings("unchecked")
	public static List<Error> siblingSpacing(Parser p) {
		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record c : p.getFamilyList()) {
			if (c.getProperty(PropertyType.children) != null) {
				Object[] ChildrenIdList = ((List<String>) c.getProperty(PropertyType.children).getValue()).toArray();
				String[] ChildrenIds = Arrays.copyOf(ChildrenIdList, ChildrenIdList.length, String[].class);

				if (ChildrenIds.length > 1) {
					for (int i = 0; i < ChildrenIds.length - 1; i++) {

						String id_1 = ChildrenIds[i];
						Predicate<Record> byiId = x -> x.getProperty(PropertyType.id).getValue().equals(id_1);
						List<Record> resi = p.getIndividualList().stream().filter(byiId)
								.collect(Collectors.<Record>toList());
						LocalDate bdate_i = (LocalDate) resi.get(0).getProperty(PropertyType.birthday).getValue();
						

						for (int j = i + 1; j < ChildrenIds.length; j++) {

							String id_2 = ChildrenIds[j];
							Predicate<Record> byjId = x -> x.getProperty(PropertyType.id).getValue().equals(id_2);
							List<Record> resj = p.getIndividualList().stream().filter(byjId)
									.collect(Collectors.<Record>toList());
							LocalDate bdate_j = (LocalDate) resj.get(0).getProperty(PropertyType.birthday).getValue();
							

							long diffDays = ChronoUnit.DAYS.between(bdate_i, bdate_j);

							if (diffDays > 2 && diffDays < (8 * 30)) {
								error = new Error();
								error.setErrorType(ErrorType.ERROR);
								error.setRecordType(RecordType.FAMILY);
								error.setUserStoryNumber("US13");
								error.setLineNumber(resj.get(0).getProperty(PropertyType.birthday).getLineNumber());
								error.setId(id_2);
								error.setMessage("Difference between birthdate (" + bdate_j + ") with sibling " + id_1
										+ " (" + bdate_i + ") is not valid");
								errors.add(error);
							}
						}
					}
				}
			}
		}
		return errors;
	}

	// US14
	public static List<Error> fiveSiblingSpacing(Parser p) {
		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record c : p.getFamilyList()) {
			if (c.getProperty(PropertyType.children) != null) {
				@SuppressWarnings("unchecked")
				Object[] ChildrenIdList = ((List<String>) c.getProperty(PropertyType.children).getValue()).toArray();
				String[] ChildrenIds = Arrays.copyOf(ChildrenIdList, ChildrenIdList.length, String[].class);

				Set<String> sameBirthSibling = new HashSet<String>();
				if (ChildrenIds.length > 5) {
					for (int i = 0; i < ChildrenIds.length - 1; i++) {

						String id_1 = ChildrenIds[i];
						Predicate<Record> byiId = x -> x.getProperty(PropertyType.id).getValue().equals(id_1);
						List<Record> resi = p.getIndividualList().stream().filter(byiId)
								.collect(Collectors.<Record>toList());
						LocalDate bdate_i = (LocalDate) resi.get(0).getProperty(PropertyType.birthday).getValue();

						for (int j = i + 1; j < ChildrenIds.length; j++) {

							String id_2 = ChildrenIds[j];
							Predicate<Record> byjId = x -> x.getProperty(PropertyType.id).getValue().equals(id_2);
							List<Record> resj = p.getIndividualList().stream().filter(byjId)
									.collect(Collectors.<Record>toList());
							LocalDate bdate_j = (LocalDate) resj.get(0).getProperty(PropertyType.birthday).getValue();

							long diffDays = ChronoUnit.DAYS.between(bdate_i, bdate_j);

							if (diffDays < 2 && diffDays >= 0) {
								sameBirthSibling.add(id_1);
								sameBirthSibling.add(id_2);
							}
						}
					}

					// Sorting HashSet using List
					List<String> sameBirthSiblingList = new ArrayList<String>(sameBirthSibling);
					Collections.sort(sameBirthSiblingList);

					if (sameBirthSibling.size() > 5) {
						error = new Error();
						error.setErrorType(ErrorType.ERROR);
						error.setRecordType(RecordType.FAMILY);
						error.setUserStoryNumber("US14");
						error.setLineNumber(c.getProperty(PropertyType.id).getLineNumber());
						error.setId((String) c.getProperty(PropertyType.id).getValue());
						error.setMessage("Family has more than 5 siblings born within 2 days (Children IDs : "
								+ String.join(",", sameBirthSiblingList) + ")");
						errors.add(error);
					}
				}
			}
		}

		return errors;
	}
	
	// US15
	public static List<Error> fewerThanFifteenSiblings(Parser p) {
		
		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();
		
		for (Record f: p.getFamilyList()) {
			
			if (f.getProperty(PropertyType.children) != null) {
				@SuppressWarnings("unchecked")
				Object[] ChildrenList = ((List<String>) f.getProperty(PropertyType.children).getValue()).toArray();
				String[] ChildrenId = Arrays.copyOf(ChildrenList, ChildrenList.length, String[].class);
				
				int totalChildren = ChildrenId.length;
				
				if (totalChildren > 15) {
					error = new Error();
					error.setErrorType(ErrorType.ERROR);
					error.setRecordType(RecordType.FAMILY);
					error.setUserStoryNumber("US15");
					error.setLineNumber(f.getProperty(PropertyType.id).getLineNumber());
					error.setId((String) f.getProperty(PropertyType.id).getValue());
					error.setMessage("Family (" + f.getProperty(PropertyType.id).getValue() + ") has more than 15 siblings (Children IDs: " 
							+ f.getProperty(PropertyType.children).getValue()  + ").");
					errors.add(error);
				}
			}
		}	
		return errors;	
	}
	
	

}