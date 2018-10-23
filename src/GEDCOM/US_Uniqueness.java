package GEDCOM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_Uniqueness {
	
	//US23
	
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
										error.setMessage("Name " + name + "and " +" Birthday " + birthDate + " is same as " + j.getProperty(PropertyType.id).getLineNumber() + ": " + idj + ": " + namej + " and "+"Birthday " + birthDatej);
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
