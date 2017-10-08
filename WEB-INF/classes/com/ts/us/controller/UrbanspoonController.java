package com.ts.us.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ts.us.exception.UrbanspoonException;
import com.ts.us.helper.UrbanspoonHelper;

/**
 * Servlet implementation class UrbanspoonController
 */
@WebServlet("/UrbanspoonController")
public class UrbanspoonController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UrbanspoonController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("restaurantsList",
					UrbanspoonHelper.getRestaurants(true));
			request.getRequestDispatcher("home.jsp").forward(request, response);
		} catch (UrbanspoonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(
					diskFileItemFactory);

			List<FileItem> fileItems;
			try {
				fileItems = servletFileUpload.parseRequest(request);
				UrbanspoonHelper.addRestaurant(fileItems, request, response);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UrbanspoonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else {
			System.out.println(action);
			if (action.equals("user_registration")) {
				try {
					UrbanspoonHelper.addUser(request, response);

				} catch (UrbanspoonException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		if (action.equals("login")) {
			String loginAs = request.getParameter("loginAs");
			if (loginAs.equals("user")) {
				try {
					if (UrbanspoonHelper.loginAsUser(request, response)) {
						response.sendRedirect("userHome.jsp");
					}
				} catch (UrbanspoonException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (loginAs.equals("restaurant")) {
				try {
					if (UrbanspoonHelper.loginAsRestaurantOwner(request,
							response)) {
						response.sendRedirect("restaurantHome.jsp");
					}
				} catch (UrbanspoonException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
}
