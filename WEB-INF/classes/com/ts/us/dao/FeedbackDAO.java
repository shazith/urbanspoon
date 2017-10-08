package com.ts.us.dao;

import java.util.List;

import com.ts.us.dto.Feedback;
import com.ts.us.exception.UrbanspoonException;

public class FeedbackDAO {

	public Feedback insertBranchFeedback(Feedback feedback) throws UrbanspoonException {
		return feedback;
		
	}

	public Feedback insertRecipeFeedback(Feedback feedback) throws UrbanspoonException {
		
		return feedback;

	}

	public List<Feedback> getRecipeFeedbacks(int recipeId, int branchId) throws UrbanspoonException {
		return null;
		

	}

	public List<Feedback> getBranchFeedbacks(int branchId) throws UrbanspoonException {
		return null;
		

	}

}
