/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
