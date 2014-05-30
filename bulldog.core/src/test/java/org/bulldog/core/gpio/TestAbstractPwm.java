package org.bulldog.core.gpio;

import junit.framework.TestCase;

import org.bulldog.core.mocks.MockedPwm;
import org.junit.Before;
import org.junit.Test;

public class TestAbstractPwm {

	private Pin pin;
	
	@Before
	public void setup() {
		pin = new Pin("Testpin", 0, "A", 0);
		MockedPwm pwm = new MockedPwm(pin);
		pin.addFeature(pwm);
	}
	
	@Test
	public void testPwm() {
		MockedPwm pwm = pin.as(MockedPwm.class);
		
		TestCase.assertFalse(pwm.isBlocking());
	}
	
	@Test
	public void testFrequency() {
		MockedPwm pwm = pin.as(MockedPwm.class);
		
		try {
			pwm.setFrequency(0.0f);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		try {
			pwm.setFrequency(-1.0f);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		try {
			pwm.setFrequency(0.99f);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		TestCase.assertFalse(pwm.pwmImplCalled());
		
		pwm.setFrequency(1.00f);
		TestCase.assertEquals(1.00f, pwm.getFrequency());
		TestCase.assertTrue(pwm.pwmImplCalled());
		pwm.reset();
		TestCase.assertFalse(pwm.pwmImplCalled());
		
		pwm.setFrequency(1000.0f);
		TestCase.assertEquals(1000.0f, pwm.getFrequency());
		TestCase.assertTrue(pwm.pwmImplCalled());
	}
	
	@Test
	public void testDuty() {
		MockedPwm pwm = pin.as(MockedPwm.class);
		
		try {
			pwm.setDuty(-1.0f);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		try {
			pwm.setDuty(2.0f);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		try {
			pwm.setDuty(1.00001f);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		TestCase.assertFalse(pwm.pwmImplCalled());
		
		pwm.setDuty(1.00f);
		TestCase.assertEquals(1.00f, pwm.getDuty());
		TestCase.assertTrue(pwm.pwmImplCalled());
		pwm.reset();
		TestCase.assertFalse(pwm.pwmImplCalled());
		
		pwm.setDuty(0.00001f);
		TestCase.assertEquals(0.00001f, pwm.getDuty());
		TestCase.assertTrue(pwm.pwmImplCalled());
		pwm.reset();
		TestCase.assertFalse(pwm.pwmImplCalled());
		
		pwm.setDuty(0.999999999999999f);
		TestCase.assertEquals(0.999999999999999f, pwm.getDuty());
		TestCase.assertTrue(pwm.pwmImplCalled());
	}
	
	@Test
	public void testEnable() {
		MockedPwm pwm = pin.as(MockedPwm.class);
		TestCase.assertFalse(pwm.enableImplCalled());
		TestCase.assertFalse(pwm.disableImplCalled());
		pwm.enable();
		TestCase.assertTrue(pwm.isEnabled());
		TestCase.assertTrue(pwm.enableImplCalled());
		TestCase.assertFalse(pwm.disableImplCalled());
		pwm.disable();
		TestCase.assertFalse(pwm.isEnabled());
		TestCase.assertTrue(pwm.disableImplCalled());
	}
	
	@Test
	public void testName() {
		MockedPwm pwm = pin.as(MockedPwm.class);
		String name = pwm.getName();
		TestCase.assertNotNull(name);
		
		pwm.enable();
		name = pwm.getName();
		TestCase.assertNotNull(name);
	}
	
}
