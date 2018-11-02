package GEDCOM;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class US_List {

	// US38
	public static List<Record> upcomingBirthdays(Parser p) {
		List<Record> individuals = new ArrayList<Record>();
		for (Record i : p.getIndividualList()) {
			LocalDate birthDate = i.getProperty(PropertyType.birthday) != null
					? (LocalDate) i.getProperty(PropertyType.birthday).getValue()
					: null;

			if (birthDate != null && birthDate.isBefore(LocalDate.now())) {
				LocalDate birthDay = LocalDate.of(LocalDate.now().getYear(), birthDate.getMonthValue(),
						birthDate.getDayOfMonth());
				long diffDays = ChronoUnit.DAYS.between(LocalDate.now(), birthDay);
				if (diffDays < 30 && diffDays > 0) {
					individuals.add(i);
				}
			}
		}
		return individuals;
	}

	// US39
	public static List<Record> upcomingAnniversaries(Parser p) {
		List<Record> families = new ArrayList<Record>();
		for (Record i : p.getFamilyList()) {
			LocalDate marriageDate = i.getProperty(PropertyType.married) != null
					? (LocalDate) i.getProperty(PropertyType.married).getValue()
					: null;

			if (marriageDate != null && marriageDate.isBefore(LocalDate.now())) {
				LocalDate marriageDay = LocalDate.of(LocalDate.now().getYear(), marriageDate.getMonthValue(),
						marriageDate.getDayOfMonth());
				long diffDays = ChronoUnit.DAYS.between(LocalDate.now(), marriageDay);
				if (diffDays < 30 && diffDays > 0) {
					families.add(i);
				}
			}
		}
		return families;
	}

	// US29
	public static List<Record> deceased(Parser p) {
		List<Record> individuals = new ArrayList<Record>();
		for (Record i : p.getIndividualList()) {
			LocalDate deathDate = i.getProperty(PropertyType.death) != null
					? (LocalDate) i.getProperty(PropertyType.death).getValue()
					: null;

			if (deathDate != null && deathDate.isBefore(LocalDate.now())) {
				individuals.add(i);
			}
		}
		return individuals;
	}

}