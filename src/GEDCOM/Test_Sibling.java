package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_Sibling {

	// US13
	@Test
	public void testSiblingSpacing() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 26), 4));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		children.add("I02");
		family.setProperty(PropertyType.children, new Property(children, 6));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.siblingSpacing(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US13");
		error.setLineNumber(4);
		error.setId("I02");
		error.setMessage("Difference between birthdate (1992-02-26) with sibling I01 (1992-02-20) is not valid");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US13
	@Test
	public void testSiblingSpacing_valid1() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 21), 4));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		children.add("I02");
		family.setProperty(PropertyType.children, new Property(children, 6));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.siblingSpacing(p);

		assertEquals(0, errors.size());
	}

	// US13
	@Test
	public void testSiblingSpacing_valid2() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1993, 2, 21), 4));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		children.add("I02");
		family.setProperty(PropertyType.children, new Property(children, 6));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.siblingSpacing(p);

		assertEquals(0, errors.size());
	}

	// US13
	@Test
	public void testSiblingSpacing_noChildren() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.siblingSpacing(p);

		assertEquals(0, errors.size());
	}

	// US13
	@Test
	public void testSiblingSpacing_singleChild() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 2));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 3));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		family.setProperty(PropertyType.children, new Property(children, 4));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.siblingSpacing(p);

		assertEquals(0, errors.size());
	}

}
