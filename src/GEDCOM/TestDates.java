package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestDates {

	@Test
	public void testBirthBeforeMarriage() {
		List<Individual> IList = new ArrayList<Individual>();
		List<Family> FList = new ArrayList<Family>();

		Individual individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 6, 23), 2));
		IList.add(individual);
		individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1995, 4, 18), 4));
		IList.add(individual);

		Family family = new Family();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1998, 10, 7), 8));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<String> errors = UserStoriesDates.birthBeforeMarriage(p);

		assertEquals("ERROR: INDIVIDUAL: US02: 8: I01: Marriage Date occurs 1998-10-07 before Birth Date 2000-06-23.",
				errors.get(0));
	}

	@Test
	public void testRejectIllegitimateDatesValid() {
		try {
			// we are parsing all our dates to LocalDate and thus the following test suffice
			LocalDate date = LocalDate.of(2000, 2, 30);
			assertTrue(false);
		} catch (java.time.DateTimeException de) {
			assertTrue(true);
		}
	}

	@Test
	public void testRejectIllegitimateDatesInvalid() {
		try {
			// we are parsing all our dates to LocalDate and thus the following test suffice
			LocalDate date = LocalDate.of(1998, 6, 13);
			assertTrue(true);
		} catch (java.time.DateTimeException de) {
			assertTrue(false);
		}
	}

	@Test
	public void testMarriageBeforeDivorce() {

		List<Family> FList = new ArrayList<Family>();

		Family family = new Family();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2009, 01, 01), 2));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2009, 01, 01), 3));
		FList.add(family);
		family = new Family();
		family.setProperty(PropertyType.id, new Property("F02", 4));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2013, 01, 01), 5));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2015, 01, 01), 6));
		FList.add(family);
		family = new Family();
		family.setProperty(PropertyType.id, new Property("F02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1996, 01, 01), 8));
		FList.add(family);

		Parser p = new Parser(null, FList);
		List<String> errors = UserStoriesDates.marriageBeforeDivorce(p);

		assertEquals("ERROR: FAMILY: US04: 3: F01: Divorce 2009-01-01 occurs along with marriage on 2009-01-01.",
				errors.get(0));
	}

	@Test
	public void testMarriageBeforeDeath() {

		List<Individual> IList = new ArrayList<Individual>();
		List<Family> FList = new ArrayList<Family>();

		Individual individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2010, 01, 01), 2));
		IList.add(individual);
		individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		IList.add(individual);

		Family family = new Family();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2015, 01, 01), 8));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<String> errors = UserStoriesDates.marriageBeforeDeath(p);

		assertEquals(
				"ERROR: FAMILY: US05: 8: F01: Marriage occurs 2015-01-01 after husband's (I01) death on 2010-01-01.",
				errors.get(0));
	}

	@Test
	public void testBirthBeforeDeath() {
		List<Individual> IList = new ArrayList<Individual>();

		Individual individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 6, 23), 2));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2012, 2, 7), 3));
		IList.add(individual);
		individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I02", 4));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1886, 4, 12), 5));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(1880, 1, 22), 6));
		IList.add(individual);
		individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I03", 7));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2005, 7, 20), 8));
		IList.add(individual);

		Parser p = new Parser(IList, null);
		List<String> errors = UserStoriesDates.birthBeforeDeath(p);

		assertEquals("ERROR: INDIVIDUAL: US03: 6: I02: Death Date occurs 1880-01-22 before Birth Date 1886-04-12.",
				errors.get(0));
	}

	@Test
	public void testdatesBeforeCurrentDate_1() {
		List<Individual> IList = new ArrayList<Individual>();
		List<Family> FList = new ArrayList<Family>();

		Individual individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1985, 6, 23), 2));
		IList.add(individual);
		individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2019, 4, 18), 4));
		IList.add(individual);

		Parser p = new Parser(IList, FList);
		List<String> errors = UserStoriesDates.datesBeforeCurrentDate(p);

		assertEquals("ERROR: INDIVIDUAL: US01: 4: I02 : Birthday (2019-04-18) occurs in the future.", errors.get(0));
	}

	@Test
	public void testdatesBeforeCurrentDate_2() {
		List<Individual> IList = new ArrayList<Individual>();
		List<Family> FList = new ArrayList<Family>();

		Individual individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1985, 6, 23), 2));
		IList.add(individual);
		individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 4, 18), 4));
		IList.add(individual);

		Family family = new Family();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2020, 10, 7), 8));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<String> errors = UserStoriesDates.datesBeforeCurrentDate(p);

		assertEquals("ERROR: FAMILY: US01: 8: F01 : Marriage (2020-10-07) occurs in the future.", errors.get(0));
	}

	@Test
	public void testlessThanOneFiftyAge() {
		List<Individual> IList = new ArrayList<Individual>();

		Individual individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I20", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1800, 01, 01), 2));
		IList.add(individual);
		individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I21", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1999, 01, 01), 4));
		IList.add(individual);

		Parser p = new Parser(IList, null);
		List<String> errors = UserStoriesDates.lessThanOneFiftyAge(p);

		assertEquals("ERROR: INDIVIDUAL: US07: 2: I20: More than 150 years old, Birth Date (1800-01-01).",
				errors.get(0));
	}
}
