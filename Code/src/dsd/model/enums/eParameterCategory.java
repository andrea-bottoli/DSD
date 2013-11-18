package dsd.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum eParameterCategory
{
	ShiftingWeights(1), VehicleBreaking(2), WeightOfTheStack(3), HydroDyanmicThrust(4), WindThrust(5), GeometryOfTheStackNr30(6);
	
	private int code;
	 
    /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<Integer, eParameterCategory> codeToStatusMapping;
 
    private eParameterCategory(int code) {
        this.code = code;
    }
 
    public static eParameterCategory getParameterCategory(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);
    }
 
    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, eParameterCategory>();
        for (eParameterCategory s : values()) {
            codeToStatusMapping.put(s.code, s);
        }
    }
 
    public int getCode() {
        return code;
    }
}
