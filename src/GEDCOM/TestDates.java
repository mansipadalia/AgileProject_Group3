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
}