package GEDCOM;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class AnomaliesParser {
	
		public static void main(String[] args) {
			Parser p = new Parser();
       // User Story 03
				for (Individual j :p.getIndividualList()) {
<<<<<<< HEAD
					if(j.getDeath()!= null) {
						if((j.getBirthday()).compareTo(j.getDeath())>0) {
							System.out.println("Error: " + "INDIVIDUAL: " + "US03: " +  j.getLineNumber() + ": " + j.getId() + ": " + "Died " + j.getDeath() + " before born " + j.getBirthday());
						}			
					}
				}
	    	
=======
					if(j.getBirthday().compareTo(j.getDeath())<0) {
						System.out.println("Error: " + "INDIVIDUAL: " + "US03: " +  j.getLineNumber() + ": " + j.getId() + ": " + "Died " + j.getDeath() + " before born " + j.getBirthday());
					}		
>>>>>>> 01b4ff7935da677db76807aaa843f2c000bbaf1c
		}	
}
