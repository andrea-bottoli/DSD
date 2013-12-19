/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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

