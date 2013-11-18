package dsd.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum eSonarType {
	CorrectData(1), UncertainData(2), WrongData(3), SonarOutOfWaterData(4), ErrorData(5);
 
    private int code;
 
    /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<Integer, eSonarType> codeToStatusMapping;
 
    private eSonarType(int code) {
        this.code = code;
    }
 
    public static eSonarType getSonarType(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);
    }
 
    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, eSonarType>();
        for (eSonarType s : values()) {
            codeToStatusMapping.put(s.code, s);
        }
    }
 
    public int getCode() {
        return code;
    }
}
