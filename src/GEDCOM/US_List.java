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
}
