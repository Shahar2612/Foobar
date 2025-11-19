package com.example.project2_android.Register;

import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for validating user input during the sign-up process.
 */
public class SignUpValidation {

    /**
     * Checks if a password meets the specified criteria.
     *
     * @param password The password to validate.
     * @return True if the password is valid, false otherwise.
     */
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Checks if two passwords match.
     *
     * @param password        The first password.
     * @param confirmPassword The second password for confirmation.
     * @return True if the passwords match, false otherwise.
     */
    private boolean isMatchingPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    /**
     * Checks if a profile picture is selected.
     *

     * @param imageBase64 The base64 string of the selected picture.
     * @return True if a picture is selected, false otherwise.
     */
    public boolean isPictureValid(String imageBase64) {
        return imageBase64 != null;

    }

    /**
     * Retrieves input fields with invalid data.
     *
     * @param editTexts The EditText fields to validate.
     * @return A list of EditText fields with invalid data.
     */
    public Map<EditText, String> getInvalidFields(EditText... editTexts) {
        Map<EditText, String> invalid = new HashMap<>();
        for (int i = 0; i < editTexts.length; i++) {
            String x = editTexts[i].getText().toString().trim();
            if (x.isEmpty()) {
                invalid.put(editTexts[i], "Field cannot be empty");
            } else if (i == 3) {
                // Check if the password is valid
                if (!isValidPassword(x)) {
                    invalid.put(editTexts[i], "Invalid password");
                }
            } else if (i == 4) {
                // Check if the password and confirm password match
                if (!isMatchingPassword(editTexts[3].getText().toString().trim(), x)) {
                    invalid.put(editTexts[i], "Passwords do not match");
                }
            }
        }
        return invalid;
    }

}

