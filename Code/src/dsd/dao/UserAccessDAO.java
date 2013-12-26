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
package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dsd.calculations.CryptFunktions;
import dsd.model.User;
import dsd.model.enums.eUserRole;

public class UserAccessDAO {

	private static String usertable = "user";
	private static String roleTable = "user_roles";
	private static String[] usertableFields = new String[] { "ID", "username",
			"surename", "lastname", "password", "email" };
	private static String[] roleTableFields = new String[] { "ID", "user",
			"role" };

	public static User selectUserByUsername(String username) {

		return selectUser(usertableFields[1], username);

	}

	public static User selectUserByLastname(String username) {

		return selectUser(usertableFields[1], username);

	}

	public static ArrayList<User> getAllUsers() {
		ArrayList<User> userList = new ArrayList<User>();

		try {
			Connection con = DAOProvider.getDataSource().getConnection();

			Object[] parameters = {};
			ResultSet result = DAOProvider.SelectTableSecure(usertable, "", "",
					"", con, parameters);
			while (result.next()) {
				User user = new User();
				user.setUsername(result.getString(usertableFields[1]));
				user.setSurename(result.getString(usertableFields[2]));
				user.setLastname(result.getString(usertableFields[3]));
				user.setPasswd(result.getString(usertableFields[4]));
				user.setEmail(result.getString(usertableFields[5]));
				user.setRole(eUserRole.getSonarType(result
						.getInt(roleTableFields[2])));
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}

	private static User selectUser(String field, String param) {
		try {
			User user = new User();
			Connection con = DAOProvider.getDataSource().getConnection();

			Object[] parameters = { param };
			String table = usertable + " join " + roleTable + " on "
					+ usertable + "." + usertableFields[1] + " = " + roleTable
					+ "." + roleTableFields[1];
			String select = "*";
			String where = field + " = ?";
			String order = "";
			ResultSet result = DAOProvider.SelectTableSecure(table, select,
					where, order, con, parameters);
			while (result.next()) {

				user.setUsername(result.getString(usertableFields[1]));
				user.setSurename(result.getString(usertableFields[2]));
				user.setLastname(result.getString(usertableFields[3]));
				user.setPasswd(result.getString(usertableFields[4]));
				user.setEmail(result.getString(usertableFields[5]));
				user.setRole(eUserRole.getSonarType(result
						.getInt(roleTableFields[2])));
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void setUser(User user) {

	}

	@SuppressWarnings("unused")
	public static boolean checkPassword(String username, String password) {
		String cryptedPassword = CryptFunktions.MD5(password);
		Object[] param = { username };
		try {
			Connection con = DAOProvider.getDataSource().getConnection();

			ResultSet result = DAOProvider.SelectTableSecure(usertable,
					usertableFields[4], usertableFields[1] + " = ? ", "", con,
					param);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
