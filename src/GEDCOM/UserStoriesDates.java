package GEDCOM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserStoriesDates {

	// US02
	public static List<String> birthBeforeMarriage(Parser p) {

		List<String> errors = new ArrayList<String>();

		for (Family i : p.getFamilyList()) {
			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;
			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;

			Predicate<Individual> byHId = x -> x.getProperty(PropertyType.id).getValue().equals(husbandID);
			List<Individual> res1 = p.getIndividualList().stream().filter(byHId)
					.collect(Collectors.<Individual>toList());

			if (((LocalDate) res1.get(0).getProperty(PropertyType.birthday).getValue())
					.isAfter((LocalDate) i.getProperty(PropertyType.married).getValue())) {
				errors.add("ERROR: INDIVIDUAL: US02: " + i.getProperty(PropertyType.married).getLineNumber() + ": "
						+ husbandID + ": Marriage Date occurs "
						+ (LocalDate) i.getProperty(PropertyType.married).getValue() + " before Birth Date "
						+ (LocalDate) res1.get(0).getProperty(PropertyType.birthday).getValue() + ".");
			}

			Predicate<Individual> byWId = x -> x.getProperty(PropertyType.id).getValue().equals(wifeID);
			List<Individual> res2 = p.getIndividualList().stream().filter(byWId)
					.collect(Collectors.<Individual>toList());

			if (((LocalDate) res2.get(0).getProperty(PropertyType.birthday).getValue())
					.isAfter((LocalDate) i.getProperty(PropertyType.married).getValue())) {
				errors.add("ERROR: INDIVIDUAL: US02: " + i.getProperty(PropertyType.married).getLineNumber() + ": "
						+ wifeID + ": Marriage Date occurs "
						+ (LocalDate) i.getProperty(PropertyType.married).getValue() + " before Birth Date "
						+ (LocalDate) res2.get(0).getProperty(PropertyType.birthday).getValue() + ".");
			}

		}
		return errors;
	}
  
  // US03
	public static List<String> birthBeforeDeath(Parser p) {

		List<String> errors = new ArrayList<String>();

		for (Individual i : p.getIndividualList()) {
			LocalDate deathDate = i.getProperty(PropertyType.death) != null
					? (LocalDate) i.getProperty(PropertyType.death).getValue()
					: null;
			if (deathDate != null && ((LocalDate) i.getProperty(PropertyType.birthday).getValue()).isAfter(deathDate)) {
				errors.add("ERROR: INDIVIDUAL: US03: " + i.getProperty(PropertyType.death).getLineNumber() + ": "
						+ i.getProperty(PropertyType.id).getValue() + ": Death Date occurs " + deathDate
						+ " before Birth Date " + (LocalDate) i.getProperty(PropertyType.birthday).getValue() + ".");
			}
		}
		return errors;
	}

	// US04
	public static List<String> marriageBeforeDivorce(Parser p) {

		List<String> errors = new ArrayList<String>();

			for (Family i : p.getFamilyList()) {
				String familyID = i.getProperty(PropertyType.id) != null
						? (String) i.getProperty(PropertyType.id).getValue()
						: null;
						
				LocalDate divorceDate = i.getProperty(PropertyType.divorced) != null
						? (LocalDate) i.getProperty(PropertyType.divorced).getValue()
						: null;

				if (divorceDate != null && ((LocalDate) i.getProperty(PropertyType.married).getValue())
						.isAfter((LocalDate) i.getProperty(PropertyType.divorced).getValue())) {
					errors.add("ERROR: FAMILY: US04: " + i.getProperty(PropertyType.divorced).getLineNumber() + ": "
							+ familyID + ": Divorce " + (LocalDate) i.getProperty(PropertyType.divorced).getValue()
							+ " occurs before marriage on " + (LocalDate) i.getProperty(PropertyType.married).getValue()
							+ ".");
				}
				
				if (divorceDate != null && ((LocalDate) i.getProperty(PropertyType.married).getValue())
						.equals((LocalDate) i.getProperty(PropertyType.divorced).getValue())) {
					errors.add("ERROR: FAMILY: US04: " + i.getProperty(PropertyType.divorced).getLineNumber() + ": "
							+ familyID + ": Divorce " + (LocalDate) i.getProperty(PropertyType.divorced).getValue()
							+ " occurs before marriage on " + (LocalDate) i.getProperty(PropertyType.married).getValue()
							+ ".");
				}

			}
			return errors;
		}
		
	//US 05
	public static List<String> marriageBeforeDeath(Parser p) {

		List<String> errors = new ArrayList<String>();

			for (Family i : p.getFamilyList()) {
				String husbandID = i.getProperty(PropertyType.husbandID) != null
						? (String) i.getProperty(PropertyType.husbandID).getValue()
						: null;
				String wifeID = i.getProperty(PropertyType.wifeID) != null
						? (String) i.getProperty(PropertyType.wifeID).getValue()
						: null;
						
				String familyID = i.getProperty(PropertyType.id) != null
						? (String) i.getProperty(PropertyType.id).getValue()
						: null;

				Predicate<Individual> byHId = x -> x.getProperty(PropertyType.id).getValue().equals(husbandID);
				List<Individual> res1 = p.getIndividualList().stream().filter(byHId)
						.collect(Collectors.<Individual>toList());
				
				LocalDate HdeathDate = res1.get(0).getProperty(PropertyType.death) != null
						? (LocalDate) res1.get(0).getProperty(PropertyType.death).getValue()
						: null;
				
				if (HdeathDate != null && ((LocalDate) i.getProperty(PropertyType.married).getValue())
						.isAfter((LocalDate) res1.get(0).getProperty(PropertyType.death).getValue())) {
					errors.add("ERROR: FAMILY: US05: " + i.getProperty(PropertyType.married).getLineNumber() + ": "
							+ familyID + ": Marriage occurs " + (LocalDate) i.getProperty(PropertyType.married).getValue()
							+ " after husband's (" + husbandID + ") death on " + HdeathDate + ".");
				}

				Predicate<Individual> byWId = x -> x.getProperty(PropertyType.id).getValue().equals(wifeID);
				List<Individual> res2 = p.getIndividualList().stream().filter(byWId)
						.collect(Collectors.<Individual>toList());

				LocalDate WdeathDate = res2.get(0).getProperty(PropertyType.death) != null
						? (LocalDate) res2.get(0).getProperty(PropertyType.death).getValue()
						: null;
						
				if (WdeathDate != null && ((LocalDate) i.getProperty(PropertyType.married).getValue())
						.isAfter((LocalDate) res2.get(0).getProperty(PropertyType.death).getValue())) {
					errors.add("ERROR: FAMILY: US05: " + i.getProperty(PropertyType.married).getLineNumber() + ": "
							+ familyID + ": Marriage occurs " + (LocalDate) i.getProperty(PropertyType.married).getValue()
							+ " after wife's (" + wifeID + ") death on " + WdeathDate + ".");
				}

			}
			return errors;
		}

	// US01
			public static List<String> datesBeforeCurrentDate(Parser p) {

				List<String> errors = new ArrayList<String>();
				LocalDate currentDate = LocalDate.now();

				for (Individual i : p.getIndividualList()) {
					LocalDate birthDate = i.getProperty(PropertyType.birthday) != null
							? (LocalDate) i.getProperty(PropertyType.birthday).getValue()
							: null;

					LocalDate deathDate = i.getProperty(PropertyType.death) != null
							? (LocalDate) i.getProperty(PropertyType.death).getValue()
							: null;

					if (birthDate != null
							&& ((LocalDate) i.getProperty(PropertyType.birthday).getValue()).isAfter(currentDate)) {
						errors.add("ERROR: INDIVIDUAL: US01: " + i.getProperty(PropertyType.birthday).getLineNumber() + ": "
								+ i.getProperty(PropertyType.id).getValue() + " :" + birthDate + ": Birthday occurs in the future" + ".");

					}
					if (deathDate != null && ((LocalDate) i.getProperty(PropertyType.death).getValue()).isAfter(currentDate)) {
						errors.add("ERROR: INDIVIDUAL: US01: " + i.getProperty(PropertyType.death).getLineNumber() + ": "
								+ i.getProperty(PropertyType.id).getValue()  + " :"  + deathDate + ": Death occurs in the future" + ".");
					}
				}

				for (Family i : p.getFamilyList()) {

					LocalDate marriedDate = i.getProperty(PropertyType.married) != null
							? (LocalDate) i.getProperty(PropertyType.married).getValue()
							: null;

					LocalDate divorcedDate = i.getProperty(PropertyType.divorced) != null
							? (LocalDate) i.getProperty(PropertyType.divorced).getValue()
							: null;
					if (marriedDate != null
							&& ((LocalDate) i.getProperty(PropertyType.married).getValue()).isAfter(currentDate)) {
						errors.add("ERROR: INDIVIDUAL: US01: " + i.getProperty(PropertyType.married).getLineNumber() + ": "
								+ i.getProperty(PropertyType.id).getValue() + " : " + marriedDate
								+ ": Marriage occurs in the future" + ".");
					}
					if (divorcedDate != null
							&& ((LocalDate) i.getProperty(PropertyType.divorced).getValue()).isAfter(currentDate)) {
						errors.add("ERROR: INDIVIDUAL: US01: " + i.getProperty(PropertyType.divorced).getLineNumber() + ": "
								+ i.getProperty(PropertyType.id).getValue() + ": " + divorcedDate + ": Divorced occurs in the future" + ".");

					}
				}

				return errors;
			}
			

			// US07
			public static List<String> lessThanOneFiftyAge(Parser p) {

				List<String> errors = new ArrayList<String>();
				
				for (Individual i : p.getIndividualList()) {
					LocalDate birthDate = i.getProperty(PropertyType.birthday) != null
							? (LocalDate) i.getProperty(PropertyType.birthday).getValue()
							: null;
					if ((int) i.getProperty(PropertyType.age).getValue() > 150 ){
						errors.add("ERROR: INDIVIDUAL: US07: " + i.getProperty(PropertyType.birthday).getLineNumber() + ": "
								+ i.getProperty(PropertyType.id).getValue() + ": More than 150 years old Birth Date" + ":" + birthDate + ".");
				}
			}
				return errors;
			}
}
