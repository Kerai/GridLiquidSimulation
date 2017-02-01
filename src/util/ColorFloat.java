package util;

import java.awt.Color;

public class ColorFloat {
	public float r;
	public float g;
	public float b;
	
	public ColorFloat(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public ColorFloat() {
		
	}

	public void set(ColorFloat other) {
		this.r = other.r;
		this.g = other.g;
		this.b = other.b;
	}
	public void set(Color clr) {
		this.r = clr.getRed() / 255f;
		this.g = clr.getGreen() / 255f;
		this.b = clr.getBlue() / 255f;
	}
	
	public static ColorFloat lerp(ColorFloat a, ColorFloat b, float f, ColorFloat c) {
		c.r = lerp(a.r, b.r, f);
		c.g = lerp(a.g, b.g, f);
		c.b = lerp(a.b, b.b, f);
		return c;
	}

	public void setLerp(ColorFloat a, ColorFloat b, float f) {
	   lerp(a, b, f>1? 1 : f, this);
	}
	
	static float lerp(float a, float b, float f) {
	    return a + f * (b - a);
	}

	public Color toColor() {
		return ColorHack.make(r, g, b, 1f);
	}

}
