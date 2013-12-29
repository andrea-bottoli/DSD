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
package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dsd.calculations.CryptFunktions;
import dsd.model.User;
import dsd.model.enums.eUserRole;

public class UserAccessDAO {

	private static String usertable = "users";
	private static String roleTable = "users_roles";
	private static String[] usertableFields = new String[] { "ID", "username",
			"surename", "lastname", "password", "email" };
	private static String[] roleTableFields = new String[] { "ID", "username",
			"role" };

	public static User selectUserByUsername(String username) {

		ArrayList<User> userList = selectUser(usertableFields[1], username);
		if (userList.size() == 1)
			return userList.get(0);
		else
			return null;

	}

	public static ArrayList<User> selectUserByLastname(String username) {

		return selectUser(usertableFields[3], username);

	}

	public static ArrayList<User> selectUserByFirstname(String username) {

		return selectUser(usertableFields[2], username);

	}

	public static ArrayList<User> getAllUsers() {
		ArrayList<User> userList = new ArrayList<User>();

		try {
			Connection con = DAOProvider.getDataSource().getConnection();

			Object[] parameters = {};
			String table = usertable + " right join " + roleTable + " on "
					+ usertable + "." + usertableFields[1] + " = " + roleTable
					+ "." + roleTableFields[1];
			ResultSet result = DAOProvider.SelectTableSecure(table, "*", "",
					"", con, parameters);
			String blub = result.getStatement().toString();
			System.out.println(blub);
			while (result.next()) {
				User user = new User();
				user.setUsername(result.getString(usertableFields[1]));
				user.setSurename(result.getString(usertableFields[2]));
				user.setLastname(result.getString(usertableFields[3]));
				user.setPasswd(result.getString(usertableFields[4]));
				user.setEmail(result.getString(usertableFields[5]));
				user.setRole(eUserRole.valueOf(result
						.getString(roleTableFields[2])));
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}

	private static ArrayList<User> selectUser(String field, String param) {
		try {
			ArrayList<User> userList = new ArrayList<User>();
			Connection con = DAOProvider.getDataSource().getConnection();

			Object[] parameters = { param };
			String table = usertable + " join " + roleTable + " on "
					+ usertable + "." + usertableFields[1] + " = " + roleTable
					+ "." + roleTableFields[1];
			String select = "*";
			String where = usertable + "." + field + " = ?";
			String order = "";
			ResultSet result = DAOProvider.SelectTableSecure(table, select,
					where, order, con, parameters);

			System.out.println(result.getStatement().toString());
			while (result.next()) {
				User user = new User();
				user.setUsername(result.getString(usertableFields[1]));
				user.setSurename(result.getString(usertableFields[2]));
				user.setLastname(result.getString(usertableFields[3]));
				user.setPasswd(result.getString(usertableFields[4]));
				user.setEmail(result.getString(usertableFields[5]));
				user.setRole(eUserRole.valueOf(result
						.getString(roleTableFields[2])));
				userList.add(user);
			}
			return userList;
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
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static boolean deletUser(String username) {
		Object[] param = { username };
		try {
			Connection con = DAOProvider.getDataSource().getConnection();

			int result1 = DAOProvider.DeleteRowSecure(roleTable,
					roleTableFields[1] + " = ?", con, param);
			int result2 = DAOProvider.DeleteRowSecure(usertable,
					usertableFields[1] + " = ?", con, param);
			if (result1 != 0 && result2 != 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
}
