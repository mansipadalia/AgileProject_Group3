package GEDCOM;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_Consistency {

	// US26
	@Test
	public void testCorrespondingEntries_1() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		List<String> spouse = new ArrayList<String>();
		spouse.add("F01");
		individual.setProperty(PropertyType.spouse, new Property(spouse, 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		spouse = new ArrayList<String>();
		spouse.add("F02");
		individual.setProperty(PropertyType.spouse, new Property(spouse, 4));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		family.setProperty(PropertyType.husbandID, new Property("I01", 6));
		family.setProperty(PropertyType.wifeID, new Property("I02", 7));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Consistency.correspondingEntries(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.FAMILY);
		error.setUserStoryNumber("US26");
		error.setLineNumber(7);
		error.setId("F01");
		error.setMessage("Inconsistent Wife Entry for Wife ID - I02, Spouse for - (F02)");

		assertEquals(error.toString(), errors.get(0).toString());

		error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US26");
		error.setLineNumber(4);
		error.setId("I02");
		error.setMessage("Inconsistent Spouse Entry for Family ID - F02, Spouse are - (,)");

		assertEquals(error.toString(), errors.get(1).toString());
	}
	
	// US26
		@Test
		public void testCorrespondingEntries_2() {
			List<Record> IList = new ArrayList<Record>();
			List<Record> FList = new ArrayList<Record>();

			Record individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I01", 1));
			List<String> spouse = new ArrayList<String>();
			spouse.add("F02");
			individual.setProperty(PropertyType.spouse, new Property(spouse, 2));
			IList.add(individual);
			individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I02", 3));
			spouse = new ArrayList<String>();
			spouse.add("F01");
			individual.setProperty(PropertyType.spouse, new Property(spouse, 4));
			IList.add(individual);

			Record family = new Record();
			family.setProperty(PropertyType.id, new Property("F01", 5));
			family.setProperty(PropertyType.husbandID, new Property("I01", 6));
			family.setProperty(PropertyType.wifeID, new Property("I02", 7));
			FList.add(family);

			Parser p = new Parser(IList, FList);
			List<Error> errors = US_Consistency.correspondingEntries(p);

			Error error = new Error();
			error.setErrorType(ErrorType.ERROR);
			error.setRecordType(RecordType.FAMILY);
			error.setUserStoryNumber("US26");
			error.setLineNumber(6);
			error.setId("F01");
			error.setMessage("Inconsistent Husband Entry for Husband ID - I01, Spouse for - (F02)");

			assertEquals(error.toString(), errors.get(0).toString());

			error = new Error();
			error.setErrorType(ErrorType.ERROR);
			error.setRecordType(RecordType.INDIVIDUAL);
			error.setUserStoryNumber("US26");
			error.setLineNumber(2);
			error.setId("I01");
			error.setMessage("Inconsistent Spouse Entry for Family ID - F02, Spouse are - (,)");

			assertEquals(error.toString(), errors.get(1).toString());
		}


	// US26
	@Test
	public void testCorrespondingEntries_3() {
		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		List<String> spouse = new ArrayList<String>();
		spouse.add("F01");
		individual.setProperty(PropertyType.spouse, new Property(spouse, 2));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 3));
		spouse = new ArrayList<String>();
		spouse.add("F01");
		individual.setProperty(PropertyType.spouse, new Property(spouse, 4));
		IList.add(individual);

		Record family = new Record();
		family.setProperty(PropertyType.id, new Property("F01", 5));
		FList.add(family);

		Parser p = new Parser(IList, FList);
		List<Error> errors = US_Consistency.correspondingEntries(p);

		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US26");
		error.setLineNumber(2);
		error.setId("I01");
		error.setMessage("Inconsistent Spouse Entry for Family ID - F01, Spouse are - (,)");

		assertEquals(error.toString(), errors.get(0).toString());

		error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US26");
		error.setLineNumber(4);
		error.setId("I02");
		error.setMessage("Inconsistent Spouse Entry for Family ID - F01, Spouse are - (,)");

		assertEquals(error.toString(), errors.get(1).toString());
	}

	// US26
		@Test
		public void testCorrespondingEntries_4() {
			List<Record> IList = new ArrayList<Record>();
			List<Record> FList = new ArrayList<Record>();

			Record individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I01", 1));
			individual.setProperty(PropertyType.child, new Property("F01", 2));
			IList.add(individual);
			individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I02", 3));
			individual.setProperty(PropertyType.child, new Property("F01", 4));
			IList.add(individual);

			Record family = new Record();
			family.setProperty(PropertyType.id, new Property("F01", 5));
			List<String> children = new ArrayList<String>();
			children.add("I02");
			family.setProperty(PropertyType.children, new Property(children, 6));
			FList.add(family);

			Parser p = new Parser(IList, FList);
			List<Error> errors = US_Consistency.correspondingEntries(p);

			Error error = new Error();
			error.setErrorType(ErrorType.ERROR);
			error.setRecordType(RecordType.INDIVIDUAL);
			error.setUserStoryNumber("US26");
			error.setLineNumber(2);
			error.setId("I01");
			error.setMessage("Inconsistent Children Entry for Child ID - F01, Children are - ([I02])");

			assertEquals(error.toString(), errors.get(0).toString());
		}
		
		// US26
				@Test
				public void testCorrespondingEntries_5() {
					List<Record> IList = new ArrayList<Record>();
					List<Record> FList = new ArrayList<Record>();

					Record individual = new Record();
					individual.setProperty(PropertyType.id, new Property("I01", 1));
					IList.add(individual);
					individual = new Record();
					individual.setProperty(PropertyType.id, new Property("I02", 3));
					individual.setProperty(PropertyType.child, new Property("F01", 4));
					IList.add(individual);

					Record family = new Record();
					family.setProperty(PropertyType.id, new Property("F01", 5));
					List<String> children = new ArrayList<String>();
					children.add("I01");
					children.add("I02");
					family.setProperty(PropertyType.children, new Property(children, 6));
					FList.add(family);

					Parser p = new Parser(IList, FList);
					List<Error> errors = US_Consistency.correspondingEntries(p);

					Error error = new Error();
					error.setErrorType(ErrorType.ERROR);
					error.setRecordType(RecordType.FAMILY);
					error.setUserStoryNumber("US26");
					error.setLineNumber(6);
					error.setId("F01");
					error.setMessage("Inconsistent Child Entry for Child ID - I01, Child for - ()");

					assertEquals(error.toString(), errors.get(0).toString());
				}
}
