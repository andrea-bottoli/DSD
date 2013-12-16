package dsd.calculationsTest;
import dsd.calculations.PylonForcesFormulas; 
import static org.junit.Assert.*;

import org.junit.Test;

public class PylonForcesFormulasTest {

	@Test
	public void testForceNcontributionPP() {
	assertEquals(3, PylonForcesFormulas.ForceNcontributionPP(9), 0);
	}
	
	@Test
	public void testForceNcontributionN() {
	assertEquals(1, PylonForcesFormulas.ForceNcontributionN(3), 0);
	}

	@Test
	public void testForceNcontributionTy() {
	assertEquals(6.65, PylonForcesFormulas.ForceNcontributionTy(4, 8, 7.3, 12), 0);
	}
	
	@Test
	public void testForceNcontributionMx() {
	assertEquals(3, PylonForcesFormulas.ForceNcontributionMx(9),0);
	}
	
	@Test
	public void testForceNcontributionQy() {
	assertEquals(20, PylonForcesFormulas.ForceNcontributionQy(5, 2, 4.5, 7),0);
	}
	
	@Test
	public void testForceTxContributionTx() {
	assertEquals(0.9, PylonForcesFormulas.ForceTxContributionTx(2.7),0);
	}
	
	@Test
	public void testForceTyContributionTy() {
	assertEquals(0.5, PylonForcesFormulas.ForceTyContributionTy(1.5),0);
	}

	@Test
	public void testForceTyContributionQy() {
	assertEquals(1, PylonForcesFormulas.ForceTyContributionQy(3),0);
	}
	
	@Test
	public void testForceMxContributionTy() {
	assertEquals(0.625, PylonForcesFormulas.ForceMxContributionTy(1.5, 2.5),0);
	}
	
	@Test
	public void testForceMxContributionQy() {
	assertEquals(4.48, PylonForcesFormulas.ForceMxContributionQy(4.2, 6.4),0);
	}
	
	@Test
	public void testForceMyContributionTx() {
	assertEquals(12.88, PylonForcesFormulas.ForceMyContributionTx(4.6, 8.4),0);
	}
	
}

