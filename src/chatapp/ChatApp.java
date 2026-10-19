package chatapp;

import java.util.Scanner;
import java.util.Random;

public class ChatApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=============================");
        System.out.println("========== ChatApp ==========");
        System.out.println("=============================");

        // Registration Phase
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        String username;
        while (true) {
            System.out.print("Create username (must contain '_' and <= 5 chars): ");
            username = scanner.nextLine();
            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        String password;
        while (true) {
            System.out.print("Create password (>=8 chars, uppercase, number, special): ");
            password = scanner.nextLine();
            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        String cell;
        while (true) {
            System.out.print("Enter cell phone number (e.g., +27838968976): ");
            cell = scanner.nextLine();
            if (login.checkCellPhoneNumber(cell)) {
                System.out.println("Cell number successfully captured.");
                break;
            } else {
                System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
            }
        }

        login.setStoredDetails(firstName, lastName, username, password, cell);
        System.out.println(login.registerUser(username, password));
        System.out.println("Registration complete.\n");

        // Login Phase
        System.out.println("=============================");
        System.out.println("========== Login ============");
        System.out.println("=============================");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();

        boolean loginSuccess = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(loginSuccess, login.getStoredFirstName(), login.getStoredLastName()));

        // Part 2: Proceed only if login is successful
        if (loginSuccess) {
            System.out.println("\nWelcome to QuickChat.");
            boolean running = true;
            int totalMessagesSent = 0;

            while (running) {
                System.out.println("\n--- QuickChat Menu ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose an option (1-3): ");
                
                String choiceStr = scanner.nextLine();
                int choice;
                try {
                    choice = Integer.parseInt(choiceStr);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.print("How many messages would you like to enter? ");
                        int numMessages;
                        try {
                            numMessages = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Returning to menu.");
                            break;
                        }

                        Random random = new Random();

                        for (int i = 0; i < numMessages; i++) {
                            System.out.println("\n--- Entering Message " + (i + 1) + " of " + numMessages + " ---");

                            // Generate 10-digit random message ID
                            String messageID = String.format("%010d", Math.abs(random.nextLong() % 10000000000L));

                            // Recipient Cell input
                            String recipient;
                            Message msgValidator = new Message(messageID, i, "", "");
                            while (true) {
                                System.out.print("Enter recipient cell number (e.g., +27718693002): ");
                                recipient = scanner.nextLine();
                                System.out.println(msgValidator.getRecipientCellMessage(recipient));
                                if (msgValidator.checkRecipientCell(recipient)) {
                                    break;
                                }
                            }

                            // Message text input
                            String messageText;
                            while (true) {
                                System.out.print("Enter message text (max 250 characters): ");
                                messageText = scanner.nextLine();
                                String lengthCheck = msgValidator.checkMessageLength(messageText);
                                System.out.println(lengthCheck);
                                if (messageText.length() < 250) {
                                    break;
                                }
                            }

                            Message msg = new Message(messageID, i, recipient, messageText);

                            // Send / Disregard / Store options
                            System.out.println("Choose action for this message:");
                            System.out.println("1) Send Message");
                            System.out.println("2) Disregard Message");
                            System.out.println("3) Store Message");
                            System.out.print("Enter choice (1-3): ");
                            
                            int actionChoice = 1;
                            try {
                                actionChoice = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException ignored) {}

                            String actionResult = msg.SentMessage(actionChoice);
                            System.out.println(actionResult);

                            if (actionChoice == 1) {
                                totalMessagesSent++;
                            }

                            System.out.println("\n--- Message Summary ---");
                            System.out.println(msg.printMessageDetails());
                        }

                        System.out.println("\nTotal messages successfully processed/sent in this session: " + totalMessagesSent);
                        break;

                    case 2:
                        System.out.println("Coming Soon.");
                        break;

                    case 3:
                        System.out.println("Exiting QuickChat. Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please choose 1, 2, or 3.");
                }
            }
        } else {
            System.out.println("Authentication failed. Cannot access messaging features.");
        }

        scanner.close();
    }
}