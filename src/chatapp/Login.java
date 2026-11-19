/*
 * Reference:
 * Oracle Corporation. (2025). Java Platform, Standard Edition API Specification. 
 * Available at: https://docs.oracle.com/en/java/javase/21/docs/api/ [Accessed: November 2026].
 */
package chatapp;

import java.util.regex.Pattern;

public class Login {

    private String storedFirstName;
    private String storedLastName;
    private String storedUsername;
    private String storedPassword;
    private String storedCell;

    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String cell) {
        return cell != null && Pattern.matches("^\\+[0-9]{9,11}$", cell);
    }

    public String getCellPhoneMessage(String cell) {
        if (checkCellPhoneNumber(cell)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
    }

    public String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        return "User registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        return storedUsername != null && storedUsername.equals(username) && storedPassword.equals(password);
    }

    public String returnLoginStatus(boolean success, String firstName, String lastName) {
        if (success) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

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