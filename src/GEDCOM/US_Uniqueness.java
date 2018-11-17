package GEDCOM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class US_Uniqueness {

	// US23
	public static List<Error> uniqueNameBirthDate(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getIndividualList()) {
			String name = i.getProperty(PropertyType.name) != null
					? (String) i.getProperty(PropertyType.name).getValue()
					: null;
			LocalDate birthDate = i.getProperty(PropertyType.birthday) != null
					? (LocalDate) i.getProperty(PropertyType.birthday).getValue()
					: null;
			String id = i.getProperty(PropertyType.id) != null ? (String) i.getProperty(PropertyType.id).getValue()
					: null;

			if (name != null && birthDate != null) {

				for (Record j : p.getIndividualList()) {
					String namej = j.getProperty(PropertyType.name) != null
							? (String) j.getProperty(PropertyType.name).getValue()
							: null;
					LocalDate birthDatej = j.getProperty(PropertyType.birthday) != null
							? (LocalDate) j.getProperty(PropertyType.birthday).getValue()
							: null;
					String idj = j.getProperty(PropertyType.id) != null
							? (String) j.getProperty(PropertyType.id).getValue()
							: null;

					if (namej != null && birthDatej != null) {
						if (((id).equals(idj)) == false && (j.getProperty(PropertyType.id).getLineNumber() > i
								.getProperty(PropertyType.id).getLineNumber())) {

							if ((name).equals(namej) && (birthDate).equals(birthDatej)) {
								error = new Error();
								error.setErrorType(ErrorType.ERROR);
								error.setRecordType(RecordType.INDIVIDUAL);
								error.setUserStoryNumber("US23");
								error.setLineNumber(i.getProperty(PropertyType.id).getLineNumber());
								error.setId(id);
								error.setMessage("Name " + name + " and " + " Birthday " + birthDate + " is same as "
										+ j.getProperty(PropertyType.id).getLineNumber() + ": " + idj + ": " + namej
										+ " and " + "Birthday " + birthDatej);
								errors.add(error);
							}
						}
					}
				}
			}

		}
		return errors;
	}

	// US25
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Error> uniqueFirstNameBirthDate(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getFamilyList()) {

			int flag = 0;

			ArrayList<String> firstNames = new ArrayList<String>();
			ArrayList<LocalDate> birthDays = new ArrayList<LocalDate>();
			HashMap<String, LocalDate> display = new HashMap<>();

			if (i.getProperty(PropertyType.children) != null) {
				Object[] childrenIdList = ((List<String>) i.getProperty(PropertyType.children).getValue()).toArray();
				String[] childrenIds = Arrays.copyOf(childrenIdList, childrenIdList.length, String[].class);

				for (int a = 0; a < childrenIds.length; a++) {

					String id_1 = childrenIds[a];
					Predicate<Record> byiId = x -> x.getProperty(PropertyType.id).getValue().equals(id_1);
					List<Record> resi = p.getIndividualList().stream().filter(byiId)
							.collect(Collectors.<Record>toList());

					String childFN = (String) resi.get(0).getProperty(PropertyType.name).getValue();
					String[] childSplited = childFN.split("\\s+", 2);
					firstNames.add(childSplited[0]);

					LocalDate childBD = (LocalDate) resi.get(0).getProperty(PropertyType.birthday).getValue();
					birthDays.add(childBD);
				}

				for (int b = 0; b < firstNames.size(); b++) {
					for (int c = b + 1; c < firstNames.size(); c++) {
						if (firstNames.get(b).equals(firstNames.get(c)) && birthDays.get(b).equals(birthDays.get(c))) {
							flag = 1;
							for (int j = 0; j < firstNames.size(); j++) {
								if (!(display.containsKey(firstNames.get(b)))) {
									display.put(firstNames.get(b), birthDays.get(b));
								}
							}
						}
					}
				}

				if (flag == 1) {

					Set set = display.entrySet();
					Iterator iterator = set.iterator();

					while (iterator.hasNext()) {

						Map.Entry mentry = (Map.Entry) iterator.next();
						error = new Error();
						error.setErrorType(ErrorType.ERROR);
						error.setRecordType(RecordType.FAMILY);
						error.setUserStoryNumber("US25");
						error.setLineNumber(i.getProperty(PropertyType.id).getLineNumber());
						error.setId((String) i.getProperty(PropertyType.id).getValue());
						error.setMessage("More than one child in a family have the same first name " + mentry.getKey()
								+ " and birthday " + mentry.getValue());
						errors.add(error);

					}
				}
			}

		}

		return errors;

	}
	
	// US 22
	
	public static List<Error> uniqueIds(Parser p) {
		
		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();
		ArrayList<String> familyIds = new ArrayList<String>();

		for (Record i : p.getFamilyList()) {
			
			String id = i.getProperty(PropertyType.id) != null ? (String) i.getProperty(PropertyType.id).getValue()
					: null;
			for (int a = 0; a < familyIds.size(); a++) {
			familyIds.add(id);
			}
		
			for (int b = 0; b < familyIds.size(); b++) {
				for (int c = b + 1; c < familyIds.size(); c++) {
					if (familyIds.get(b).equals(familyIds.get(c))) {
						error = new Error();
						error.setErrorType(ErrorType.ERROR);
						error.setRecordType(RecordType.FAMILY);
						error.setUserStoryNumber("US22");
						error.setLineNumber(i.getProperty(PropertyType.id).getLineNumber());
						error.setId(id);
						error.setMessage("More than one families have same IDs " + id );
						errors.add(error);
					}	
				}
			}
		}
			return errors;
				}
	
		
	

}
