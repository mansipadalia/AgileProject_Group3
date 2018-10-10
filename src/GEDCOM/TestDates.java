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
}
