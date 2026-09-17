package chatapp;

import java.util.Scanner;

public class ChatApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login Login = new Login();

        System.out.println("=============================");
        System.out.println("========== ChatApp ==========");
        System.out.println("=============================");

        // First and last name
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        // Username (retry until valid)
        
        while (true) {
            System.out.print("Create username (must contain '_' and ≤ 5 chars): ");
            username = scanner.nextLine();
            if (Login.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        // Password (retry until valid)
        String password;
        while (true) {
            System.out.print("Create password (≥8 chars, uppercase, number, special): ");
            password = scanner.nextLine();
            if (Login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        // Cell phone (retry until valid)
        String cell;
        while (true) {
            System.out.print("Enter cell phone number (e.g., +27838968976): ");
            cell = scanner.nextLine();
            if (Login.checkCellPhoneNumber(cell)) {
                System.out.println("Cell number successfully captured.");
                break;
            } else {
                System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
            }
        }

        // All valid – store and register
        Login.setStoredDetails(firstName, lastName, username, password, cell);
        System.out.println(Login.registerUser(username, password));
        System.out.println("Registration complete.\n");

        // Login
        System.out.println("=============================");
        System.out.println("========== Login ============");
        System.out.println("=============================");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();

        boolean loginSuccess = Login.loginUser(loginUser, loginPass);
        System.out.println(Login.returnLoginStatus(loginSuccess, Login.getStoredFirstName(), Login.getStoredLastName()));

        scanner.close();
    }
}