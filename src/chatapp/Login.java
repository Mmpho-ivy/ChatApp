package chatapp;

import java.util.regex.Pattern;

public class Login {

    private String storedFirstName;
    private String storedLastName;
    private String storedUsername;
    private String storedPassword;
    private String storedCell;

    // Check username: contains '_' and max 5 characters
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Check password: length >= 8, uppercase, digit, special
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    // Check cell phone: starts with '+', total length 10-12 chars
    // regex adapted from standard international number patterns
    public boolean checkCellPhoneNumber(String cell) {
        return Pattern.matches("^\\+[0-9]{9,11}$", cell);
    }

    // Returns the cell phone message (used in ChatApp and unit tests)
    public String getCellPhoneMessage(String cell) {
        if (checkCellPhoneNumber(cell)) {
            return "Cell number successfully captured.";
        } else {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
    }

    // Registration status (username and password checks)
    public String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        return "User registered successfully.";
    }

    // Verify login credentials
    public boolean loginUser(String username, String password) {
        return storedUsername != null && storedUsername.equals(username) && storedPassword.equals(password);
    }

    // Login status message
    public String returnLoginStatus(boolean success, String firstName, String lastName) {
        if (success) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Store user details
    public void setStoredDetails(String firstName, String lastName, String username, String password, String cell) {
        this.storedFirstName = firstName;
        this.storedLastName = lastName;
        this.storedUsername = username;
        this.storedPassword = password;
        this.storedCell = cell;
    }

    public String getStoredFirstName() { return storedFirstName; }
    public String getStoredLastName() { return storedLastName; }
    public String getStoredUsername() { return storedUsername; }
    public String getStoredPassword() { return storedPassword; }
    public String getStoredCell() { return storedCell; }
}