package GEDCOM;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

	// US30
	public static List<Record> livingMarried(Parser p) {
		List<Record> records = new ArrayList<Record>();
		for (Record i : p.getFamilyList()) {
			LocalDate marriageDate = i.getProperty(PropertyType.married) != null
					? (LocalDate) i.getProperty(PropertyType.married).getValue()
					: null;
			LocalDate divorcedDate = i.getProperty(PropertyType.divorced) != null
					? (LocalDate) i.getProperty(PropertyType.divorced).getValue()
					: null;

			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;
			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;

			String[] IDs = new String[] { husbandID, wifeID };

			for (String id : IDs) {

				Predicate<Record> byId = x -> x.getProperty(PropertyType.id).getValue().equals(id);
				List<Record> res = p.getIndividualList().stream().filter(byId).collect(Collectors.<Record>toList());
				LocalDate birthDate = id == null ? null
						: res.get(0).getProperty(PropertyType.birthday) != null
								? (LocalDate) res.get(0).getProperty(PropertyType.birthday).getValue()
								: null;
				LocalDate deathDate = id == null ? null
						: res.get(0).getProperty(PropertyType.death) != null
								? (LocalDate) res.get(0).getProperty(PropertyType.death).getValue()
								: null;

				if (divorcedDate == null && deathDate == null && birthDate != null && marriageDate != null) {
					Record record = new Record();
					record.setProperty(PropertyType.id, new Property(id, 0));
					record.setProperty(PropertyType.name,
							new Property(res.get(0).getProperty(PropertyType.name).getValue(), 0));
					record.setProperty(PropertyType.birthday, new Property(birthDate, 0));
					record.setProperty(PropertyType.death, new Property(deathDate, 0));
					record.setProperty(PropertyType.married, new Property(marriageDate, 0));
					record.setProperty(PropertyType.divorced, new Property(divorcedDate, 0));
					records.add(record);
				}
			}
		}
		Collections.sort(records, (i1, i2) -> (((String) i1.getProperty(PropertyType.id).getValue())
				.compareTo((String) i2.getProperty(PropertyType.id).getValue())));
		return records;
	}

	// US 28
	@SuppressWarnings("unchecked")
	public static List<Record> orderSiblingsByAge(Parser p) {
		List<Record> records = new ArrayList<Record>();

		for (Record i : p.getFamilyList()) {

			if (i.getProperty(PropertyType.children) != null) {
				Object[] childrenIdList = ((List<String>) i.getProperty(PropertyType.children).getValue()).toArray();
				String[] childrenIds = Arrays.copyOf(childrenIdList, childrenIdList.length, String[].class);

				String familyId = i.getProperty(PropertyType.id) != null
						? (String) i.getProperty(PropertyType.id).getValue()
						: null;

				for (String id : childrenIds) {

					Predicate<Record> byId = x -> x.getProperty(PropertyType.id).getValue().equals(id);
					List<Record> res = p.getIndividualList().stream().filter(byId).collect(Collectors.<Record>toList());
					String name = res.get(0).getProperty(PropertyType.name) != null
							? (String) res.get(0).getProperty(PropertyType.name).getValue()
							: null;
					String gender = res.get(0).getProperty(PropertyType.gender) != null
							? (String) res.get(0).getProperty(PropertyType.gender).getValue()
							: null;
					LocalDate birthdate = res.get(0).getProperty(PropertyType.birthday) != null
							? (LocalDate) res.get(0).getProperty(PropertyType.birthday).getValue()
							: null;
					int age = res.get(0).getProperty(PropertyType.age) != null
							? (int) res.get(0).getProperty(PropertyType.age).getValue()
							: null;

					Record record = new Record();
					record.setProperty(PropertyType.child, new Property(familyId, 0));
					record.setProperty(PropertyType.id, new Property(id, 0));
					record.setProperty(PropertyType.name, new Property(name, 0));
					record.setProperty(PropertyType.gender, new Property(gender, 0));
					record.setProperty(PropertyType.birthday, new Property(birthdate, 0));
					record.setProperty(PropertyType.age, new Property(age, 0));
					records.add(record);
				}

			}
			Collections.sort(records,
					(i1, i2) -> (((String) i1.getProperty(PropertyType.child).getValue())
							.compareTo((String) i2.getProperty(PropertyType.child).getValue())
							+ ((Integer) i1.getProperty(PropertyType.age).getValue())
									.compareTo((Integer) i2.getProperty(PropertyType.age).getValue())));

		}
		return records;
	}

	// US 33
	@SuppressWarnings("unchecked")
	public static List<Record> orphans(Parser p) {
		List<Record> records = new ArrayList<Record>();

		for (Record i : p.getFamilyList()) {

			if (i.getProperty(PropertyType.children) != null) {
				Object[] childrenIdList = ((List<String>) i.getProperty(PropertyType.children).getValue()).toArray();
				String[] childrenIds = Arrays.copyOf(childrenIdList, childrenIdList.length, String[].class);

				String familyId = i.getProperty(PropertyType.id) != null
						? (String) i.getProperty(PropertyType.id).getValue()
						: null;

				String husbandID = i.getProperty(PropertyType.husbandID) != null
						? (String) i.getProperty(PropertyType.husbandID).getValue()
						: null;
				String wifeID = i.getProperty(PropertyType.wifeID) != null
						? (String) i.getProperty(PropertyType.wifeID).getValue()
						: null;

				Predicate<Record> byHId = x -> x.getProperty(PropertyType.id).getValue().equals(husbandID);
				List<Record> resH = p.getIndividualList().stream().filter(byHId).collect(Collectors.<Record>toList());
				LocalDate husbandDeathDate = resH.get(0).getProperty(PropertyType.death) != null
						? (LocalDate) resH.get(0).getProperty(PropertyType.death).getValue()
						: null;
				Predicate<Record> byWId = x -> x.getProperty(PropertyType.id).getValue().equals(wifeID);
				List<Record> resW = p.getIndividualList().stream().filter(byWId).collect(Collectors.<Record>toList());
				LocalDate wifeDeathDate = resW.get(0).getProperty(PropertyType.death) != null
						? (LocalDate) resW.get(0).getProperty(PropertyType.death).getValue()
						: null;

				if (husbandDeathDate != null && wifeDeathDate != null) {

					for (String id1 : childrenIds) {

						Predicate<Record> byId1 = x -> x.getProperty(PropertyType.id).getValue().equals(id1);
						List<Record> res1 = p.getIndividualList().stream().filter(byId1)
								.collect(Collectors.<Record>toList());

						String name = res1.get(0).getProperty(PropertyType.name) != null
								? (String) res1.get(0).getProperty(PropertyType.name).getValue()
								: null;

						int age = (int) res1.get(0).getProperty(PropertyType.age).getValue();

						LocalDate childDeathDate = res1.get(0).getProperty(PropertyType.death) != null
								? (LocalDate) res1.get(0).getProperty(PropertyType.death).getValue()
								: null;

						if (age < 18) {
							Record record = new Record();
							record.setProperty(PropertyType.child, new Property(familyId, 0));
							record.setProperty(PropertyType.id, new Property(id1, 0));
							record.setProperty(PropertyType.name, new Property(name, 0));
							record.setProperty(PropertyType.birthday,
									new Property(res1.get(0).getProperty(PropertyType.birthday).getValue(), 0));
							record.setProperty(PropertyType.death, new Property(childDeathDate, 0));
							records.add(record);
						}
					}
				}
			}
		}

		return records;
	}

	// US31
	public static List<Record> livingSingle(Parser p) {
		List<Record> individuals = new ArrayList<Record>();

		for (Record i : p.getIndividualList()) {

			int individualAge = i.getProperty(PropertyType.age) != null
					? (int) i.getProperty(PropertyType.age).getValue()
					: null;

			if (i.getProperty(PropertyType.age) != null && individualAge > 30
					&& i.getProperty(PropertyType.spouse) == null) {
				individuals.add(i);
			}
		}
		return individuals;
	}

	// US 34
	public static List<Record> largeAgeDifferences(Parser p) {
		List<Record> records = new ArrayList<Record>();

		for (Record i : p.getFamilyList()) {

			String familyId = i.getProperty(PropertyType.id) != null
					? (String) i.getProperty(PropertyType.id).getValue()
					: null;

			LocalDate marriagedate = i.getProperty(PropertyType.married) != null
					? (LocalDate) i.getProperty(PropertyType.married).getValue()
					: null;

			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;

			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;

			Predicate<Record> byHId = x -> x.getProperty(PropertyType.id).getValue().equals(husbandID);
			List<Record> resH = p.getIndividualList().stream().filter(byHId).collect(Collectors.<Record>toList());

			int hAge = resH.get(0).getProperty(PropertyType.age) != null
					? (int) resH.get(0).getProperty(PropertyType.age).getValue()
					: null;

			LocalDate husbandBday = resH.get(0).getProperty(PropertyType.birthday) != null
					? (LocalDate) resH.get(0).getProperty(PropertyType.birthday).getValue()
					: null;

			Predicate<Record> byWId = x -> x.getProperty(PropertyType.id).getValue().equals(wifeID);
			List<Record> resW = p.getIndividualList().stream().filter(byWId).collect(Collectors.<Record>toList());

			Integer wAge = resW.get(0).getProperty(PropertyType.age) != null
					? (int) resW.get(0).getProperty(PropertyType.age).getValue()
					: null;

			LocalDate wifeBday = resH.get(0).getProperty(PropertyType.birthday) != null
					? (LocalDate) resH.get(0).getProperty(PropertyType.birthday).getValue()
					: null;

			if (husbandBday.isBefore(marriagedate) && wifeBday.isBefore(marriagedate)
					&& (resH.get(0).getProperty(PropertyType.alive).getValue().equals(true))
					&& (resW.get(0).getProperty(PropertyType.alive).getValue().equals(true))) {
				if ((hAge > wAge && hAge > 2 * wAge) || (wAge > hAge && wAge > 2 * hAge)) {
					Record record = new Record();
					record.setProperty(PropertyType.id, new Property(familyId, 0));
					record.setProperty(PropertyType.married, new Property(marriagedate, 0));
					record.setProperty(PropertyType.husbandID, new Property(husbandID, 0));
					record.setProperty(PropertyType.husbandName, i.getProperty(PropertyType.husbandName));
					record.setProperty(PropertyType.divorced, new Property(hAge, 0));
					record.setProperty(PropertyType.wifeID, new Property(wifeID, 0));
					record.setProperty(PropertyType.wifeName, i.getProperty(PropertyType.wifeName));
					record.setProperty(PropertyType.children, new Property(wAge, 0));

					records.add(record);
				}
			}
		}
		return records;
	}

	// US35
	public static List<Record> listRecentBirths(Parser p) {
		List<Record> individuals = new ArrayList<Record>();

		for (Record i : p.getIndividualList()) {

			LocalDate birthDate = i.getProperty(PropertyType.birthday) != null
					? (LocalDate) i.getProperty(PropertyType.birthday).getValue()
					: null;

			if (birthDate != null && birthDate.isBefore(LocalDate.now())) {
				LocalDate today = LocalDate.now();

				long days = ChronoUnit.DAYS.between(birthDate, today);
				if (days < 31 && days > 0) {
					individuals.add(i);
				}
			}
		}
		return individuals;
	}

	// US36
	public static List<Record> listRecentDeaths(Parser p) {
		List<Record> individuals = new ArrayList<Record>();

		for (Record i : p.getIndividualList()) {

			LocalDate deathDate = i.getProperty(PropertyType.death) != null
					? (LocalDate) i.getProperty(PropertyType.death).getValue()
					: null;
			if (deathDate != null && deathDate.isBefore(LocalDate.now())) {
				LocalDate today = LocalDate.now();
				long days = ChronoUnit.DAYS.between(deathDate, today);
				if (days < 31 && days > 0) {
					individuals.add(i);
				}
			}
		}
		return individuals;
	}

}