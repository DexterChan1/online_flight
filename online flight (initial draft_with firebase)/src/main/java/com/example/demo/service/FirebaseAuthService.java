package com.example.demo.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;


@Service
public class FirebaseAuthService {
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    // User Registration
    public String registerUser(String email, String password) throws FirebaseAuthException, com.google.firebase.auth.FirebaseAuthException {
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest();
        createRequest.setEmail(email);
        createRequest.setPassword(password);
        UserRecord userRecord = firebaseAuth.createUser(createRequest);
        return userRecord.getUid();

    }

    // User Login
    public String loginUser(String email, String password) throws FirebaseAuthException {
        try {
            // Verify the user's credentials
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);

            // Authenticate the user (create a custom token)
            String customToken = firebaseAuth.createCustomToken(userRecord.getUid());

            // Return the custom token to the client
            return customToken;
        } catch (FirebaseAuthException e) {
            // Handle authentication failure, e.g., invalid credentials
            throw new FirebaseAuthException("Failed to authenticate user.", e.getAuthErrorCode(), e);
        } catch (com.google.firebase.auth.FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }


    // Get Firebase UID by Email
    public String getFirebaseUidByEmail(String email) throws FirebaseAuthException {
        try {
            // Retrieve the UserRecord using the provided email
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);

            // Get and return the Firebase UID from the UserRecord
            return userRecord.getUid();
        } catch (FirebaseAuthException e) {
            // Handle exceptions if the email is not found or other Firebase Authentication errors
            throw new FirebaseAuthException("Failed to get Firebase UID by email.", e.getAuthErrorCode(), e);
        } catch (com.google.firebase.auth.FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }
    // Password Reset
    public void resetPassword(String email) throws FirebaseAuthException {
        try {
            // Generate a password reset link
            String link = firebaseAuth.generatePasswordResetLink(email);

            // TODO: Send the password reset link to the user (e.g., by email)
            // You need to implement the email sending logic here
        } catch (FirebaseAuthException e) {
            // Handle password reset failure, e.g., email not found
            throw new FirebaseAuthException("Failed to reset password.", e.getAuthErrorCode(), e);
        } catch (com.google.firebase.auth.FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }

    public class FirebaseAuthException extends RuntimeException {

        private String authErrorCode;

        public FirebaseAuthException(String message, String authErrorCode, FirebaseAuthException e) {
            super(message);
            this.authErrorCode = authErrorCode;
        }

        public String getAuthErrorCode() {
            return authErrorCode;
        }
    }
}
