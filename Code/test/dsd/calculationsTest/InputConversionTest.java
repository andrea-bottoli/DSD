package dsd.calculationsTest;

import dsd.calculations.InputConversion;
import static org.junit.Assert.*;
import org.junit.Test;

public class InputConversionTest {


	@Test
	public void testwindSpeedConversion() {
		InputConversion p1 = new InputConversion();
	assertEquals(97.5, p1.windSpeedConversion(0.03), 0);
	}

	@Test
	public void testwindDirectionConversion() {
		InputConversion p2 = new InputConversion();
	assertEquals(1035, p2.windDirectionConversion(0.05),0);
	}

	@Test
	public void testwaterLevelConversion() {
		InputConversion p3 = new InputConversion();
	assertEquals(192.36, p3.waterLevelConversion(0.15),0);
	}
	
	@Test
	public void testwaterDistanceConversion() {
		InputConversion p4 = new InputConversion();
	assertEquals(-162.5, p4.waterDistanceConversion(0.15),0);
	}
	
	@Test
	public void testsonarConversion() {
		InputConversion p5 = new InputConversion();
	assertEquals(6.33, p5.sonarConversion(6),0);
	}
}
