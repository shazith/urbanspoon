package com.ts.us.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ts.us.dto.Cuisine;
import com.ts.us.dto.Restaurant;
import com.ts.us.exception.UrbanspoonException;

public class CuisineDAO {

	public Cuisine insert(Cuisine cuisine) throws UrbanspoonException {

		return cuisine;
	}

	public Cuisine getCuisine(int cuisineId, boolean includeRecipes)
			throws UrbanspoonException {
		return null;

	}

	public List<Cuisine> getCuisines(boolean includeRecipes)
			throws UrbanspoonException {

		return null;

	}

	public List<Cuisine> getCuisines(int branchId, boolean includeRecipes)
			throws UrbanspoonException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Cuisine> cuisinesList = null;

		connection = DAOUtility.getConnection();

		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection
					.prepareStatement("select distinct cuisine.* from recipe join cuisine on cuisine.id=recipe.cuisine_id join serve on serve.recipe_id=recipe.id where branch_id=?");
			preparedStatement.setInt(1, branchId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				cuisinesList = new ArrayList<Cuisine>();
				do {

					Cuisine cuisine = new Cuisine();
					cuisine.setId(resultSet.getInt(1));
					cuisine.setName(resultSet.getString(2));
					cuisine.setCountry(resultSet.getString(3));

					if (includeRecipes) {
						RecipeDAO recipeDAO = new RecipeDAO();
						cuisine.setRecipesList(recipeDAO.getRecipes(cuisine.getId(), branchId));
					}
					cuisinesList.add(cuisine);
				} while (resultSet.next());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtility.close(resultSet, preparedStatement, connection);
		}

		return cuisinesList;

	}

}
