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

public class US_ParentChild {
	
	//user story 08
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
	
			//System.out.println(ChildrenIds);
			
			if (ChildrenIds.length > 1) {
				
			
				for (int j = 0; j < ChildrenIds.length - 1; j++) {

					String id_1 = ChildrenIds[j];
					Predicate<Record> byiId = x -> x.getProperty(PropertyType.id).getValue().equals(id_1);
					List<Record> resi = p.getIndividualList().stream().filter(byiId)
							.collect(Collectors.<Record>toList());
					LocalDate bdate_j = (LocalDate) resi.get(0).getProperty(PropertyType.birthday).getValue();
					
				
						if (marriageDate != null && bdate_j != null
								&& (marriageDate.isAfter(bdate_j))) {

							error = new Error();
							error.setErrorType(ErrorType.ERROR);
							error.setRecordType(RecordType.FAMILY);
							error.setUserStoryNumber("US08");
							error.setLineNumber(i.getProperty(PropertyType.married).getLineNumber());
							error.setId(familyID);
							error.setMessage("Difference between birthdate (" + bdate_j + ") and marriage Date ("+ marriageDate
									+ ") is not valid");
							errors.add(error);
						}
						
						if (divorceDate != null && bdate_j != null
								&& (divorceDate.isAfter(bdate_j))) {
						long diffMonth = ChronoUnit.DAYS.between(divorceDate, bdate_j);

						if (diffMonth > (8 * 30)) {
							error = new Error();
							error.setErrorType(ErrorType.ERROR);
							error.setRecordType(RecordType.FAMILY);
							error.setUserStoryNumber("US08");
							error.setLineNumber(i.getProperty(PropertyType.divorced).getLineNumber());
							error.setId(id_1);
							error.setMessage("Difference between birthdate (" + bdate_j + ") and divorce Date ("+ divorceDate 
										+ ") is not valid");
							errors.add(error);
						}
						}
						
					}
				}			
					
			}
		}
			return errors;
	}
				

	// user_story 09
	
	public static List<Error> birthBeforeDeathOfParents(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getFamilyList()) {
			
			String familyID = (String) i.getProperty(PropertyType.id).getValue();
			
			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;
			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;
			
			LocalDate deathDate = i.getProperty(PropertyType.death) != null
					? (LocalDate) i.getProperty(PropertyType.death).getValue()
					: null;

					
			if (i.getProperty(PropertyType.children) != null) {
			Object[] ChildrenIdList = ((List<String>) i.getProperty(PropertyType.children).getValue()).toArray();
			String[] ChildrenIds = Arrays.copyOf(ChildrenIdList, ChildrenIdList.length, String[].class);
			
			if (ChildrenIds.length > 1) {
				
				
				for (int j = 0; j < ChildrenIds.length - 1; j++) {

					String id_1 = ChildrenIds[j];
					Predicate<Record> byiId = x -> x.getProperty(PropertyType.id).getValue().equals(id_1);
					List<Record> resi = p.getIndividualList().stream().filter(byiId)
							.collect(Collectors.<Record>toList());
					LocalDate bdate_j = (LocalDate) resi.get(0).getProperty(PropertyType.birthday).getValue();
					
					if (deathDate != null && bdate_j != null && (deathDate.isBefore(bdate_j))) {

						error = new Error();
						error.setErrorType(ErrorType.ERROR);
						error.setRecordType(RecordType.FAMILY);
						error.setUserStoryNumber("US09");
						error.setLineNumber(i.getProperty(PropertyType.death).getLineNumber());
						error.setId(familyID);
						error.setMessage("Difference between birthdate (" + bdate_j + ") and deathDate of mother ("+ deathDate
								+ ") is not valid");
						errors.add(error);
					}
					
					if (deathDate != null && bdate_j != null
							&& (deathDate.isBefore(bdate_j))) {
					
						long diffMonth = ChronoUnit.DAYS.between(deathDate, bdate_j);

					if (diffMonth > (8 * 30)) {
						error = new Error();
						error.setErrorType(ErrorType.ERROR);
						error.setRecordType(RecordType.FAMILY);
						error.setUserStoryNumber("US09");
						error.setLineNumber(i.getProperty(PropertyType.death).getLineNumber());
						error.setId(id_1);
						error.setMessage("Difference between birthdate (" + bdate_j + ") and deathDate of father ("+ deathDate 
									+ ") is not valid");
						errors.add(error);
					}
				}
			
			}
		}
	}
}
		return errors;
}
		
}