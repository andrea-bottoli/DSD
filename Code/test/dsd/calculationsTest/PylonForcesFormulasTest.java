package dsd.calculationsTest;
import dsd.calculations.PylonForcesFormulas; 
import static org.junit.Assert.*;

import org.junit.Test;

public class PylonForcesFormulasTest {

	@Test
	public void testForceNcontributionPP() {
		PylonForcesFormulas a1 = new PylonForcesFormulas();
	assertEquals(3, a1.ForceNcontributionPP(9), 0);
	}
	
	@Test
	public void testForceNcontributionN() {
		PylonForcesFormulas a2 = new PylonForcesFormulas();
	assertEquals(1, a2.ForceNcontributionN(3), 0);
	}

	@Test
	public void testForceNcontributionTy() {
		PylonForcesFormulas a3= new PylonForcesFormulas();
	assertEquals(6.65, a3.ForceNcontributionTy(4f, 8f, 7.3f, 12f), 0);
	}

	
}
