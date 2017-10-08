package com.ts.us.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ts.us.dto.Branch;
import com.ts.us.exception.UrbanspoonException;

public class BranchDAO {

	public boolean addImage(long branchId, String fileName)
			throws UrbanspoonException {

		return false;
	}

	public Branch insert(long restaurantId, Branch branch)
			throws UrbanspoonException {

		return branch;
	}

	public List<Branch> getBranches(long restaurantId, boolean includeCuisines
			) throws UrbanspoonException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<Branch> branchList = null;
		ResultSet resultSet = null;
		connection = DAOUtility.getConnection();
		try {
			preparedStatement = connection
					.prepareStatement("select * from branch where restaurant_id=?");
			preparedStatement.setLong(1, restaurantId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				branchList = new ArrayList<Branch>();
				do {
					Branch branch = new Branch();
					branch.setId(resultSet.getInt(1));
					branch.setLocation(resultSet.getString(2));
					branch.setCity(resultSet.getString(3));
					branch.setState(resultSet.getString(4));
					branch.setCountry(resultSet.getString(5));
					branch.setPostalCode(resultSet.getInt(6));
					if (includeCuisines) {
						CuisineDAO cuisineDAO = new CuisineDAO();
						branch.setCuisinesList(cuisineDAO.getCuisines(
								branch.getId(), true));
					}
					branchList.add(branch);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtility.close(resultSet, preparedStatement, connection);
		}

		return branchList;
	}

	public Branch getBranch(int branchId, boolean includeCuisines)
			throws UrbanspoonException {

		return null;
	}

	public List<String> getBranchImages(int branchId)
			throws UrbanspoonException {
		return null;

	}

}
