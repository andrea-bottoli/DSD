package dsd.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum eUserRole {
	Administrator(1), Engineere(2), User(3);

	private int code;

	private eUserRole(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}

	private static Map<Integer, eUserRole> codeToRoleMapping;

	public static eUserRole getSonarType(int i) {
		if (codeToRoleMapping == null) {
			initMapping();
		}
		return codeToRoleMapping.get(i);
	}

	private static void initMapping() {
		codeToRoleMapping = new HashMap<Integer, eUserRole>();
		for (eUserRole s : values()) {
			codeToRoleMapping.put(s.code, s);
		}
	}

}
