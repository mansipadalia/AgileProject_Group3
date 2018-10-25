package GEDCOM;

import java.time.LocalDate;
import java.time.Period;
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

public class US_BirthDeathDates {

	// US03
	public static List<Error> birthBeforeDeath(Parser p) {

		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getIndividualList()) {
			LocalDate deathDate = i.getProperty(PropertyType.death) != null
					? (LocalDate) i.getProperty(PropertyType.death).getValue()
					: null;

			if (deathDate != null && ((LocalDate) i.getProperty(PropertyType.birthday).getValue()).isAfter(deathDate)) {

				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.INDIVIDUAL);
				error.setUserStoryNumber("US03");
				error.setLineNumber(i.getProperty(PropertyType.death).getLineNumber());
				error.setId((String) i.getProperty(PropertyType.id).getValue());
				error.setMessage("Death Date " + deathDate + " occurs before Birth Date "
						+ (LocalDate) i.getProperty(PropertyType.birthday).getValue());
				errors.add(error);
			}
		}
		return errors;
	}
	
	// US 12
	public static List<Error> parentsNotTooOld(Parser p) {
			List<Error> errors = new ArrayList<Error>();
			Error error = new Error();

			for (Record c : p.getFamilyList()) {
				String husbandID = c.getProperty(PropertyType.husbandID) != null
						? (String) c.getProperty(PropertyType.husbandID).getValue()
						: null;
				String wifeID = c.getProperty(PropertyType.wifeID) != null
						? (String) c.getProperty(PropertyType.wifeID).getValue()
						: null;
										
				if (c.getProperty(PropertyType.children) != null) {
					Object[] ChildrenList = ((List<String>) c.getProperty(PropertyType.children).getValue()).toArray();
					String[] ChildrenIds = Arrays.copyOf(ChildrenList, ChildrenList.length, String[].class);

					if (ChildrenIds.length >= 1) {
						for (int i = 0; i < ChildrenIds.length; i++) {

							String id_1 = ChildrenIds[i];
							
							Predicate<Record> bycId = x -> x.getProperty(PropertyType.id).getValue().equals(id_1);
							List<Record> resC = p.getIndividualList().stream().filter(bycId)
									.collect(Collectors.<Record>toList());
							LocalDate bdate_C = resC.get(0).getProperty(PropertyType.birthday) != null
									? (LocalDate) resC.get(0).getProperty(PropertyType.birthday).getValue()
									: null;

						
							Predicate<Record> byHId = x -> x.getProperty(PropertyType.id).getValue().equals(husbandID);
							List<Record> resH = p.getIndividualList().stream().filter(byHId).collect(Collectors.<Record>toList());
								
							LocalDate birthDateH = resH.get(0).getProperty(PropertyType.birthday) != null
									? (LocalDate) resH.get(0).getProperty(PropertyType.birthday).getValue()
									: null;
											
							if (birthDateH != null && bdate_C != null 
									&& (Period.between(birthDateH, bdate_C).getYears()) > 80) {
										
										error = new Error();
										error.setErrorType(ErrorType.ERROR);
										error.setRecordType(RecordType.FAMILY);
										error.setUserStoryNumber("US12");
										error.setLineNumber(resC.get(0).getProperty(PropertyType.birthday).getLineNumber());
										error.setId((String) c.getProperty(PropertyType.id).getValue());
										error.setMessage("Parent (" + resH.get(0).getProperty(PropertyType.id).getValue() + ") (Birth Date: " + birthDateH + 
												") is older than his child (" + resC.get(0).getProperty(PropertyType.id).getValue() + 
												") (Birth Date: " + bdate_C + ").");
										errors.add(error);
									}
									
							Predicate<Record> byWId = x -> x.getProperty(PropertyType.id).getValue().equals(wifeID);
							List<Record> resW = p.getIndividualList().stream().filter(byWId).collect(Collectors.<Record>toList());
								
							LocalDate birthDateW = resW.get(0).getProperty(PropertyType.birthday) != null
									? (LocalDate) resW.get(0).getProperty(PropertyType.birthday).getValue()
									: null;
		
							if (birthDateW != null && bdate_C != null 
								&& (Period.between(birthDateW, bdate_C).getYears()) > 60) {
											
									error = new Error();
									error.setErrorType(ErrorType.ERROR);
									error.setRecordType(RecordType.FAMILY);
									error.setUserStoryNumber("US12");
									error.setLineNumber(resC.get(0).getProperty(PropertyType.birthday).getLineNumber());
									error.setId((String) c.getProperty(PropertyType.id).getValue());
									error.setMessage("Parent (" + resW.get(0).getProperty(PropertyType.id).getValue() + ") (Birth Date: " + birthDateW + 
										") is older than her child (" + resC.get(0).getProperty(PropertyType.id).getValue() + 
										") (Birth Date: " + bdate_C + ").");
									errors.add(error);
									}
								}
							}
						}
					}
			return errors;
		}
}