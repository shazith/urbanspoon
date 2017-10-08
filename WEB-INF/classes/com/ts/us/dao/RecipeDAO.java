package com.ts.us.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ts.us.dto.Branch;
import com.ts.us.dto.Cuisine;
import com.ts.us.dto.Recipe;
import com.ts.us.exception.UrbanspoonException;

public class RecipeDAO {

	public Recipe insert(int cuisineId, Recipe recipe)
			throws UrbanspoonException {
		return recipe;

	}

	public List<Recipe> getRecipes() throws UrbanspoonException {
		return null;

	}

	public List<Recipe> getRecipes(int cuisineId) throws UrbanspoonException {
		return null;

	}

	public Recipe getRecipe(int recipeId) throws UrbanspoonException {
		return null;

	}

	public List<Recipe> getRecipes(int cuisineId, int branchId)
			throws UrbanspoonException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Recipe> recipesList = null;
		connection = DAOUtility.getConnection();
		try {
			preparedStatement = connection
					.prepareStatement("select * from recipe join serve on serve.recipe_id=recipe.id where cuisine_id=? and branch_id=? ");
			preparedStatement.setInt(1, cuisineId);
			preparedStatement.setInt(2, branchId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				recipesList = new ArrayList<Recipe>();
				do {
					Recipe recipe = new Recipe();
					recipe.setId(resultSet.getInt(1));
					recipe.setName(resultSet.getString(2));
					recipe.setDescription(resultSet.getString(3));
					recipe.setVeg(resultSet.getBoolean(4));
					recipe.setPrice(resultSet.getFloat(5));
					recipe.setImage(resultSet.getString(6));

					recipesList.add(recipe);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtility.close(resultSet, preparedStatement, connection);
		}

		return recipesList;

	}

	public boolean addRecipeToBranch(long recipeId, long branchId, float price,
			String imagePath) {
		return false;
	}

}
