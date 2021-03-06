package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Test_List {

	// US38
	@Test
	public void testUpcomingBirthdays() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property((LocalDate.now()).plusDays(10).minusYears(5), 2));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Record> upcomingBirthdays = US_List.upcomingBirthdays(p);

		assertEquals(individual, upcomingBirthdays.get(0));
	}

	// US38
	@Test
	public void testUpcomingBirthdays_Valid() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2010, 04, 18), 2));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Record> upcomingBirthdays = US_List.upcomingBirthdays(p);

		assertEquals(0, upcomingBirthdays.size());
	}

	// US38
	@Test
	public void testUpcomingBirthdays_Invalid_BirthDate() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2020, 04, 18), 2));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Record> upcomingBirthdays = US_List.upcomingBirthdays(p);

		assertEquals(0, upcomingBirthdays.size());
	}

	// US38
	@Test
	public void testUpcomingBirthdays_Null() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Record> upcomingBirthdays = US_List.upcomingBirthdays(p);

		assertEquals(0, upcomingBirthdays.size());
	}

	// US39
	@Test
	public void testUpcomingAnniversaries() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.married, new Property((LocalDate.now()).plusDays(10).minusYears(5), 2));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Record> upcomingAnniversaries = US_List.upcomingAnniversaries(p);

		assertEquals(family, upcomingAnniversaries.get(0));
	}

	// US39
	@Test
	public void testUpcomingAnniversaries_Valid() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2010, 04, 18), 2));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Record> upcomingAnniversaries = US_List.upcomingAnniversaries(p);

		assertEquals(0, upcomingAnniversaries.size());
	}

	// US39
	@Test
	public void testUpcomingAnniversaries_Invalid_BirthDate() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2020, 04, 18), 2));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Record> upcomingAnniversaries = US_List.upcomingAnniversaries(p);

		assertEquals(0, upcomingAnniversaries.size());
	}

	// US39
	@Test
	public void testUpcomingAnniversaries_Null() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Record> upcomingAnniversaries = US_List.upcomingAnniversaries(p);

		assertEquals(0, upcomingAnniversaries.size());
	}

	// US29
	@Test
	public void testdeceased() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2010, 11, 18), 2));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Record> deceased = US_List.deceased(p);

		assertEquals(individual, deceased.get(0));
	}

	// US29
	@Test
	public void testdeceased_Valid() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Record> deceased = US_List.deceased(p);

		assertEquals(0, deceased.size());
	}

	// US29
	@Test
	public void testdeceased_Invalid_DeathDate() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2020, 11, 18), 2));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<Record> deceased = US_List.deceased(p);

		assertEquals(0, deceased.size());
	}

	// US30
	@Test
	public void testLivingMarried() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.name, new Property("Sophia /Smith/", 2));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1980, 01, 01), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 4));
		individual.setProperty(PropertyType.name, new Property("Eric /Johnson/", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1980, 01, 01), 6));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2015, 01, 01), 7));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 8));
		family.setProperty(PropertyType.wifeID, new Property("I01", 9));
		family.setProperty(PropertyType.husbandID, new Property("I02", 10));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2010, 03, 10), 11));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Record> livingMarried = US_List.livingMarried(p);

		Record record = new Record();
		record.setProperty(PropertyType.id, new Property("I01", 0));
		record.setProperty(PropertyType.name, new Property("Sophia /Smith/", 0));
		record.setProperty(PropertyType.birthday, new Property(LocalDate.of(1980, 01, 01), 0));
		record.setProperty(PropertyType.death, new Property(null, 0));
		record.setProperty(PropertyType.married, new Property(LocalDate.of(2010, 03, 10), 0));
		record.setProperty(PropertyType.divorced, new Property(null, 0));
		
		assertEquals(true, record.recordEquals(livingMarried.get(0)));

	}

	// US28
	@Test
	public void testorderSiblingsByAge() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.name, new Property("Sophia /Smith/", 2));
		individual.setProperty(PropertyType.gender, new Property("F", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1993, 02, 20), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 5));
		individual.setProperty(PropertyType.name, new Property("Jacob /Smith/", 6));
		individual.setProperty(PropertyType.gender, new Property("M", 7));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 02, 20), 8));
		IList.add(individual);
		
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 9));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		children.add("I02");
		family.setProperty(PropertyType.children, new Property(children, 10));
		FList.add(family);
		
		Parser p = new Parser(IList, FList);
		List<Record> siblings = US_List.orderSiblingsByAge(p);
		
		Record record1 = new Record();
		record1.setProperty(PropertyType.child, new Property("F01", 0));
		record1.setProperty(PropertyType.id, new Property("I01", 0));
		record1.setProperty(PropertyType.name, new Property("Sophia /Smith/", 0));
		record1.setProperty(PropertyType.gender, new Property("F", 0));
		record1.setProperty(PropertyType.birthday, new Property(LocalDate.of(1993, 02, 20), 0));
		record1.setProperty(PropertyType.age, new Property(25, 0));

		Record record2 = new Record();
		record2.setProperty(PropertyType.child, new Property("F01", 0));
		record2.setProperty(PropertyType.id, new Property("I02", 0));
		record2.setProperty(PropertyType.name, new Property("Jacob /Smith/", 0));
		record2.setProperty(PropertyType.gender, new Property("M", 0));
		record2.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 02, 20), 0));
		record2.setProperty(PropertyType.age, new Property(26, 0));

		assertEquals(true, record1.recordEquals(siblings.get(0)));
		assertEquals(true, record2.recordEquals(siblings.get(1)));
	}
	
	// US31
	@Test
	public void testLivingSingle_Valid() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1960, 11, 18), 2));
		IList.add(individual);
		
		Parser p = new Parser(IList, FList);
		List<Record> single = US_List.livingSingle(p);
		
		assertEquals(individual, single.get(0));

	}
	
	// US31
	@Test
	public void testLivingSingle_Invalid() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 11, 18), 2));
		IList.add(individual);
		
		Parser p = new Parser(IList, FList);
		List<Record> single = US_List.livingSingle(p);
		
		assertEquals(0, single.size());
	}
	
	// US34
	@Test
	public void testLargeAgeDifferences() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();


		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1960, 10, 10), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1991, 06, 11), 7));
		IList.add(individual);
		
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 9));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2016, 01, 01), 11));
		family.setProperty(PropertyType.husbandID, new Property("I01", 13));
		family.setProperty(PropertyType.husbandName, new Property("John /Davis/", 15));
		family.setProperty(PropertyType.wifeID, new Property("I02", 17));
		family.setProperty(PropertyType.wifeName, new Property("Maria /Davis/", 19));
		FList.add(family);
		
		Parser p = new Parser(IList, FList);
		List<Record> largeAgeDifference = US_List.largeAgeDifferences(p);
		
		Record record = new Record();
		record.setProperty(PropertyType.id, new Property("F01", 0));
		record.setProperty(PropertyType.married, new Property(LocalDate.of(2016, 01, 01), 0));
		record.setProperty(PropertyType.husbandID, new Property("I01", 0));
		record.setProperty(PropertyType.husbandName, new Property("John /Davis/",0));
		record.setProperty(PropertyType.wifeID, new Property("I02", 0));
		record.setProperty(PropertyType.wifeName, new Property("Maria /Davis/",0));
		record.setProperty(PropertyType.divorced, new Property(58, 0));
		record.setProperty(PropertyType.children, new Property(27, 0));

		assertEquals(true, record.recordEquals(largeAgeDifference.get(0)));
	}
	

	//US 33
	@Test
	public void testorphans() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.death, new Property(LocalDate.now().minusYears(1), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 1));
		individual.setProperty(PropertyType.death, new Property(LocalDate.now().minusYears(1), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 1));
		individual.setProperty(PropertyType.name, new Property("Sophia /Smith/", 2));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.now().minusYears(30), 3));
		individual.setProperty(PropertyType.death, new Property(LocalDate.now().minusYears(5), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I04", 5));
		individual.setProperty(PropertyType.name, new Property("Jacob /Smith/", 6));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.now().minusYears(2), 7));
		
		IList.add(individual);
		
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 9));
		family.setProperty(PropertyType.husbandID, new Property("I01", 13));
		family.setProperty(PropertyType.wifeID, new Property("I02", 0));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		children.add("I04");
		family.setProperty(PropertyType.children, new Property(children, 10));
		FList.add(family);
		
		Parser p1 = new Parser(IList, FList);
		List<Record> orphans = US_List.orphans(p1);
		
		Record record1 = new Record();
		record1.setProperty(PropertyType.child, new Property("F01", 0));
		record1.setProperty(PropertyType.id, new Property("I04", 0));
		record1.setProperty(PropertyType.name, new Property("Jacob /Smith/", 0));
		record1.setProperty(PropertyType.birthday, new Property(LocalDate.now().minusYears(2), 7));
		record1.setProperty(PropertyType.death, new Property(null, 7));
		
		assertEquals(true, record1.recordEquals(orphans.get(0)));
	}

	// US35
		@Test
		public void testlistRecentBirths() {
			List<Record> IList = new ArrayList<Record>();

			Record individual = new Record();
			individual.setProperty(PropertyType.id, new Property("F01", 1));
			individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2018, 11, 14), 2));
			IList.add(individual);

			Parser p = new Parser(IList, IList);
			List<Record> listRecentBirths = US_List.listRecentBirths(p);

			assertEquals(individual, listRecentBirths.get(0));
		}
		
		// US36
				@Test
				public void testlistRecentDeaths() {
					List<Record> IList = new ArrayList<Record>();

					Record individual = new Record();
					individual.setProperty(PropertyType.id, new Property("F01", 1));
					individual.setProperty(PropertyType.death, new Property(LocalDate.of(2018, 11, 14), 2));
					IList.add(individual);

					Parser p = new Parser(IList, IList);
					List<Record> listRecentDeaths = US_List.listRecentDeaths(p);

					assertEquals(individual, listRecentDeaths.get(0));
			}

}
