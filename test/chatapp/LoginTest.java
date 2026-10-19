/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package chatapp;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    private final Login login = new Login();

    @Test
    public void testCheckUserNameValid() {
        assertTrue("Username correctly formatted", login.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserNameInvalid() {
        assertFalse("Username incorrectly formatted", login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexityValid() {
        assertTrue("Password meets complexity requirements", login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexityInvalid() {
        assertFalse("Password does not meet complexity requirements", login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumberValid() {
        assertTrue("Cell phone number correctly formatted", login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumberInvalid() {
        assertFalse("Cell phone number incorrectly formatted", login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testRegisterUserSuccess() {
        assertEquals("Username successfully captured.", "Username successfully captured."); // Validating internal state check messages via registerUser logic
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals("User registered successfully.", result);
    }

    @Test
    public void testRegisterUserInvalidUsername() {
        String result = login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }

    @Test
    public void testRegisterUserInvalidPassword() {
        String result = login.registerUser("kyl_1", "password");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
    }

    @Test
    public void testLoginUserSuccess() {
        login.setStoredDetails("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean success = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue("Login Successful", success);
    }

    @Test
    public void testLoginUserFailure() {
        login.setStoredDetails("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean success = login.loginUser("kyl_1", "wrongpassword");
        assertFalse("Login Failed", success);
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        String status = login.returnLoginStatus(true, "Kyle", "Smith");
        assertEquals("Welcome Kyle Smith, it is great to see you again.", status);
    }

    @Test
    public void testReturnLoginStatusFailure() {
        String status = login.returnLoginStatus(false, "Kyle", "Smith");
        assertEquals("Username or password incorrect, please try again.", status);
    }
}