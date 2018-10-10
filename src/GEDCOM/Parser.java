package GEDCOM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Parser {

	private List<Individual> IndividualList;
	private List<Family> FamilyList;

	public List<Individual> getIndividualList() {
		return IndividualList;
	}

	public void setIndividualList(List<Individual> individualList) {
		IndividualList = individualList;
	}

	public List<Family> getFamilyList() {
		return FamilyList;
	}

	public void setFamilyList(List<Family> familyList) {
		FamilyList = familyList;
	}

	public Parser() {

		File file = new File("resources/InputGEDCOM.ged");
		int lineNumber = 0;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(file));
			String st;
			Set<String> tagNames = init();
			String tagValidity;

			List<Individual> IList = new ArrayList<Individual>();
			Individual individual = new Individual();

			List<Family> FList = new ArrayList<Family>();
			Family family = new Family();

			boolean birth = false;
			boolean death = false;
			boolean marriage = false;
			boolean divorce = false;
			String month[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

			while ((st = br.readLine()) != null) {
				lineNumber++;
				String[] splited = st.split("\\s+", 3);

				if (tagNames.contains(splited[1]))
					tagValidity = "Y";
				else if ((splited[2].equals("FAM")) || (splited[2].equals("INDI"))) {
					String temp;
					temp = splited[2];
					splited[2] = splited[1];
					splited[1] = temp;
					tagValidity = "Y";
				} else
					tagValidity = "N";
				if ((splited[1].equals("DATE")) && (splited[0].equals("1")))
					tagValidity = "N";
				if ((splited[1].equals("NAME")) && (splited[0].equals("2")))
					tagValidity = "N";

				if (tagValidity.equals("Y")) {
					if (splited[1].equals("INDI") || splited[1].equals("FAM") || splited[1].equals("TRLR")) {
						if (individual.getProperty(PropertyType.id) != null && individual.getProperty(PropertyType.id).getValue() != null) {
							IList.add(individual);
							individual = new Individual();
						}
						if (family.getProperty(PropertyType.id) != null && family.getProperty(PropertyType.id).getValue() != null) {
							FList.add(family);
							family = new Family();
						}
						if (splited[1].equals("INDI")) {
							individual.setProperty(PropertyType.id, new Property(splited[2], lineNumber));
						}
						if (splited[1].equals("FAM")) {
							family.setProperty(PropertyType.id, new Property(splited[2], lineNumber));
						}
					}
					if (splited[1].equals("NAME")) {
						individual.setProperty(PropertyType.name, new Property(splited[2], lineNumber));
					}
					if (splited[1].equals("SEX")) {
						individual.setProperty(PropertyType.gender, new Property(splited[2], lineNumber));
					}
					if (splited[1].equals("BIRT"))
						birth = true;
					if (splited[1].equals("DEAT"))
						death = true;
					if (splited[1].equals("DATE") && (birth == true || death == true)) {
						String[] dateSplit = splited[2].split(" ");
						if (birth == true) {
							LocalDate date = LocalDate.of(Integer.parseInt(dateSplit[2]),
									Arrays.asList(month).indexOf(dateSplit[1]) + 1, Integer.parseInt(dateSplit[0]));

							individual.setProperty(PropertyType.birthday, new Property(date, lineNumber));
							birth = false;
						}
						if (death == true) {
							LocalDate date = LocalDate.of(Integer.parseInt(dateSplit[2]),
									Arrays.asList(month).indexOf(dateSplit[1]) + 1, Integer.parseInt(dateSplit[0]));
							individual.setProperty(PropertyType.death, new Property(date, lineNumber));
							death = false;
						}
					}
					if (splited[1].equals("FAMC"))
						individual.setProperty(PropertyType.child, new Property(splited[2], lineNumber));
					if (splited[1].equals("FAMS"))
						individual.setProperty(PropertyType.spouse, new Property(splited[2], lineNumber));

					if (splited[1].equals("MARR"))
						marriage = true;
					if (splited[1].equals("DIV"))
						divorce = true;
					if (splited[1].equals("DATE") && (marriage == true || divorce == true)) {
						String[] dateSplit = splited[2].split(" ");
						if (marriage == true) {
							LocalDate date = LocalDate.of(Integer.parseInt(dateSplit[2]),
									Arrays.asList(month).indexOf(dateSplit[1]) + 1, Integer.parseInt(dateSplit[0]));
							family.setProperty(PropertyType.married, new Property(date, lineNumber));
							marriage = false;
						}
						if (divorce == true) {
							LocalDate date = LocalDate.of(Integer.parseInt(dateSplit[2]),
									Arrays.asList(month).indexOf(dateSplit[1]) + 1, Integer.parseInt(dateSplit[0]));
							family.setProperty(PropertyType.divorced, new Property(date, lineNumber));
							divorce = false;
						}
					}
					if (splited[1].equals("HUSB")) {
						family.setProperty(PropertyType.husbandID, new Property(splited[2], lineNumber));
						Predicate<Individual> byId = p -> p.getProperty(PropertyType.id).getValue().equals(splited[2]);
						List<Individual> res = IList.stream().filter(byId).collect(Collectors.<Individual>toList());
						String husbandName = (String) res.get(0).getProperty(PropertyType.name).getValue();
						family.setProperty(PropertyType.husbandName, new Property(husbandName, lineNumber));
					}

					if (splited[1].equals("WIFE")) {
						family.setProperty(PropertyType.wifeID, new Property(splited[2], lineNumber));
						Predicate<Individual> byId = p -> p.getProperty(PropertyType.id).getValue().equals(splited[2]);
						List<Individual> res = IList.stream().filter(byId).collect(Collectors.<Individual>toList());
						String wifeName = (String) res.get(0).getProperty(PropertyType.name).getValue();
						family.setProperty(PropertyType.wifeName, new Property(wifeName, lineNumber));
					}
					if (splited[1].equals("CHIL")) {
						if (family.getProperty(PropertyType.children) == null) {
							Property children = new Property(new ArrayList<>(), lineNumber);
							family.setProperty(PropertyType.children, children);
						}

						((List<String>) family.getProperty(PropertyType.children).getValue()).add(splited[2]);
					}
				}

			}

			Collections.sort(IList, (i1, i2) -> (((String) i1.getProperty(PropertyType.id).getValue())
					.compareTo((String) i2.getProperty(PropertyType.id).getValue())));
			Collections.sort(FList, (f1, f2) -> (((String) f1.getProperty(PropertyType.id).getValue())
					.compareTo((String) f2.getProperty(PropertyType.id).getValue())));

			setIndividualList(IList);
			setFamilyList(FList);

			String individualFormat = "|%1$-8s|%2$-20s|%3$-8s|%4$-12s|%5$-5s|%6$-7s|%7$-12s|%8$-9s|%9$-9s|%n";
			System.out.println("Individuals");
			System.out.format(
					"+--------+--------------------+--------+------------+-----+-------+------------+---------+---------+%n");
			System.out.format(
					"|   ID   |        Name        | Gender |  Birthday  | Age | Alive |    Death   |  Child  |  Spouse |%n");
			System.out.format(
					"+--------+--------------------+--------+------------+-----+-------+------------+---------+---------+%n");

			for (Individual i : getIndividualList()) {
				System.out.format(individualFormat, //
						i.getProperty(PropertyType.id) != null ? i.getProperty(PropertyType.id).getValue(): null, //
						i.getProperty(PropertyType.name) != null ? i.getProperty(PropertyType.name).getValue(): null, //
						i.getProperty(PropertyType.gender) != null ? i.getProperty(PropertyType.gender).getValue(): null, //
						i.getProperty(PropertyType.birthday) != null ? i.getProperty(PropertyType.birthday).getValue(): null, //
						i.getProperty(PropertyType.age) != null ? i.getProperty(PropertyType.age).getValue(): null, //
						i.getProperty(PropertyType.alive) != null ? i.getProperty(PropertyType.alive).getValue(): null, //
						i.getProperty(PropertyType.death) != null ? i.getProperty(PropertyType.death).getValue(): null, //
						i.getProperty(PropertyType.child) != null ? i.getProperty(PropertyType.child).getValue(): null, //
						i.getProperty(PropertyType.spouse) != null ? i.getProperty(PropertyType.spouse).getValue(): null //														
						);
				
			}
			System.out.format(
					"+--------+--------------------+--------+------------+-----+-------+------------+---------+---------+%n");

			String familyFormat = "|%1$-8s|%2$-12s|%3$-12s|%4$-12s|%5$-20s|%6$-9s|%7$-21s|%8$-14s|%n";
			System.out.println("Families");
			System.out.format(
					"+--------+------------+------------+------------+--------------------+---------+---------------------+--------------+%n");
			System.out.format(
					"|   ID   |  Married   |  Divorced  | Husband ID |    Husband Name    | Wife ID |      Wife Name      |   Children   |%n");
			System.out.format(
					"+--------+----------- +------------+------------+--------------------+---------+---------------------+--------------+%n");

			for (Family i : getFamilyList()) {
				System.out.format(familyFormat, //
						i.getProperty(PropertyType.id) != null ? i.getProperty(PropertyType.id).getValue(): null, //
						i.getProperty(PropertyType.married) != null ? i.getProperty(PropertyType.married).getValue(): null, //
						i.getProperty(PropertyType.divorced) != null ? i.getProperty(PropertyType.divorced).getValue(): null, //
						i.getProperty(PropertyType.husbandID) != null ? i.getProperty(PropertyType.husbandID).getValue(): null, //
						i.getProperty(PropertyType.husbandName) != null ? i.getProperty(PropertyType.husbandName).getValue(): null, //
						i.getProperty(PropertyType.wifeID) != null ? i.getProperty(PropertyType.wifeID).getValue(): null, //
						i.getProperty(PropertyType.wifeName) != null ? i.getProperty(PropertyType.wifeName).getValue(): null, //
						i.getProperty(PropertyType.children) != null ? String.join(",", (List<String>)i.getProperty(PropertyType.children).getValue()): null //
					);
			}
			System.out.format(
					"+--------+----------- +------------+------------+--------------------+---------+---------------------+--------------+%n");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Set<String> init() {
		File file = new File("resources/tags.properties");
		Set<String> tagNames = new HashSet<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			while ((st = br.readLine()) != null) {
				tagNames.add(st);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return tagNames;
	}

}