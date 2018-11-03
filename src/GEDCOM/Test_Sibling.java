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

	// US14
	@Test
	public void testFiveSiblingSpacing() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 21), 6));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I04", 7));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 8));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I05", 9));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 21), 10));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I06", 11));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 12));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 13));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		children.add("I02");
		children.add("I03");
		children.add("I04");
		children.add("I05");
		children.add("I06");
		family.setProperty(PropertyType.children, new Property(children, 14));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.fiveSiblingSpacing(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US14");
		error.setLineNumber(13);
		error.setId("F01");
		error.setMessage("Family has more than 5 siblings born within 2 days (Children IDs : I01,I02,I03,I04,I05,I06)");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US14
	@Test
	public void testFiveSiblingSpacing_noChildren() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.fiveSiblingSpacing(p);

		assertEquals(0, errors.size());
	}

	// US14
	@Test
	public void testFiveSiblingSpacing_TwoChildren() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 4));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		children.add("I02");
		family.setProperty(PropertyType.children, new Property(children, 6));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.fiveSiblingSpacing(p);

		assertEquals(0, errors.size());
	}

	// US14
	@Test
	public void testFiveSiblingSpacing_MultipleChildren() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1993, 2, 21), 6));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I04", 7));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1993, 2, 20), 8));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I05", 9));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 21), 10));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I06", 11));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 2, 20), 12));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 13));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		children.add("I02");
		children.add("I03");
		children.add("I04");
		children.add("I05");
		children.add("I06");
		family.setProperty(PropertyType.children, new Property(children, 14));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.fiveSiblingSpacing(p);

		assertEquals(0, errors.size());
	}
	
	// US15
	@Test
	public void testFewerThanFifteenSiblings_Error() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1940, 2, 20), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1950, 4, 3), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 5));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I04", 7));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I05", 9));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I06", 11));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I07", 13));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I08", 15));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I09", 17));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I10", 19));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I11", 21));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I12", 23));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I13", 25));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I14", 27));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I15", 29));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I16", 31));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I17", 33));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I18", 35));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 37));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		children.add("I04");
		children.add("I05");
		children.add("I06");
		children.add("I07");
		children.add("I08");
		children.add("I09");
		children.add("I10");
		children.add("I11");
		children.add("I12");
		children.add("I13");
		children.add("I14");
		children.add("I15");
		children.add("I16");
		children.add("I17");
		children.add("I18");
		family.setProperty(PropertyType.children, new Property(children, 39));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.fewerThanFifteenSiblings(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US15");
		error.setLineNumber(37);
		error.setId("F01");
		error.setMessage("Family (F01) has more than 15 siblings (Children IDs: [I03, I04, I05, I06, I07, I08, I09, I10, I11, I12, I13, I14, I15, I16, I17, I18]).");

		assertEquals(error.toString(), errors.get(0).toString());
	}
	
	// US15
	@Test
	public void testFewerThanFifteenSiblings_Valid() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1940, 2, 20), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1950, 4, 3), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 5));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I04", 7));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I05", 9));
		IList.add(individual);
		
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 11));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		children.add("I04");
		children.add("I05");
		family.setProperty(PropertyType.children, new Property(children, 13));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Sibling.fewerThanFifteenSiblings(p);

		assertEquals(0, errors.size());
	}
}
