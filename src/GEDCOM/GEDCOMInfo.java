package GEDCOM;

import java.util.ArrayList;
import java.util.List;

public class GEDCOMInfo {

	public static void main(String[] args) {
		Parser p = new Parser();

		displayIndividuals(p.getIndividualList());
		displayFamilies(p.getFamilyList());
		displayErrors(p);
		
	}

	private static void displayErrors(Parser p) {
		List<Error> errors = new ArrayList<Error>();
		
		errors.addAll(US_MarriageDivorceDates.birthBeforeMarriage(p));
		errors.addAll(US_MarriageDivorceDates.marriageBeforeDivorce(p));
		errors.addAll(US_MarriageDivorceDates.marriageBeforeDeath(p));
		errors.addAll(US_BirthDeathDates.birthBeforeDeath(p));
		errors.addAll(US_GeneralDates.datesBeforeCurrentDate(p));
		errors.addAll(US_Age.lessThanOneFiftyAge(p));
		errors.addAll(US_Uniqueness.uniqueNameBirthDate(p));
		errors.addAll(US_Uniqueness.uniqueFirstNameBirthDate(p));
		errors.addAll(US_Sibling.siblingSpacing(p));
		errors.addAll(US_Sibling.fiveSiblingSpacing(p));
		errors.addAll(US_Parent.birthBeforeMarriageOfParents(p));
		errors.addAll(US_Parent.birthBeforeDeathOfParents(p));
		errors.addAll(US_MarriageDivorceDates.marriageAfter14(p));
		errors.addAll(US_BirthDeathDates.parentsNotTooOld(p));
		
		for (Error i : errors) {
			System.out.println(i.toString());
		}
		System.out.println("");
		
		List<Record> List_US38 = US_List.upcomingBirthdays(p);
		System.out.println("US38 : List of Upcoming Birthdays");
		displayIndividuals(List_US38);
		
	}

	@SuppressWarnings({ "unchecked" })
	private static void displayFamilies(List<Record> family) {
		String familyFormat = "|%1$-8s|%2$-12s|%3$-12s|%4$-12s|%5$-20s|%6$-9s|%7$-21s|%8$-24s|%n";
		System.out.println("Families");
		System.out.format(
				"+--------+------------+------------+------------+--------------------+---------+---------------------+------------------------+%n");
		System.out.format(
				"|   ID   |  Married   |  Divorced  | Husband ID |    Husband Name    | Wife ID |      Wife Name      |        Children        |%n");
		System.out.format(
				"+--------+----------- +------------+------------+--------------------+---------+---------------------+------------------------+%n");

		for (Record i : family) {
			System.out.format(familyFormat, //
					i.getProperty(PropertyType.id) != null ? i.getProperty(PropertyType.id).getValue() : null, //
					i.getProperty(PropertyType.married) != null ? i.getProperty(PropertyType.married).getValue() : null, //
					i.getProperty(PropertyType.divorced) != null ? i.getProperty(PropertyType.divorced).getValue()
							: null, //
					i.getProperty(PropertyType.husbandID) != null ? i.getProperty(PropertyType.husbandID).getValue()
							: null, //
					i.getProperty(PropertyType.husbandName) != null ? i.getProperty(PropertyType.husbandName).getValue()
							: null, //
					i.getProperty(PropertyType.wifeID) != null ? i.getProperty(PropertyType.wifeID).getValue() : null, //
					i.getProperty(PropertyType.wifeName) != null ? i.getProperty(PropertyType.wifeName).getValue()
							: null, //
					i.getProperty(PropertyType.children) != null
							? String.join(",", (List<String>) i.getProperty(PropertyType.children).getValue())
							: null //
			);
		}
		System.out.format(
				"+--------+----------- +------------+------------+--------------------+---------+---------------------+------------------------+%n");
		System.out.println("");
	}

	@SuppressWarnings("unchecked")
	private static void displayIndividuals(List<Record> indi) {
		String individualFormat = "|%1$-8s|%2$-20s|%3$-8s|%4$-12s|%5$-5s|%6$-7s|%7$-12s|%8$-9s|%9$-9s|%n";
		System.out.println("Individuals");
		System.out.format(
				"+--------+--------------------+--------+------------+-----+-------+------------+---------+---------+%n");
		System.out.format(
				"|   ID   |        Name        | Gender |  Birthday  | Age | Alive |    Death   |  Child  |  Spouse |%n");
		System.out.format(
				"+--------+--------------------+--------+------------+-----+-------+------------+---------+---------+%n");

		for (Record i : indi) {
			System.out.format(individualFormat, //
					i.getProperty(PropertyType.id) != null ? i.getProperty(PropertyType.id).getValue() : null, //
					i.getProperty(PropertyType.name) != null ? i.getProperty(PropertyType.name).getValue() : null, //
					i.getProperty(PropertyType.gender) != null ? i.getProperty(PropertyType.gender).getValue() : null, //
					i.getProperty(PropertyType.birthday) != null ? i.getProperty(PropertyType.birthday).getValue()
							: null, //
					i.getProperty(PropertyType.age) != null ? i.getProperty(PropertyType.age).getValue() : null, //
					i.getProperty(PropertyType.alive) != null ? i.getProperty(PropertyType.alive).getValue() : null, //
					i.getProperty(PropertyType.death) != null ? i.getProperty(PropertyType.death).getValue() : null, //
					i.getProperty(PropertyType.child) != null ? i.getProperty(PropertyType.child).getValue() : null, //
					i.getProperty(PropertyType.spouse) != null
							? String.join(",", (List<String>) i.getProperty(PropertyType.spouse).getValue())
							: null //
			);

		}
		System.out.format(
				"+--------+--------------------+--------+------------+-----+-------+------------+---------+---------+%n");
	}

}
