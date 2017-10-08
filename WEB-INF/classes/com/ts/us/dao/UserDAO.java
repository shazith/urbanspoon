package com.ts.us.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ts.us.dto.User;
import com.ts.us.exception.UrbanspoonException;

public class UserDAO {

	public User insert(User user) throws UrbanspoonException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		connection = DAOUtility.getConnection();
		try {
			preparedStatement = connection
					.prepareStatement("insert into user(name,gender,email,password,mobile_number) values(?,?,?,?,?) ");

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getGender());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setLong(5, user.getMobileNumber());
			int insertStatus = preparedStatement.executeUpdate();
			if (insertStatus > 0) {
				int latestId = DAOUtility.getLatestId("user");
				user.setId(latestId);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtility.close(preparedStatement, connection);
		}

		return user;
	}

	public User getUser(String email) throws UrbanspoonException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		connection = DAOUtility.getConnection();
		User user = new User();
		try {
			preparedStatement = connection
					.prepareStatement("select * from user where email=?");
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {

				user.setId(resultSet.getLong(1));
				user.setName(resultSet.getString(2));
				user.setGender(resultSet.getString(3));
				user.setEmail(resultSet.getString(4));
				user.setPassword(resultSet.getString(5));
				user.setMobileNumber(resultSet.getLong(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;

	}

	public User getUser(int userId) throws UrbanspoonException {
		return null;

	}
}
