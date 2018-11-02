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
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2010, 11, 18), 2));
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
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2010, 11, 18), 2));
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

}
