package com.ts.us.helper;

import java.io.File;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

import com.ts.us.dao.DAOUtility;
import com.ts.us.dao.RestaurantDAO;
import com.ts.us.dao.UserDAO;
import com.ts.us.dto.Branch;
import com.ts.us.dto.Cuisine;
import com.ts.us.dto.FeedbackType;
import com.ts.us.dto.Recipe;
import com.ts.us.dto.Restaurant;
import com.ts.us.dto.User;
import com.ts.us.exception.UrbanspoonException;

public class UrbanspoonHelper {
	private static final String IMAGESLOCATION = "/home/tsuser/j08/alekya/workspace/Urbanspoon/WebContent/images/";

	public static long getLoggedUserId(HttpServletRequest request) {

		return -1;
	}

	public static List<Restaurant> getRestaurants(boolean includeBranches)
			throws UrbanspoonException {
		RestaurantDAO restaurantDAO = new RestaurantDAO();
		List<Restaurant> restaurants = restaurantDAO.getRestaurants(true);
		return restaurants;

	}

	public static List<Branch> getBranches(HttpServletRequest request,
			boolean includeCuisines) throws UrbanspoonException {
		return null;

	}

	public static List<Cuisine> getCuisine(boolean includeRecipe)
			throws UrbanspoonException {
		return null;

	}

	public static List<FeedbackType> getFeedbackTypesList()
			throws UrbanspoonException {
		return null;

	}

	public static Branch getBranch(int branchId, boolean includeCuisines)
			throws UrbanspoonException {
		return null;

	}

	public static Restaurant getRestaurant(int restaurantId,
			boolean includeBranches) throws UrbanspoonException {

		return null;

	}

	public static Recipe getRecipe(int recipeId) throws UrbanspoonException {
		return null;

	}

	public static List<FileItem> getFileItems(HttpServletRequest request)
			throws UrbanspoonException {

		return null;
	}

	public static boolean addRestaurant(List<FileItem> fileItemsList,
			HttpServletRequest request, HttpServletResponse response)
			throws UrbanspoonException {
		Restaurant restaurant = new Restaurant();
		for (FileItem fileItem : fileItemsList) {
			if (fileItem.getFieldName().equals("name")) {
				restaurant.setName(fileItem.getString());
			} else if (fileItem.getFieldName().equals("govt_registration_id")) {
				restaurant.setGovtRegistrationtId(fileItem.getString());
			} else if (fileItem.getFieldName().equals("password")) {
				restaurant.setPassword(fileItem.getString());
			}
			if (!(fileItem.isFormField())) {
				int latestId = DAOUtility.getLatestId("restaurant");
				File file = new File(IMAGESLOCATION + "restaurants//"
						+ latestId + "_logo.jpg");
				try {
					fileItem.write(file);
					restaurant.setLogo(latestId + "_logo.jpg");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		RestaurantDAO restaurantDAO = new RestaurantDAO();
		restaurantDAO.insert(restaurant);

		return false;
	}

	public static boolean addBranch(List<FileItem> fileItemsList,
			HttpServletRequest request, HttpServletResponse response)
			throws UrbanspoonException {

		return false;
	}

	public static boolean addRecipeToBranch(List<FileItem> fileItemsList,
			HttpServletRequest request, HttpServletResponse response)
			throws UrbanspoonException {

		return false;
	}

	public static boolean addUser(HttpServletRequest request,
			HttpServletResponse response) throws UrbanspoonException {

		User user = new User();
		user.setName(request.getParameter("first_name")
				+ request.getParameter("last_name"));
		user.setGender(request.getParameter("gender"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		user.setMobileNumber(Long.parseLong(request
				.getParameter("mobile_number")));
		UserDAO userDAO = new UserDAO();
		userDAO.insert(user);
		if (user.getId() != 0)
			return true;
		return false;
	}

	public static boolean addBranchFeedback(HttpServletRequest request,
			HttpServletResponse response) throws UrbanspoonException {

		return false;
	}

	public static boolean addRecipeFeedback(HttpServletRequest request,
			HttpServletResponse response) throws UrbanspoonException {

		return false;
	}

	public static boolean addRecipe(HttpServletRequest request,
			HttpServletResponse response) throws UrbanspoonException {

		return false;
	}

	public static boolean addCuisine(HttpServletRequest request,
			HttpServletResponse response) throws UrbanspoonException {

		return false;
	}

	public static boolean storeImage(FileItem fileItem, String imageType,
			String fileName) throws UrbanspoonException {

		return false;
	}

	public static boolean loginAsUser(HttpServletRequest request,
			HttpServletResponse response) throws UrbanspoonException {

		String userId = request.getParameter("user_id");
		String password = request.getParameter("password");
		User user = new UserDAO().getUser(userId);
		if (user.getPassword().equals(password)) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("loggedUser", user);
			return true;
		}
		return false;
	}

	public static boolean loginAsRestaurantOwner(HttpServletRequest request,
			HttpServletResponse response) throws UrbanspoonException {
		String userId = request.getParameter("user_id");
		String password = request.getParameter("password");
		Restaurant restaurant = new RestaurantDAO()
				.getRestaurant(userId, false);
		if (restaurant.getPassword().equals(password)) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("loggedUser", restaurant);
			return true;
		}

		return false;
	}
}