package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
