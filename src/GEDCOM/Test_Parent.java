package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_Parent {

	// US08
	@Test
	public void testBirthBeforeMarriageOfParents_1() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 1, 1), 2));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 3));
		family.setProperty(PropertyType.married, new Property(LocalDate.of(2000, 1, 1), 4));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		family.setProperty(PropertyType.children, new Property(children, 5));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeMarriageOfParents(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US08");
		error.setLineNumber(4);
		error.setId("F01");
		error.setMessage("Difference between birthdate (1992-01-01) and marriage Date (2000-01-01) is not valid");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US08
	@Test
	public void testBirthBeforeMarriageOfParents_2() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 10, 1), 2));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 3));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2000, 1, 1), 4));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		family.setProperty(PropertyType.children, new Property(children, 5));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeMarriageOfParents(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US08");
		error.setLineNumber(4);
		error.setId("I01");
		error.setMessage("Difference between birthdate (2000-10-01) and divorce Date (2000-01-01) is not valid");

		assertEquals(error.toString(), errors.get(0).toString());
	}
	
	// US08
	@Test
	public void testBirthBeforeMarriageOfParents_Valid() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 7, 1), 2));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 3));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2000, 1, 1), 4));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		family.setProperty(PropertyType.children, new Property(children, 5));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeMarriageOfParents(p);

		assertEquals(0, errors.size());
	}

	// US08
	@Test
	public void testBirthBeforeMarriageOfParents_null_1() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 3));
		family.setProperty(PropertyType.divorced, new Property(LocalDate.of(2000, 1, 1), 4));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeMarriageOfParents(p);

		assertEquals(0, errors.size());
	}

	// US08
	@Test
	public void testBirthBeforeMarriageOfParents_null_2() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1992, 1, 1), 2));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 3));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		family.setProperty(PropertyType.children, new Property(children, 5));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeMarriageOfParents(p);

		assertEquals(0, errors.size());
	}

	// US08
	@Test
	public void testBirthBeforeMarriageOfParents_null_3() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 3));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		family.setProperty(PropertyType.children, new Property(children, 5));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeMarriageOfParents(p);

		assertEquals(0, errors.size());
	}

	// US09
	@Test
	public void testBirthBeforeDeathOfParents_1() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 2));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2000, 1, 1), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 4));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 9, 1), 5));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 6));
		family.setProperty(PropertyType.husbandID, new Property("I01", 7));
		family.setProperty(PropertyType.wifeID, new Property("I02", 8));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		family.setProperty(PropertyType.children, new Property(children, 9));
		FList.add(family);

		Parser p1 = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeDeathOfParents(p1);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US09");
		error.setLineNumber(5);
		error.setId("I03");
		error.setMessage("Birth date (2000-09-01) after death of Mother (ID: I02) (2000-01-01)");

		assertEquals(error.toString(), errors.get(0).toString());
	}

	// US09
	@Test
	public void testBirthBeforeDeathOfParents_2() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 2));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2000, 1, 1), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 4));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 10, 1), 5));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 6));
		family.setProperty(PropertyType.husbandID, new Property("I02", 7));
		family.setProperty(PropertyType.wifeID, new Property("I01", 8));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		family.setProperty(PropertyType.children, new Property(children, 9));
		FList.add(family);

		Parser p1 = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeDeathOfParents(p1);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US09");
		error.setLineNumber(5);
		error.setId("I03");
		error.setMessage("Birth date (2000-10-01) after more than 9 months of death of Father (ID: I02) (2000-01-01)");

		assertEquals(error.toString(), errors.get(0).toString());
	}
	
	// US09
	@Test
	public void testBirthBeforeDeathOfParents_Valid() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 2));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2000, 1, 1), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 4));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 8, 1), 5));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 6));
		family.setProperty(PropertyType.husbandID, new Property("I02", 7));
		family.setProperty(PropertyType.wifeID, new Property("I01", 8));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		family.setProperty(PropertyType.children, new Property(children, 9));
		FList.add(family);

		Parser p1 = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeDeathOfParents(p1);

		assertEquals(0, errors.size());
	}
	
	// US09
	@Test
	public void testBirthBeforeDeathOfParents_null_1() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 2));
		List<String> children = new ArrayList<String>();
		children.add("I01");
		family.setProperty(PropertyType.children, new Property(children, 9));
		FList.add(family);

		Parser p1 = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeDeathOfParents(p1);

		assertEquals(0, errors.size());
	}
		
	// US09
	@Test
	public void testBirthBeforeDeathOfParents_null_2() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 1));
		FList.add(family);

		Parser p1 = new Parser(IList, FList);
		List<Error> errors = US_Parent.birthBeforeDeathOfParents(p1);

		assertEquals(0, errors.size());
	}
	
	// US12
	@Test
	public void testParentsNotTooOld_HusbandError() {

		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1915, 01, 01), 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1960, 01, 01), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2018, 01, 01), 6));
		IList.add(individual);

		Record family = new Record();
		
		family.setProperty(PropertyType.id, new Property("F01", 7));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		family.setProperty(PropertyType.husbandID, new Property("I01", 8));
		family.setProperty(PropertyType.wifeID, new Property("I02", 9));
		family.setProperty(PropertyType.children, new Property(children, 10));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		
		List<Error> husbandErrors = US_Parent.parentsNotTooOld(p);

		Error husbandError = new Error();
		husbandError.setErrorType(ErrorType.ERROR);
		husbandError.setRecordType(RecordType.FAMILY);
		husbandError.setUserStoryNumber("US12");
		husbandError.setLineNumber(6);
		husbandError.setId("F01");
		husbandError.setMessage("Parent (I01) (Birth Date: 1915-01-01) is older than his child (I03) (Birth Date: 2018-01-01).");

		assertEquals(husbandError.toString(), husbandErrors.get(0).toString());
	}
	
	// US12
	@Test
	public void testParentsNotTooOld_WifeError() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
	
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I07", 21));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1900, 01, 01), 22));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I08", 23));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1882, 01, 01), 24));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I09", 25));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1945, 01, 01), 26));
		IList.add(individual);
	
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F03", 27));
		List<String> childList = new ArrayList<String>();
		childList.add("I09");
		family.setProperty(PropertyType.husbandID, new Property("I07", 28));
		family.setProperty(PropertyType.wifeID, new Property("I08", 29));
		family.setProperty(PropertyType.children, new Property(childList, 30));
		FList.add(family);
				
		Parser p = new Parser(IList, FList);

		List<Error> wifeErrors = US_Parent.parentsNotTooOld(p);

		Error wifeError = new Error();
		wifeError.setErrorType(ErrorType.ERROR);
		wifeError.setRecordType(RecordType.FAMILY);
		wifeError.setUserStoryNumber("US12");
		wifeError.setLineNumber(26);
		wifeError.setId("F03");
		wifeError.setMessage("Parent (I08) (Birth Date: 1882-01-01) is older than her child (I09) (Birth Date: 1945-01-01).");

		assertEquals(wifeError.toString(), wifeErrors.get(0).toString());
	}
	
	// US12
	@Test
	public void testParentsNotTooOld_Valid() {
	
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
	
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I04", 11));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 01, 01), 12));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I05", 13));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2001, 01, 01), 14));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I06", 15));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2018, 01, 01), 16));
		IList.add(individual);
		
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F02", 17));
		List<String> child = new ArrayList<String>();
		child.add("I06");
		family.setProperty(PropertyType.husbandID, new Property("I04", 18));
		family.setProperty(PropertyType.wifeID, new Property("I05", 19));
		family.setProperty(PropertyType.children, new Property(child, 20));
		FList.add(family);
		
		Parser p = new Parser(IList, FList);

		List<Error> valid = US_Parent.parentsNotTooOld(p);
		assertEquals(0, valid.size());
	}
	
	//US12
	@Test
	public void testParentsNotTooOld_Null() {
		
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
		
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I04", 13));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I05", 15));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I06", 17));
		IList.add(individual);
		
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F02", 18));
		List<String> child = new ArrayList<String>();
		child.add("I06");
		family.setProperty(PropertyType.husbandID, new Property("I04", 19));
		family.setProperty(PropertyType.wifeID, new Property("I05", 20));
		family.setProperty(PropertyType.children, new Property(child, 21));
		FList.add(family);
					
		Parser p = new Parser(IList, FList);

		List<Error> nullErrors = US_Parent.parentsNotTooOld(p);
		assertEquals(0, nullErrors.size());
	}

	//US12
	@Test
	public void testParentsNotTooOld_HusbBdayNull() {
		
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
		
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1970, 01, 01), 6));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 7));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2018, 01, 01), 8));
		IList.add(individual);
		
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 8));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		family.setProperty(PropertyType.husbandID, new Property("I01", 9));
		family.setProperty(PropertyType.wifeID, new Property("I02", 10));
		family.setProperty(PropertyType.children, new Property(children, 11));
		FList.add(family);
		
		Parser p = new Parser(IList, FList);
		
		List<Error> errors = US_Parent.parentsNotTooOld(p);
		assertEquals(0, errors.size());
	}
	
	//US12
	@Test
	public void testParentsNotTooOld_WifeBdayNull() {
		
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
		
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1960, 01, 01), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 5));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 7));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2018, 01, 01), 8));
		IList.add(individual);
		
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 8));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		family.setProperty(PropertyType.husbandID, new Property("I01", 9));
		family.setProperty(PropertyType.wifeID, new Property("I02", 10));
		family.setProperty(PropertyType.children, new Property(children, 11));
		FList.add(family);
		
		Parser p = new Parser(IList, FList);
		
		List<Error> errors = US_Parent.parentsNotTooOld(p);
		assertEquals(0, errors.size());
	}
	
	//US12
	@Test
	public void testParentsNotTooOld_ChildBdayNull() {
		
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
		
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 3));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 01, 01), 4));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 5));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2003, 01, 01), 6));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 7));
		IList.add(individual);
		
		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 8));
		List<String> children = new ArrayList<String>();
		children.add("I03");
		family.setProperty(PropertyType.husbandID, new Property("I01", 9));
		family.setProperty(PropertyType.wifeID, new Property("I02", 10));
		family.setProperty(PropertyType.children, new Property(children, 11));
		FList.add(family);
		
		Parser p = new Parser(IList, FList);
		
		List<Error> errors = US_Parent.parentsNotTooOld(p);
		assertEquals(0, errors.size());
	}
}
