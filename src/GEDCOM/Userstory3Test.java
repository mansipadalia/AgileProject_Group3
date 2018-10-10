package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Userstory3Test {

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
