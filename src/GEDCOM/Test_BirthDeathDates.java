package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_BirthDeathDates {

	// US03
	@Test
	public void testBirthBeforeDeathError() {
		List<Record> IList = new ArrayList<Record>();

		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 6, 23), 2));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2012, 2, 7), 3));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I02", 4));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1886, 4, 12), 5));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(1880, 1, 22), 6));
		IList.add(individual);
		individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I03", 7));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2005, 7, 20), 8));
		IList.add(individual);

		Parser p = new Parser(IList, null);
		List<Error> errors = US_BirthDeathDates.birthBeforeDeath(p);
		
		Error error = new Error();
		error.setErrorType(ErrorType.ERROR);
		error.setRecordType(RecordType.INDIVIDUAL);
		error.setUserStoryNumber("US03");
		error.setLineNumber(6);
		error.setId("I02");
		error.setMessage("Death Date 1880-01-22 occurs before Birth Date 1886-04-12");

		assertEquals(error.toString(),errors.get(0).toString());
	}
	
	// US03
	public void testBirthBeforeDeathSuccess() {

		List<Record> IList = new ArrayList<Record>();
		List<Record> FList = new ArrayList<Record>();
		Record individual = new Record();
		individual.setProperty(PropertyType.id, new Property("I01", 1));
		individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(2000, 6, 23), 2));
		individual.setProperty(PropertyType.death, new Property(LocalDate.of(2016, 01, 01), 3));
		IList.add(individual);
		Parser p = new Parser(IList, FList);
		List<Error> errors = US_MarriageDivorceDates.marriageBeforeDeath(p);

		assertEquals(0, errors.size());
	}
	
	// US12
	@Test
	public void testParentsNotTooOld() {

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
			
			List<Error> husbanderrors = US_BirthDeathDates.parentsNotTooOld(p);

			Error husbandError = new Error();
			husbandError.setErrorType(ErrorType.ERROR);
			husbandError.setRecordType(RecordType.FAMILY);
			husbandError.setUserStoryNumber("US12");
			husbandError.setLineNumber(6);
			husbandError.setId("F01");
			husbandError.setMessage("Parent (I01) (Birth Date: 1915-01-01) is older than his child (I03) (Birth Date: 2018-01-01).");

			assertEquals(husbandError.toString(), husbanderrors.get(0).toString());
			
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
			
			family.setProperty(PropertyType.id, new Property("F02", 17));
			List<String> child = new ArrayList<String>();
			child.add("I06");
			family.setProperty(PropertyType.husbandID, new Property("I04", 18));
			family.setProperty(PropertyType.wifeID, new Property("I05", 19));
			family.setProperty(PropertyType.children, new Property(child, 20));
			FList.add(family);
			
			List<Error> validError = US_BirthDeathDates.parentsNotTooOld(p);
			assertEquals(0, validError.size());
			
			individual.setProperty(PropertyType.id, new Property("I07", 21));
			IList.add(individual);
			individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I08", 23));
			individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1882, 01, 01), 24));
			IList.add(individual);
			individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I09", 25));
			individual.setProperty(PropertyType.birthday, new Property(LocalDate.of(1945, 01, 01), 26));
			IList.add(individual);
			
			family.setProperty(PropertyType.id, new Property("F03", 27));
			List<String> childList = new ArrayList<String>();
			childList.add("I09");
			family.setProperty(PropertyType.husbandID, new Property("I07", 28));
			family.setProperty(PropertyType.wifeID, new Property("I08", 29));
			family.setProperty(PropertyType.children, new Property(childList, 30));
			FList.add(family);
						
			List<Error> wifeErrors = US_BirthDeathDates.parentsNotTooOld(p);

			Error wifeError = new Error();
			wifeError.setErrorType(ErrorType.ERROR);
			wifeError.setRecordType(RecordType.FAMILY);
			wifeError.setUserStoryNumber("US12");
			wifeError.setLineNumber(26);
			wifeError.setId("F03");
			wifeError.setMessage("Parent (I08) (Birth Date: 1882-01-01) is older than her child (I09) (Birth Date: 1945-01-01).");

			assertEquals(wifeError.toString(), wifeErrors.get(0).toString());
		}
	
	//US12
	@Test
	public void testParentsNotTooOld_Null() {
			List<Record> IList = new ArrayList<Record>();
			List<Record> FList = new ArrayList<Record>();
			
			Record individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I01", 3));
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
			
			List<Error> errors = US_BirthDeathDates.parentsNotTooOld(p);
			assertEquals(0, errors.size());
			
			individual.setProperty(PropertyType.id, new Property("I04", 13));
			IList.add(individual);
			individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I05", 15));
			IList.add(individual);
			individual = new Record();
			individual.setProperty(PropertyType.id, new Property("I06", 17));
			IList.add(individual);
			
			family.setProperty(PropertyType.id, new Property("F02", 18));
			List<String> child = new ArrayList<String>();
			child.add("I06");
			family.setProperty(PropertyType.husbandID, new Property("I04", 19));
			family.setProperty(PropertyType.wifeID, new Property("I05", 20));
			family.setProperty(PropertyType.children, new Property(child, 21));
			FList.add(family);
						
			List<Error> nullErrors = US_BirthDeathDates.parentsNotTooOld(p);
			assertEquals(0, nullErrors.size());
	}
}
