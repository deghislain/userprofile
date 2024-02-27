package com.userprofile.service;

/*public class AuthenticationService {

	// During 2FA setup:
	String secret = Base32.random();
	String qrCodeUrl = QRCode.generateQRCodeUrl(user, secret);
	// ... store secret in the user's data

	// During login:
	String token = request.getParameter("twoFactorToken");
	boolean verified = TOTP.verify(secret, token, 2); // Allow 2 time steps for clock drift
	if (verified) {
	    // Complete login
	} else {
	    // Handle invalid token
	}
	
	
	// In your authentication service
		public boolean login(String username, String password, String totpCode) {
		    if (isValidPrimaryLogin(username, password)) {
		        User user = userRepository.findByUsername(username);
		        if (user.isTwoFactorEnabled()) {
		            if (isValidTotpCode(user.getTotpSecret(), totpCode)) {
		                // Login successful, set up session
		                return true; 
		            } else {
		                return false; // Invalid TOTP code
		            }
		        } else {
		            // Login successful (no 2FA)
		            return true;
		        }
		    }
		    return false; // Invalid username/password
		}
}
*/