package GEDCOM;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnomaliesParser {

	public static void main(String[] args) {
		Parser p = new Parser();

		/*
		 * String individualFormat =
		 * "|%1$-8s|%2$-20s|%3$-8s|%4$-12s|%5$-5s|%6$-7s|%7$-12s|%8$-9s|%9$-9s|%n";
		 * System.out.println("Individuals"); System.out.format(
		 * "+--------+--------------------+--------+------------+-----+-------+------------+---------+---------+%n"
		 * ); System.out.format(
		 * "|   ID   |        Name        | Gender |  Birthday  | Age | Alive |    Death   |  Child  |  Spouse |%n"
		 * ); System.out.format(
		 * "+--------+--------------------+--------+------------+-----+-------+------------+---------+---------+%n"
		 * );
		 * 
		 * for (Individual i : p.getIndividualList()) {
		 * System.out.format(individualFormat, // i.getProperty(PropertyType.id) != null
		 * ? i.getProperty(PropertyType.id).getValue(): null, //
		 * i.getProperty(PropertyType.name) != null ?
		 * i.getProperty(PropertyType.name).getValue(): null, //
		 * i.getProperty(PropertyType.gender) != null ?
		 * i.getProperty(PropertyType.gender).getValue(): null, //
		 * i.getProperty(PropertyType.birthday) != null ?
		 * i.getProperty(PropertyType.birthday).getValue(): null, //
		 * i.getProperty(PropertyType.age) != null ?
		 * i.getProperty(PropertyType.age).getValue(): null, //
		 * i.getProperty(PropertyType.alive) != null ?
		 * i.getProperty(PropertyType.alive).getValue(): null, //
		 * i.getProperty(PropertyType.death) != null ?
		 * i.getProperty(PropertyType.death).getValue(): null, //
		 * i.getProperty(PropertyType.child) != null ?
		 * i.getProperty(PropertyType.child).getValue(): null, //
		 * i.getProperty(PropertyType.spouse) != null ?
		 * i.getProperty(PropertyType.spouse).getValue(): null // );
		 * 
		 * } System.out.format(
		 * "+--------+--------------------+--------+------------+-----+-------+------------+---------+---------+%n"
		 * );
		 * 
		 * String familyFormat =
		 * "|%1$-8s|%2$-12s|%3$-12s|%4$-12s|%5$-20s|%6$-9s|%7$-21s|%8$-14s|%n";
		 * System.out.println("Families"); System.out.format(
		 * "+--------+------------+------------+------------+--------------------+---------+---------------------+--------------+%n"
		 * ); System.out.format(
		 * "|   ID   |  Married   |  Divorced  | Husband ID |    Husband Name    | Wife ID |      Wife Name      |   Children   |%n"
		 * ); System.out.format(
		 * "+--------+----------- +------------+------------+--------------------+---------+---------------------+--------------+%n"
		 * );
		 * 
		 * for (Family i : p.getFamilyList()) { System.out.format(familyFormat, //
		 * i.getProperty(PropertyType.id) != null ?
		 * i.getProperty(PropertyType.id).getValue(): null, //
		 * i.getProperty(PropertyType.married) != null ?
		 * i.getProperty(PropertyType.married).getValue(): null, //
		 * i.getProperty(PropertyType.divorced) != null ?
		 * i.getProperty(PropertyType.divorced).getValue(): null, //
		 * i.getProperty(PropertyType.husbandID) != null ?
		 * i.getProperty(PropertyType.husbandID).getValue(): null, //
		 * i.getProperty(PropertyType.husbandName) != null ?
		 * i.getProperty(PropertyType.husbandName).getValue(): null, //
		 * i.getProperty(PropertyType.wifeID) != null ?
		 * i.getProperty(PropertyType.wifeID).getValue(): null, //
		 * i.getProperty(PropertyType.wifeName) != null ?
		 * i.getProperty(PropertyType.wifeName).getValue(): null, //
		 * i.getProperty(PropertyType.children) != null ? String.join(",",
		 * (List<String>)i.getProperty(PropertyType.children).getValue()): null // ); }
		 * System.out.format(
		 * "+--------+----------- +------------+------------+--------------------+---------+---------------------+--------------+%n"
		 * );
		 */

		List<String> errors = new ArrayList<String>();
		errors.addAll(UserStoriesDates.birthBeforeMarriage(p));
		for (String i : errors) {
			System.out.println(i);
		}
	}

	// User Story 03
	public static boolean birthOccursBeforeDeath() {
//					Parser p = new Parser();
//					for (Individual j :p.getIndividualList()) {
//						if(j.getDeath()!= null) {
//							if((j.getBirthday()).compareTo(j.getDeath())>0) {
//								System.out.println("Error: " + "INDIVIDUAL: " + "US03: " +  j.getLineNumber() + ": " + j.getId() + ": " + "Died " + j.getDeath() + " before born " + j.getBirthday());
//							}			
//						}
//					}
		return true;
	}

}
