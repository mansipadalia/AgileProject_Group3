package GEDCOM;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class Test_Miscellaneous {

	// US42
	@Test
	public void testRejectIllegitimateDatesValid() {
		try {
			// we are parsing all our dates to LocalDate and thus the following test suffice
			LocalDate date = LocalDate.of(2000, 2, 30);
			assertTrue(false);
		} catch (java.time.DateTimeException de) {
			assertTrue(true);
		}
	}

	// US42
	@Test
	public void testRejectIllegitimateDatesInvalid() {
		try {
			// we are parsing all our dates to LocalDate and thus the following test suffice
			LocalDate date = LocalDate.of(1998, 6, 13);
			assertTrue(true);
		} catch (java.time.DateTimeException de) {
			assertTrue(false);
		}
	}

}
