package dsd.calculationsTest;
import dsd.calculations.WeightFormulas;
import static org.junit.Assert.*;

import org.junit.Test;

public class WeightFormulasTest {

	@Test
	public void testStackWeight() {
	assertEquals(49.72, WeightFormulas.StackWeight(5.2, 2.4, 1.1, 1.2, 4.4, 1.8),0);
		}

}
