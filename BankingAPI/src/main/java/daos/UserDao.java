package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.ConnectionService;
import model.Account;
import model.AccountStatus;
import model.AccountType;
import model.Role;
import model.User;
//import model.Role;

public class UserDao {
	Connection connection;
	PreparedStatement ps;

	public UserDao() {
		this.connection = ConnectionService.getConnection();
	}

	// creates a new user
	public void createUser(User u) {
		try {
			if (connection == null) {
				connection = ConnectionService.getConnection();
			}
			ps = connection.prepareStatement("INSERT INTO users values (" + u.getUserId() + ",'" + u.getUsername()
					+ "','" + u.getPassword() + "','" + u.getFirstName() + "','" + u.getLastName() + "','"
					+ u.getEmail() + "'," + u.getRole().getRoleId() + ");");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createAccount(Account a) {
		try {
			if (connection == null) {
				connection = ConnectionService.getConnection();
			}
			ps = connection.prepareStatement("INSERT INTO accounts values (" + a.getAccountId() + "," + a.getBalance()
					+ "," + a.getStatus().getStatusId() + "," + a.getType().getTypeId() + ");");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// returns the first user with their information = info in the specified column
	public User getUser(String column, String info) {
		try {
			if (connection == null) {
				connection = ConnectionService.getConnection();
			}
			ps = connection.prepareStatement("SELECT * from users WHERE " + column + " = '" + info + "';");
			ResultSet rs = ps.executeQuery();
			User ret = new User();
			while (rs.next()) {
				ret.setUserId(rs.getInt("id"));
				ret.setUsername(rs.getString("username"));
				ret.setPassword(rs.getString("password"));
				ret.setFirstName(rs.getString("firstName"));
				ret.setLastName(rs.getString("lastName"));
				ret.setEmail(rs.getString("email"));
				Role r = new Role();
				r.setRoleId(rs.getInt("roleId"));
				r.getRole();
				ret.setRole(r);
			}
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new User();
	}

	public Account getAccount(String column, String info) {
		try {
			if (connection == null) {
				connection = ConnectionService.getConnection();
			}
			ps = connection.prepareStatement("SELECT * from accounts WHERE " + column + " = '" + info + "';");
			ResultSet rs = ps.executeQuery();
			Account ret = new Account();
			while (rs.next()) {
				ret.setAccountId(rs.getInt("id"));
				ret.setBalance(rs.getDouble("balance"));
				AccountStatus as = new AccountStatus();
				as.setStatusId(rs.getInt("accStatus"));
				ret.setStatus(as);
				AccountType at = new AccountType();
				at.setTypeId(rs.getInt("accType"));
				ret.setType(at);
			}
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Account();
	}

	public int getUserId() {
		int num = 0;
		ResultSet rs;
		try {
			ps = connection.prepareStatement("SELECT * from users;");
			rs = ps.executeQuery();
			while (rs.next()) {
				num = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		num++;
		return num;
	}

	// updates the user with the same id to have the new information
	public void updateUser(User u) {
		try {
			ps = connection.prepareStatement(
					"UPDATE users SET username = ?, password = ?, firstName = ?, lastName = ?, email = ?, roleId = ?"
							+ "WHERE id = ?;");
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getEmail());
			ps.setInt(5, u.getRole().getRoleId());
			ps.setInt(6, u.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// connects a new account to a user
	public void addAccount(int userId, Account a) {
		try {
			ps = connection.prepareStatement(
					"Insert INTO userToAccount (userId, accountId) VALUES ('" + userId + a.getAccountId() + ");");
			ps.executeUpdate();
			ps = connection.prepareStatement("Insert INTO accounts (id,balance,accStatus,accType) VALUES ('"
					+ a.getAccountId() + a.getBalance() + a.getStatus().getStatusId() + a.getType().getTypeId() + ");");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// tracks an account to a user
	public int findAccount(int a) {
		int ret = -1;
		try {
			ps = connection.prepareStatement("SELECT FROM * userToAccount WHERE accountId = '" + a + "';");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ret = rs.getInt("userId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	// removes an account
	public void deleteAccount(int accountId) {
		try {
			ps = connection.prepareStatement("DELETE FROM accounts WHERE id = ?;");
			ps.setInt(1, accountId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// deletes a User
	public void deleteUser(int id) {
		try {
			ps = connection.prepareStatement("DELETE FROM users WHERE id = ?;");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// update account
	public void transaction(int amount, int accId) {
		try {
			ps = connection.prepareStatement("UPDATE accounts SET balance = balance + ? where id = ?");
			ps.setInt(1, amount);
			ps.setInt(2, accId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// transfers funds
	public void transfer(int amount, int accIdA, int accIdB) {
		// lock
		transaction(-amount, accIdA);
		transaction(amount, accIdB);
		// unlock
	}
}
