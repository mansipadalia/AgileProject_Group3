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
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000,6,23), 2));
		IList.add(individual);
		individual = new Individual();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1995,4,18), 4));
		IList.add(individual);
		
		Family family = new Family();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(1998,10,7), 8));
		FList.add(family);
		
		Parser p = new Parser(IList, FList);
		List<String> errors = UserStoriesDates.birthBeforeMarriage(p);
		
		assertEquals("Error US02: At line 8, 'I01' got married at 1998-10-07 before birth at 2000-06-23.",errors.get(0));
	}

}
