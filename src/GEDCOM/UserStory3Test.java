package GEDCOM;

import static org.junit.Assert.*;
import org.junit.Test;
public class UserStory3Test {

	@Test
	public void birthOccursBeforDeath() {
		AnomaliesParser a= new AnomaliesParser();
		assertEquals(true,a.birthOccursBeforDeath());

	}

}
