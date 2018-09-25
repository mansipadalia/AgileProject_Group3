package GEDCOM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	public Parser() 
	{
	File file = new File("resources/InputGEDCOM.ged");
	try {
		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		Set<String> tagNames = init();
		int level;
		String tag;
		String arguments;
		String tagValidity;
		
		while ((st = br.readLine()) != null)
		{
			String[] splited = st.split("\\s+",3);
			if(tagNames.contains(splited[1]))
			{
				tagValidity="Y";
			}
			else if ((splited[2].equals("FAM")) || (splited[2].equals("INDI")))
			{
				String temp;
				temp = splited[2];
				splited[2] = splited[1];
				splited[1] = temp;
				tagValidity="Y";
			}
			else
				tagValidity="N";
			if ((splited[1].equals("DATE")) && (splited[0].equals("1")))
			{
				tagValidity="N";
			}
			if ((splited[1].equals("NAME")) && (splited[0].equals("2")))
			{
				tagValidity="N";
			}
			//To be replaced with a collection of individual and family class objects using set methods
			System.out.println("<-- "+splited[0]+"|"+splited[1]+"|"+tagValidity+"|"+(splited.length>2?splited[2]:""));
		}
	} catch (Exception e) {
		System.out.println(e);
	}
}

	public Set<String> init()
	{
		File file = new File("resources/tags.properties");
		Set<String> tagNames = new HashSet<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
	
			String st;
			while ((st = br.readLine()) != null)
			{
				tagNames.add(st);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return tagNames;
	}

	
}