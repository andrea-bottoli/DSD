/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brčić, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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
import dsd.calculations.WindPushFormulas;
import static org.junit.Assert.*;

import org.junit.Test;

public class WindPushFormulasTest {

	@Test
	public void testEffectiveWindSpeed() {
    assertEquals(0, WindPushFormulas.EffectiveWindSpeed(4, 0, 0),0);
	}

	@Test
	public void testWindPushOnPlank() {
    assertEquals(10.9368, WindPushFormulas.WindPushOnPlank(0.4, 6.2, 4.5, 1.4),0);
	}
	
	@Test
	public void testWindPushOnA1TrafficCombination() {
    assertEquals(877.5, WindPushFormulas.WindPushOnA1TrafficCombination(2, 3, 6.5, 5, 3),0);
	}
	
	@Test
	public void testwindPushOnA2TrafficCombination() {
    assertEquals(480, WindPushFormulas.WindPushOnA2TrafficCombination(4, 5, 1.5, 2, 4),0);
	}
	
	@Test
	public void testwindPushOnA3TrafficCombination() {
    assertEquals(720, WindPushFormulas.WindPushOnA3TrafficCombination(3, 12, 4, 2.5, 2),0);
	}
	
}
