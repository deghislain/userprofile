package com.userprofile.validator;

import org.springframework.stereotype.Component;

import com.userprofile.model.BaseModel;
import com.userprofile.model.UserProfile;



@Component
public class UserProfileValidator implements IModelValidator{

	@Override
	public String validate(BaseModel bm) {
		UserProfile up = (UserProfile)bm;
		String result = "";
		
		if(up.getUserId() == null || up.getUserId().isBlank()) {
			result += "userId,";
		}
		if(up.getUsername() == null || up.getUsername().isBlank()) {
			result += " username,";
		}
		if(up.getPassword() == null || up.getPassword().isBlank()) {
			result += " password,";
		}
		if(up.getFirstname() == null || up.getFirstname().isBlank()) {
			result += " firstname,";
		}
		if(up.getLastname() == null || up.getLastname().isBlank()) {
			result += " lastname,";
		}
		if(up.getEmail() == null || up.getEmail().isBlank()) {
			result += " email,";
		}
		if(up.getPhone() == null || up.getPhone().isBlank()) {
			result += " phone";
		}
		
		return result;
	}

}
