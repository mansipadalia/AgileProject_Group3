package GEDCOM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_Uniqueness {
	
	// US23
	
	public static List<Error> uniqueNameBirthDate(Parser p){

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getIndividualList()) {
			String name = i.getProperty(PropertyType.name) != null
					? (String) i.getProperty(PropertyType.name).getValue()
					: null;
			LocalDate birthDate = i.getProperty(PropertyType.birthday) != null
					? (LocalDate) i.getProperty(PropertyType.birthday).getValue()
					: null;
			String id = i.getProperty(PropertyType.id) != null
							? (String) i.getProperty(PropertyType.id).getValue()
							: null;

			if (name != null && birthDate != null ) {
			
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
									
							if (namej != null && birthDatej != null ) {
								if(((id).equals(idj)) == false && (j.getProperty(PropertyType.id).getLineNumber() > i.getProperty(PropertyType.id).getLineNumber()) ) {
								
									if((name).equals(namej) && (birthDate).equals(birthDatej)) {
										error = new Error();
										error.setErrorType(ErrorType.ERROR);
										error.setRecordType(RecordType.INDIVIDUAL);
										error.setUserStoryNumber("US23");
										error.setLineNumber(i.getProperty(PropertyType.id).getLineNumber());
										error.setId(id);
										error.setMessage("Name " + name + " and " +" Birthday " + birthDate + " is same as " + j.getProperty(PropertyType.id).getLineNumber() + ": " + idj + ": " + namej + " and "+"Birthday " + birthDatej);
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
	public static List<Error> uniqueFirstName(Parser p){
		
		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();
		
		String[] IDs = {};
		
		for (Record i : p.getFamilyList()) {
			
			int flag = 0;
			ArrayList<String>  displayName = new ArrayList<String> ();

			String husbandId = i.getProperty(PropertyType.husbandID) != null
							? (String) i.getProperty(PropertyType.husbandID).getValue()
							: null;

			String wifeId = i.getProperty(PropertyType.wifeID) != null
							? (String) i.getProperty(PropertyType.wifeID).getValue()
							: null;
							
			String husbandFN = (String) i.getProperty(PropertyType.husbandName).getValue();
			String[] husbandSplited = husbandFN.split("\\s+", 2);

			String wifeFN = (String) i.getProperty(PropertyType.wifeName).getValue();
			String[] wifeSplited = wifeFN.split("\\s+", 2);
			
			ArrayList<String>  firstNames = new ArrayList<String> ();
			firstNames.add(husbandSplited[0]);
			firstNames.add(wifeSplited[0]);
				
			if (i.getProperty(PropertyType.children) != null) {
			Object[] childrenIdList = ((List<String>) i.getProperty(PropertyType.children).getValue()).toArray();
			String[] childrenIds = Arrays.copyOf(childrenIdList, childrenIdList.length, String[].class);

				 
			for (int a = 0; a < childrenIds.length - 1; a++) {

			String id_1 = childrenIds[a];
			Predicate<Record> byiId = x -> x.getProperty(PropertyType.id).getValue().equals(id_1);
			List<Record> resi = p.getIndividualList().stream().filter(byiId)
			.collect(Collectors.<Record>toList());
			
			String childFN = (String) resi.get(0).getProperty(PropertyType.name).getValue();
			String[] childSplited = childFN.split("\\s+", 2);
			firstNames.add(childSplited[0]);
			}

			for (int b = 0; b < firstNames.size(); b++) {
				for (int c = b+1; c < firstNames.size(); c++) {
					if (firstNames.get(b).equals(firstNames.get(c)))
					{
						flag = 1;
					        for(int j=0; j<firstNames.size(); j++){
					            if(displayName.indexOf(firstNames.get(b)) == -1){
					            	displayName.add(firstNames.get(b));
					            }
					        }
					}
				}
			}
			
			if ( flag == 1) {
				
				for (int d = 0; d < displayName.size(); d++) {
				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.FAMILY);
				error.setUserStoryNumber("US25");
				error.setLineNumber(i.getProperty(PropertyType.id).getLineNumber());
				error.setId((String) i.getProperty(PropertyType.id).getValue());
				//error.setId(id);
				error.setMessage("More than one individual in a family have the same name " + displayName.get(d)); 	
				errors.add(error);
				}
			}
			}

	
			else {
		
				
				if (firstNames.get(0).equals(firstNames.get(1))) {
					
					error = new Error();
					error.setErrorType(ErrorType.ERROR);
					error.setRecordType(RecordType.FAMILY);
					error.setUserStoryNumber("US25");
					error.setLineNumber(i.getProperty(PropertyType.husbandID).getLineNumber());
					error.setId((String) i.getProperty(PropertyType.id).getValue());
					error.setMessage("Husband's First Name " + husbandSplited[0]  + " is same as Wife's First Name " + wifeSplited[0]); 	
					errors.add(error);
				}
			
			}
			}
		
	return errors;

	}
	
}
