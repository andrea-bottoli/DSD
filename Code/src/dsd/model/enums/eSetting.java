package dsd.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum eSetting
{
	TestSetting("TestSetting"); 
	
	private String name;
	 
    /**
     * A mapping between the string name and its corresponding Setting to facilitate lookup by code.
     */
    private static Map<String, eSetting> NameToSettingrMapping;
 
    private eSetting(String name) {
        this.name = name;
    }
 
    public static eSetting geteParameter(String name) {
        if (NameToSettingrMapping == null) {
            initMapping();
        }
        return NameToSettingrMapping.get(name);
    }
 
    private static void initMapping() {
    	NameToSettingrMapping = new HashMap<String, eSetting>();
        for (eSetting s : values()) {
        	NameToSettingrMapping.put(s.name, s);
        }
    }
 
    public String getName() {
        return name;
    }
}
