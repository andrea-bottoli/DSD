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
