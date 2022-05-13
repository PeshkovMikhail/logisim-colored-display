package com.MPeshkov.LCD;

import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.gui.generic.GridPainter;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.util.GraphicsUtil;
import com.cburch.logisim.util.StringGetter;
import com.cburch.logisim.util.StringUtil;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.data.Attributes;
import java.awt.*;
import java.util.IllegalFormatWidthException;

public class LCD extends InstanceFactory {
    private static final BitWidth BIT_WIDTH = BitWidth.create(32);
    private static final int LCD_SIZE = 320;
    private static final int LCD_GAP = LCD_SIZE/32;
    private static final int WIRE_GAP = 10;
    private static int states[] = new int[64];
    private static Attribute  attrs[] = {Attributes.forColor("color 1"), Attributes.forColor("color 2"), Attributes.forColor("color 3"), Attributes.forColor("color 4")};
    public LCD() {
        super("32x32 4 bit LCD");
        setAttributes(attrs,
                new Object[] {new Color(255, 255, 255), new Color(0, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)});
        setOffsetBounds(Bounds.create(0, 0, LCD_SIZE+10, LCD_SIZE));
        Port[] ports = new Port[64];
        for(int i =0; i < 32; i++) {
            ports[i] = new Port(0, i*WIRE_GAP, Port.INPUT, 32);
        }
        for(int i = 32; i < 64; i++) {
            ports[i] = new Port(LCD_SIZE+10, (i-32)*WIRE_GAP, Port.INPUT, 32);
        }
        setPorts(ports);
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        painter.drawBounds();
        painter.drawPorts();

        if(painter.getShowState()) {
            Bounds bds = painter.getBounds();
            Graphics g = painter.getGraphics();
            for(int i = 0; i < 32; i++) {
                int first_bits = states[i];
                int seconds_bits = states[i+32];
                for(int k = 0; k < 32; k++) {
                    int pos = 1<<k;
                    int f = 0;
                    if((first_bits&pos) != 0)
                        f = 1;
                    int s = 0;
                    if((seconds_bits&pos) != 0)
                        s = 1;
                    Color colors[][] = {{(Color) painter.getAttributeValue(attrs[0]), (Color) painter.getAttributeValue(attrs[1])},
                            {(Color) painter.getAttributeValue(attrs[2]), (Color) painter.getAttributeValue(attrs[3])}};
                    g.setColor(colors[f][s]);
                    g.fillRect(bds.getX()+k*LCD_GAP+5, bds.getY()+i*LCD_GAP, LCD_GAP,LCD_GAP);
                }
            }
        }
    }

    @Override
    public void propagate(InstanceState state) {
        for(int i = 0; i < 64; i++) {
            states[i] = state.getPort(i).toIntValue();
        }
    }
}
