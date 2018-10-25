package GEDCOM;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Test_MarriageDivorceDates.class, Test_BirthDeathDates.class, Test_GeneralDates.class, Test_Age.class,
		Test_Sibling.class, Test_Miscellaneous.class, Test_Uniqueness.class, Test_Parent.class })
public class AllTests {

}
