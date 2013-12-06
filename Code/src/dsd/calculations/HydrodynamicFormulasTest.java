package dsd.calculations;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HydrodynamicFormulasTest {


	@Test
	public void testFlowRate() { // 0<hwater<hmaxwater
     HydrodynamicFormulas t1 = new HydrodynamicFormulas();
      assertEquals(1478, t1.FlowRate(46, 15, -902, 4658),0);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Test
	public void testFlowRate1(){ //hwater>hmaxwater
	HydrodynamicFormulas t2 = new HydrodynamicFormulas();
	t2.FlowRate(46,26,-902, 4658);
	 exception.expectMessage("River Overflow");
	}

	@Rule
	public ExpectedException exception1 = ExpectedException.none();
	@Test
	public void testFlowRate2() { //hwater<0
     HydrodynamicFormulas t1 = new HydrodynamicFormulas();
      t1.FlowRate(46, -1, -902, 4658);
      exception1.expectMessage("Value of hwater not allowed");
	}
	
	@Test
	public void testWaterSpeed() { // 0<hwater<hmaxwater
     HydrodynamicFormulas t1 = new HydrodynamicFormulas();
      assertEquals(10968, t1.WaterSpeed(5, 12, 14, 26),0);
	}
	
	@Rule
	public ExpectedException exception2 = ExpectedException.none();
	@Test
	public void testWaterSpeed1() { // hwater>hmaxwater
     HydrodynamicFormulas t2 = new HydrodynamicFormulas();
     t2.WaterSpeed(5, 26, 15, 12);
     exception2.expectMessage("River Overflow");
	}
	
	@Rule
	public ExpectedException exception3 = ExpectedException.none();
	@Test
	public void testWaterSpeed2() { // hwater<0
     HydrodynamicFormulas t2 = new HydrodynamicFormulas();
     t2.WaterSpeed(5, 26, 15, 12);
     exception3.expectMessage("Value of hwater not allowed");
	}
	
	@Test
	public void testStackArea() { 
     HydrodynamicFormulas t1 = new HydrodynamicFormulas();
      assertEquals(30, t1.StackArea(5, 6),0);
	}
	
	@Test
	public void testHydrodynamicThrust() { 
     HydrodynamicFormulas t1 = new HydrodynamicFormulas();
      assertEquals(1575, t1.HysrodynamicThrust(12, 20, 14, 15, 0.25),0);
      }
}
