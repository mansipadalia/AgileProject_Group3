package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Test_List {

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

	

}
