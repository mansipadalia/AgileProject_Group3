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

}
