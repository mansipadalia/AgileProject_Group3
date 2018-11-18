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

		/* ------------------- SPRINT 1 -------------------- */
		errors.addAll(US_GeneralDates.datesBeforeCurrentDate(p));
		errors.addAll(US_MarriageDivorceDates.birthBeforeMarriage(p));
		errors.addAll(US_BirthDeathDates.birthBeforeDeath(p));
		errors.addAll(US_MarriageDivorceDates.marriageBeforeDivorce(p));
		errors.addAll(US_MarriageDivorceDates.marriageBeforeDeath(p));
		errors.addAll(US_Age.lessThanOneFiftyAge(p));

		/* ------------------- SPRINT 2 -------------------- */
		errors.addAll(US_Parent.birthBeforeMarriageOfParents(p));
		errors.addAll(US_Parent.birthBeforeDeathOfParents(p));
		errors.addAll(US_MarriageDivorceDates.marriageAfter14(p));
		errors.addAll(US_Parent.parentsNotTooOld(p));
		errors.addAll(US_Sibling.siblingSpacing(p));
		errors.addAll(US_Sibling.fiveSiblingSpacing(p));
		errors.addAll(US_Uniqueness.uniqueNameBirthDate(p));
		errors.addAll(US_Uniqueness.uniqueFirstNameBirthDate(p));

		/* ------------------- SPRINT 3 -------------------- */
		errors.addAll(US_MarriageDivorceDates.divorceBeforeDeath(p));
		errors.addAll(US_Sibling.fewerThanFifteenSiblings(p));
		errors.addAll(US_Gender.maleLastNames(p));

		/* ------------------- SPRINT 4 -------------------- */
		errors.addAll(US_Gender.correctGenderForRole(p));
		errors.addAll(US_Consistency.correspondingEntries(p));
		errors.addAll(US_Uniqueness.uniqueIds(p));

		for (Error i : errors) {
			System.out.println(i.toString());
		}
		System.out.println("");

		// Display List User Stories
		List<Record> List_US28 = US_List.orderSiblingsByAge(p);
		System.out.println("US28 : List of Siblings By Age");
		displaySiblingsByage(List_US28);

		List<Record> List_US29 = US_List.deceased(p);
		System.out.println("US29 : List of Deceased Individuals");
		displayIndividuals(List_US29);

		List<Record> List_US30 = US_List.livingMarried(p);
		System.out.println("US30 : List of Living Married Individuals");
		displayLivingMarried(List_US30);

		List<Record> List_US33 = US_List.orphans(p);
		System.out.println("US33 : List of all orphans");
		displayOrphans(List_US33);

		List<Record> List_US31 = US_List.livingSingle(p);
		System.out.println("US31 : List of Living Single");
		displayIndividuals(List_US31);

		List<Record> List_US34 = US_List.largeAgeDifferences(p);
		System.out.println("US34 : List of Large Age Differences");
		displaylargeAgeDifferences(List_US34);

		List<Record> List_US35 = US_List.listRecentBirths(p);
		System.out.println("US35 : List of Recent Births");
		displayIndividuals(List_US35);

		List<Record> List_US36 = US_List.listRecentDeaths(p);
		System.out.println("US36 : List of Recent Deaths");
		displayIndividuals(List_US36);

		List<Record> List_US38 = US_List.upcomingBirthdays(p);
		System.out.println("US38 : List of Upcoming Birthdays");
		displayIndividuals(List_US38);

		List<Record> List_US39 = US_List.upcomingAnniversaries(p);
		System.out.println("US39 : List of Upcoming Anniversaries");
		displayFamilies(List_US39);

	}

	@SuppressWarnings({ "unchecked" })
	private static void displayFamilies(List<Record> family) {
		String familyFormat = "|%1$-8s|%2$-12s|%3$-12s|%4$-12s|%5$-20s|%6$-9s|%7$-21s|%8$-68s|%n";
		System.out.println("Families");
		System.out.format(
				"+--------+------------+------------+------------+--------------------+---------+---------------------+--------------------------------------------------------------------+%n");
		System.out.format(
				"|   ID   |  Married   |  Divorced  | Husband ID |    Husband Name    | Wife ID |      Wife Name      |                              Children                              |%n");
		System.out.format(
				"+--------+------------+------------+------------+--------------------+---------+---------------------+--------------------------------------------------------------------+%n");

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
				"+--------+------------+------------+------------+--------------------+---------+---------------------+--------------------------------------------------------------------+%n");
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
		System.out.println("");
	}

	private static void displayLivingMarried(List<Record> records) {
		String familyFormat = "|%1$-8s|%2$-20s|%3$-12s|%4$-12s|%5$-12s|%6$-12s|%n";
		System.out.println("Families");
		System.out.format("+--------+--------------------+------------+------------+------------+------------+%n");
		System.out.format("|   ID   |        Name        |  Birthday  |    Death   |  Married   |  Divorced  |%n");
		System.out.format("+--------+--------------------+------------+------------+------------+------------+%n");

		for (Record i : records) {
			System.out.format(familyFormat, //
					i.getProperty(PropertyType.id) != null ? i.getProperty(PropertyType.id).getValue() : null, //
					i.getProperty(PropertyType.name) != null ? i.getProperty(PropertyType.name).getValue() : null, //
					i.getProperty(PropertyType.birthday) != null ? i.getProperty(PropertyType.birthday).getValue()
							: null, //
					i.getProperty(PropertyType.death) != null ? i.getProperty(PropertyType.death).getValue() : null, //
					i.getProperty(PropertyType.married) != null ? i.getProperty(PropertyType.married).getValue() : null, //
					i.getProperty(PropertyType.divorced) != null ? i.getProperty(PropertyType.divorced).getValue()
							: null //
			);
		}
		System.out.format("+--------+--------------------+------------+------------+------------+------------+%n");
		System.out.println("");
	}

	private static void displaySiblingsByage(List<Record> records) {
		String familyFormat = "|%1$-8s|%2$-8s|%3$-20s|%4$-12s|%5$-12s|%6$-12s|%n";
		System.out.println("Families");
		System.out.format("+--------+--------+--------------------+------------+------------+------------+%n");
		System.out.format("|   FID  |   ID   |      Name          |  Gender    |  Birthday  |  Age       |%n");
		System.out.format("+--------+--------+--------------------+------------+------------+------------+%n");

		for (Record i : records) {
			System.out.format(familyFormat, //
					i.getProperty(PropertyType.child) != null ? i.getProperty(PropertyType.child).getValue() : null, //
					i.getProperty(PropertyType.id) != null ? i.getProperty(PropertyType.id).getValue() : null, //

					i.getProperty(PropertyType.name) != null ? i.getProperty(PropertyType.name).getValue() : null, //
					i.getProperty(PropertyType.gender) != null ? i.getProperty(PropertyType.gender).getValue() : null, //
					i.getProperty(PropertyType.birthday) != null ? i.getProperty(PropertyType.birthday).getValue()
							: null, //
					i.getProperty(PropertyType.age) != null ? i.getProperty(PropertyType.age).getValue() : null //

			);
		}
		System.out.format("+--------+--------+--------------------+------------+------------+------------+%n");
		System.out.println("");
	}

	private static void displayOrphans(List<Record> records) {
		String familyFormat = "|%1$-8s|%2$-8s|%3$-20s|%4$-12s|%n";
		System.out.println("Families");
		System.out.format("+--------+--------+--------------------+------------+%n");
		System.out.format("|   FID  |   ID   |      Name          |  Age       |%n");
		System.out.format("+--------+--------+--------------------+------------+%n");

		for (Record i : records) {
			System.out.format(familyFormat, //
					i.getProperty(PropertyType.child) != null ? i.getProperty(PropertyType.child).getValue() : null, //
					i.getProperty(PropertyType.id) != null ? i.getProperty(PropertyType.id).getValue() : null, //
					i.getProperty(PropertyType.name) != null ? i.getProperty(PropertyType.name).getValue() : null, //
					i.getProperty(PropertyType.age) != null ? i.getProperty(PropertyType.age).getValue() : null //
			);
		}
		System.out.format("+--------+--------+--------------------+------------+%n");

		System.out.println("");
	}

	private static void displaylargeAgeDifferences(List<Record> family) {
		String familyFormat = "|%1$-8s|%2$-12s|%3$-12s|%4$-19s|%5$-9s|%6$-21s|%7$-15s|%8$-12s|%n";
		System.out.println("Families");
		System.out.format(
				"+--------+------------+------------+-------------------+---------+---------------------+---------------+------------+%n");
		System.out.format(
				"|   ID   |  Married   | Husband ID |    Husband Name   | Wife ID |      Wife Name      | Husband's Age | Wife's Age |%n");
		System.out.format(
				"+--------+------------+------------+-------------------+---------+---------------------+---------------+------------+%n");

		for (Record i : family) {
			System.out.format(familyFormat, //
					i.getProperty(PropertyType.id) != null ? i.getProperty(PropertyType.id).getValue() : null, //
					i.getProperty(PropertyType.married) != null ? i.getProperty(PropertyType.married).getValue() : null, //
					i.getProperty(PropertyType.husbandID) != null ? i.getProperty(PropertyType.husbandID).getValue()
							: null, //
					i.getProperty(PropertyType.husbandName) != null ? i.getProperty(PropertyType.husbandName).getValue()
							: null, //
					i.getProperty(PropertyType.wifeID) != null ? i.getProperty(PropertyType.wifeID).getValue() : null, //
					i.getProperty(PropertyType.wifeName) != null ? i.getProperty(PropertyType.wifeName).getValue()
							: null, //
					i.getProperty(PropertyType.divorced) != null ? i.getProperty(PropertyType.divorced).getValue()
							: null, //
					i.getProperty(PropertyType.children) != null ? i.getProperty(PropertyType.children).getValue()
							: null //
			);
		}
		System.out.format(
				"+--------+------------+------------+-------------------+---------+---------------------+---------------+------------+%n");

		System.out.println("");
	}
}
