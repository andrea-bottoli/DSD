package dsd.calculationsTest;

import dsd.calculations.InputConversion;
import static org.junit.Assert.*;
import org.junit.Test;

public class InputConversionTest {


	@Test
	public void testwindSpeedConversion() {
	assertEquals(97.5, InputConversion.windSpeedConversion(0.03), 0);
	}

	@Test
	public void testwindDirectionConversion() {
	assertEquals(1035, InputConversion.windDirectionConversion(0.05),0);
	}

	@Test
	public void testwaterLevelConversion() {
	assertEquals(192.36, InputConversion.waterLevelConversion(0.15),0);
	}
	
	@Test
	public void testwaterDistanceConversion() {
	assertEquals(-162.5, InputConversion.waterDistanceConversion(0.15),0);
	}
	
	@Test
	public void testsonarConversion() {
	assertEquals(6.33, InputConversion.sonarConversion(6),0);
	}
}
