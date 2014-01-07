/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brčić, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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
