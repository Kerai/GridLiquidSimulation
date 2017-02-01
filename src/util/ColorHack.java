package util;

import java.awt.Color;

/**
 * This is a hack to overcome Java2D limitations. As it was meant for UI drawing, not games.
 * Creating thousands of "escaping" Color objects per frame is not cool.
 */
public class ColorHack extends Color {
	
	int rgba;

	public ColorHack() {
		super(1);
	}

	public void set(int r, int g, int b, int a) {
        rgba = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);
	}
	
	public void set(float r, float g, float b, float a) {
        set((int)(r*255+0.5), (int)(g*255+0.5), (int)(b*255+0.5), (int)(a*255+0.5));
	}
	
    public int getRGB() {
        return rgba;
    }
    
    static final ColorHack color1 = new ColorHack();
    static final ColorHack color2 = new ColorHack();
    static boolean even = false;
    
    public static Color make(float r, float g, float b, float a) {
    	ColorHack c= even ? color1 : color2;
    	even = !even;
    	c.set(r, g, b, a);
    	return c;
    }
    
    
    
    
}
