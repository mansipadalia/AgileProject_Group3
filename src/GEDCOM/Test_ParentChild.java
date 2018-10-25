package GEDCOM;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

import org.junit.Test;

public class Test_ParentChild {

	//US 08
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
		List<Error> errors = US_ParentChild.birthBeforeMarriageOfParents(p);

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
		List<Error> errors = US_ParentChild.birthBeforeMarriageOfParents(p);

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
			List<Error> errors = US_ParentChild.birthBeforeMarriageOfParents(p);

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
		List<Error> errors = US_ParentChild.birthBeforeMarriageOfParents(p);

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
		List<Error> errors = US_ParentChild.birthBeforeMarriageOfParents(p);

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
		List<Error> errors = US_ParentChild.birthBeforeMarriageOfParents(p);

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
		List<Error> errors = US_ParentChild.birthBeforeDeathOfParents(p1);

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
		List<Error> errors = US_ParentChild.birthBeforeDeathOfParents(p1);

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
			List<Error> errors = US_ParentChild.birthBeforeDeathOfParents(p1);

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
			List<Error> errors = US_ParentChild.birthBeforeDeathOfParents(p1);

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
					List<Error> errors = US_ParentChild.birthBeforeDeathOfParents(p1);

					assertEquals(0, errors.size());
				}


}
