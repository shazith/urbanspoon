package com.ts.us.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ts.us.dto.Restaurant;
import com.ts.us.exception.UrbanspoonException;

public class RestaurantDAO {

	public List<Restaurant> getRestaurants(boolean includeBranches)
			throws UrbanspoonException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Restaurant> restaurantsList = null;

		connection = DAOUtility.getConnection();
		try {
			connection = DAOUtility.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from restaurant");
			if (resultSet.next()) {
				restaurantsList = new ArrayList<Restaurant>();
				do {

					Restaurant restaurant = new Restaurant();
					restaurant.setId(resultSet.getLong(1));
					restaurant.setGovtRegistrationtId(resultSet.getString(2));
					restaurant.setName(resultSet.getString(3));
					restaurant.setPassword(resultSet.getString(4));
					restaurant.setLogo(resultSet.getString(5));
					if (includeBranches) {
						BranchDAO branchDAO = new BranchDAO();
						restaurant.setBranchesList(branchDAO.getBranches(
								restaurant.getId(), true));
					}
					restaurantsList.add(restaurant);
				} while (resultSet.next());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtility.close(resultSet, statement, connection);
		}

		return restaurantsList;

	}

	public Restaurant getRestaurant(int restaurantId, boolean includeBranches)
			throws UrbanspoonException {

		return null;

	}

	public Restaurant insert(Restaurant restaurant) throws UrbanspoonException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		connection = DAOUtility.getConnection();
		try {
			preparedStatement = connection
					.prepareStatement("insert into restaurant(govt_registration_id,name,password) values(?,?,?) ");

			preparedStatement.setString(1, restaurant.getGovtRegistrationtId());
			preparedStatement.setString(2, restaurant.getName());
			preparedStatement.setString(3, restaurant.getPassword());
			int insertStatus = preparedStatement.executeUpdate();
			if (insertStatus > 0) {
				int latestId = DAOUtility.getLatestId("restaurant");
				restaurant.setId(latestId);
				updateLogoAddress(latestId, restaurant.getLogo());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtility.close(preparedStatement, connection);
		}

		return restaurant;

	}

	public Restaurant getRestaurant(String govtRegistrationId,
			boolean includeBranches) throws UrbanspoonException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		connection = DAOUtility.getConnection();
		Restaurant restaurant = new Restaurant();
		try {
			preparedStatement = connection
					.prepareStatement("select * from restaurant where govt_registration_id=?");
			preparedStatement.setString(1, govtRegistrationId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				restaurant.setId(resultSet.getLong(1));
				restaurant.setGovtRegistrationtId(resultSet.getString(2));
				restaurant.setName(resultSet.getString(3));
				restaurant.setPassword(resultSet.getString(4));
				restaurant.setLogo(resultSet.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restaurant;

	}

	public boolean updateLogoAddress(long restaurantId, String fileName)
			throws UrbanspoonException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		connection = DAOUtility.getConnection();
		try {
			preparedStatement = connection
					.prepareStatement("update restaurant set logo_name=?  where id=?");
			preparedStatement.setString(1, fileName);
			preparedStatement.setLong(2, restaurantId);
			int insertStatus = preparedStatement.executeUpdate();
			if (insertStatus > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtility.close(preparedStatement, connection);
		}

		return false;
	}

}