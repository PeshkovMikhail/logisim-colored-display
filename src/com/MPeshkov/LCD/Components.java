package com.MPeshkov.LCD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;

/** The library of components that the user can access. */
public class Components extends Library {
    /** The list of all tools contained in this library. Technically,
     * libraries contain tools, which is a slightly more general concept
     * than components; practically speaking, though, you'll most often want
     * to create AddTools for new components that can be added into the circuit.
     */
    private List<Tool> tools;

    /** Constructs an instance of this library. This constructor is how
     * Logisim accesses first when it opens the JAR file: It looks for
     * a no-arguments constructor method of the user-designated class.
     */
    public Components() {
        try {
            tools = new ArrayList<Tool>();
            tools.add(new AddTool(new LCD()));
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /** Returns the name of the library that the user will see. */
    public String getDisplayName() {
        return "Custom display";
    }

    /** Returns a list of all the tools available in this library. */
    public List<Tool> getTools() {
        return tools;
    }
}