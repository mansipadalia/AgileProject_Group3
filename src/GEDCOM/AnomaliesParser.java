package GEDCOM;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class AnomaliesParser {
	
		public static void main(String[] args) {
			Parser p = new Parser();
       // User Story 03
				for (Individual j :p.getIndividualList()) {
					if(j.getBirthday().compareTo(j.getDeath())<0) {
						System.out.println("Error: " + "INDIVIDUAL: " + "US03: " +  j.getLineNumber() + ": " + j.getId() + ": " + "Died " + j.getDeath() + " before born " + j.getBirthday());
					}		
		}	
}
