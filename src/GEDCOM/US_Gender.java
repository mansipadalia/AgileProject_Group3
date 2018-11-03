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
												
						if(!(childLastName[1].equals(husbLastName[1]))) {
							
							error = new Error();
							error.setErrorType(ErrorType.ERROR);
							error.setRecordType(RecordType.FAMILY);
							error.setUserStoryNumber("US16");
							error.setLineNumber(resC.get(0).getProperty(PropertyType.id).getLineNumber());
							error.setId((String) f.getProperty(PropertyType.id).getValue());
							error.setMessage("Child (" + resC.get(0).getProperty(PropertyType.id).getValue() + ") has last name (" 
									+ childLastName[1] + ") different from his father's last name (" + husbLastName[1] + ").");
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
