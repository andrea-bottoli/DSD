package dsd.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum eParameter
{
	Test("test");
	
	private String name;
	 
    /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<String, eParameter> codeToStatusMapping;
 
    private eParameter(String name) {
        this.name = name;
    }
 
    public static eParameter geteParameter(String name) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(name);
    }
 
    private static void initMapping() {
        codeToStatusMapping = new HashMap<String, eParameter>();
        for (eParameter s : values()) {
            codeToStatusMapping.put(s.name, s);
        }
    }
 
    public String getName() {
        return name;
    }
}
