package org.bulldog.core.gpio.base;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.Pwm;

public abstract class AbstractPwm extends AbstractPinFeature implements Pwm {

	private String NAME_FORMAT = "PWM - status '%s' - frequency '%.2f' with duty '%.2f' on Pin %s";

	private float duty = 0.0f;
	private float frequency = 1.0f;
	private boolean enabled = false;
	
	public AbstractPwm(Pin pin) {
		super(pin);
	}

	public String getName() {
		return String.format(NAME_FORMAT, isEnabled() ? "enabled" : "disabled", getFrequency(), getDuty(), getPin().getName());
	}
	
	public void enable() {
		enableImpl();
		enabled = true;
	}
	
	public void disable() {
		disableImpl();
		enabled = false;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setDuty(float duty) {
		if(duty < 0.0 || duty > 1.0) {
			throw new IllegalArgumentException("Duty cannot be less than 0.0 or greater 1.0");
		}
		this.duty = duty;
		setPwmImpl(getFrequency(), duty);
	}
	
	public float getDuty() {
		return duty;
	}
	
	public void setFrequency(float frequency) {
		if(frequency < 1.0f) {
			throw new IllegalArgumentException("Frequency cannot be less than 1.0 Hz");
		}
		this.frequency = frequency;
		setPwmImpl(frequency, getDuty());
	}
	
	public float getFrequency() {
		return frequency;
	}
	
	protected abstract void setPwmImpl(float frequency, float duty);
	protected abstract void enableImpl();
	protected abstract void disableImpl();

}
