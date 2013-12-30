/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, J�rn Tillmanns, Miraldi Fifo
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
package dsd.controller;

import java.util.ArrayList;

import dsd.dao.UserAccessDAO;
import dsd.model.User;

public class UserController {

	public static ArrayList<User> getAllUsers() {
		return UserAccessDAO.getAllUsers();
	}

	public static User getUser(String username) {
		return UserAccessDAO.selectUserByUsername(username);
	}

	public static void delUser(String username) {
		UserAccessDAO.deletUser(username);
	}

	public static boolean getUserExists(User user) {
		if (getUser(user.getUsername()) != null) {
			return true;
		}
		return false;
	}

	public static void saveUser(User user) {
		UserAccessDAO.saveUser(user);

	}

	public static void insertUser(User user) {
		UserAccessDAO.newUser(user);
	}
}
