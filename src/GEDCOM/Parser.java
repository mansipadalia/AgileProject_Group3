package GEDCOM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			Set<String> tagNames = init();
			String tagValidity;

			List<Individual> IList = new ArrayList<Individual>();
			Individual indi = new Individual();

			List<Family> FList = new ArrayList<Family>();
			Family fam = new Family();

			boolean birth = false;
			boolean death = false;
			boolean marriage = false;
			boolean divorce = false;
			String month[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

			while ((st = br.readLine()) != null) {
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
						if (indi.getId() != null) {
							IList.add(indi);
							indi = new Individual();
						}
						if (fam.getId() != null) {
							FList.add(fam);
							fam = new Family();
						}
						if (splited[1].equals("INDI")) {
							indi.setId(splited[2]);
						}
						if (splited[1].equals("FAM")) {
							fam.setId(splited[2]);
						}
					}
					if (splited[1].equals("NAME")) {
						indi.setName(splited[2]);
					}
					if (splited[1].equals("SEX")) {
						indi.setGender(splited[2]);
					}
					if (splited[1].equals("BIRT"))
						birth = true;
					if (splited[1].equals("DEAT"))
						death = true;
					if (splited[1].equals("DATE") && (birth == true || death == true)) {
						String[] dateSplit = splited[2].split(" ");
						if (birth == true) {
							indi.setBirthday(LocalDate.of(Integer.parseInt(dateSplit[2]),
									Arrays.asList(month).indexOf(dateSplit[1]) + 1, Integer.parseInt(dateSplit[0])));
							birth = false;
						}
						if (death == true) {
							indi.setDeath(LocalDate.of(Integer.parseInt(dateSplit[2]),
									Arrays.asList(month).indexOf(dateSplit[1]) + 1, Integer.parseInt(dateSplit[0])));
							death = false;
						}
					}
					if (splited[1].equals("FAMC"))
						indi.setChild(splited[2]);
					if (splited[1].equals("FAMS"))
						indi.setSpouse(splited[2]);

					if (splited[1].equals("MARR"))
						marriage = true;
					if (splited[1].equals("DIV"))
						divorce = true;
					if (splited[1].equals("DATE") && (marriage == true || divorce == true)) {
						String[] dateSplit = splited[2].split(" ");
						if (marriage == true) {
							fam.setMarried(LocalDate.of(Integer.parseInt(dateSplit[2]),
									Arrays.asList(month).indexOf(dateSplit[1]) + 1, Integer.parseInt(dateSplit[0])));
							marriage = false;
						}
						if (divorce == true) {
							fam.setDivorced(LocalDate.of(Integer.parseInt(dateSplit[2]),
									Arrays.asList(month).indexOf(dateSplit[1]) + 1, Integer.parseInt(dateSplit[0])));
							divorce = false;
						}
					}
					if (splited[1].equals("HUSB")) {
						fam.setHusbandID(splited[2]);
						Predicate<Individual> byId = p -> p.getId().equals(splited[2]);
				        List<Individual> res = IList.stream().filter(byId).collect(Collectors.<Individual> toList());
						fam.setHusbandName(res.get(0).getName());
					}
					
					if (splited[1].equals("WIFE")) {
						fam.setWifeId(splited[2]);
						Predicate<Individual> byId = p -> p.getId().equals(splited[2]);
				        List<Individual> res = IList.stream().filter(byId).collect(Collectors.<Individual> toList());
						fam.setWifeName(res.get(0).getName());
					}
					if(splited[1].equals("CHIL")) {
						fam.getChildren().add(splited[2]);
					}

				}
			}

			Collections.sort(IList, (i1, i2) -> (i1.getId().compareTo(i2.getId())));
			Collections.sort(FList, (f1, f2) -> (f1.getId().compareTo(f2.getId())));
			
			setIndividualList(IList);
			setFamilyList(FList);
			
			/*System.out.println("------------ Individual List ------------");
			for (Individual i : getIndividualList()) {
				System.out.println(i.getId());
				System.out.println(i.getName());
				System.out.println(i.getGender());
				System.out.println(i.getBirthday());
				System.out.println(i.getAge());
				System.out.println(i.isAlive());
				System.out.println(i.getDeath());
				System.out.println(i.getChild());
				System.out.println(i.getSpouse());
			}
			System.out.println("------------ Family List ------------");
			for (Family j : getFamilyList()) {
				System.out.println(j.getId());
				System.out.println(j.getMarried());
				System.out.println(j.getDivorced());
				System.out.println(j.getHusbandID());
				System.out.println(j.getHusbandName());
				System.out.println(j.getWifeId());
				System.out.println(j.getWifeName());
				System.out.println(String.join(",", j.getChildren()));
			}*/

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