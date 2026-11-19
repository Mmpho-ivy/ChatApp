/*
 * Reference:
 * Oracle Corporation. (2025). Java Platform, Standard Edition API Specification. 
 * Available at: https://docs.oracle.com/en/java/javase/21/docs/api/ [Accessed: November 2026].
 */
package chatapp;

import java.util.Scanner;

public class ChatApp {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();
            
            System.out.println("=============================");
            System.out.println("========== ChatApp ==========");
            System.out.println("=============================");
            
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
            
            System.out.println("=============================");
            System.out.println("========== Login ============");
            System.out.println("=============================");
            System.out.print("Enter username: ");
            String loginUser = scanner.nextLine();
            System.out.print("Enter password: ");
            String loginPass = scanner.nextLine();
            
            boolean loginSuccess = login.loginUser(loginUser, loginPass);
            System.out.println(login.returnLoginStatus(loginSuccess, login.getStoredFirstName(), login.getStoredLastName()));
            
            if (loginSuccess) {
                System.out.println("\nWelcome to QuickChat.");
                boolean running = true;
                int totalMessagesSent = 0;
                
                while (running) {
                    System.out.println("\n--- QuickChat Main Menu ---");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Stored Messages & Reports (Array Operations)");
                    System.out.println("4) Quit");
                    System.out.print("Choose an option (1-4): ");
                    
                    String choiceStr = scanner.nextLine();
                    int choice;
                    try {
                        choice = Integer.parseInt(choiceStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter 1 to 4.");
                        continue;
                    }
                    
                    switch (choice) {
                        case 1:
                            System.out.print("How many messages would you like to enter? ");
                            int numMessages;
                            try {
                                numMessages = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number.");
                                break;
                            }
                            
                            for (int i = 0; i < numMessages; i++) {
                                System.out.println("\n--- Entering Message " + (i + 1) + " of " + numMessages + " ---");
                                
                                Message validator = new Message("1234567890", i, "", "");
                                String recipient;
                                while (true) {
                                    System.out.print("Enter recipient cell number (e.g., +27718693002): ");
                                    recipient = scanner.nextLine();
                                    System.out.println(validator.getRecipientCellMessage(recipient));
                                    if (validator.checkRecipientCell(recipient)) {
                                        break;
                                    }
                                }
                                
                                String messageText;
                                while (true) {
                                    System.out.print("Enter message text (max 250 characters): ");
                                    messageText = scanner.nextLine();
                                    String lenCheck = validator.checkMessageLength(messageText);
                                    System.out.println(lenCheck);
                                    if (messageText.length() <= 250) {
                                        break;
                                    }
                                }
                                
                                System.out.println("Choose action:");
                                System.out.println("1) Send Message");
                                System.out.println("2) Disregard Message");
                                System.out.println("3) Store Message in Json file");
                                System.out.print("Enter choice (1-3): ");
                                
                                int actionChoice = 1;
                                try {
                                    actionChoice = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException ignored) {}
                                
                                String status = "Sent";
                                if (actionChoice == 2) status = "Disregarded";
                                else if (actionChoice == 3) status = "Stored";
                                
                                Message msg = new Message("1234567890", i, recipient, messageText, status);
                                String actionResult = msg.SentMessage(actionChoice);
                                System.out.println(actionResult);
                                
                                // Display details ONLY for sent messages (Lecturer feedback correction)
                                if (actionChoice == 1) {
                                    totalMessagesSent++;
                                    System.out.println("\n--- Sent Message Details ---");
                                    System.out.println(msg.printMessageDetails());
                                }
                            }
                            System.out.println("\nTotal messages sent: " + totalMessagesSent);
                            break;
                            
                        case 2:
                            System.out.println("Coming Soon.");
                            break;
                            
                        case 3:
                            boolean subRunning = true;
                            while (subRunning) {
                                System.out.println("\n--- Stored Messages & Reports Menu ---");
                                System.out.println("a) Display sender/recipient of stored messages");
                                System.out.println("b) Display longest message");
                                System.out.println("c) Search message by ID");
                                System.out.println("d) Search messages by recipient");
                                System.out.println("e) Delete message by hash");
                                System.out.println("f) Display full message report");
                                System.out.println("g) Return to Main Menu");
                                System.out.print("Choose option (a-g): ");
                                
                                String subChoice = scanner.nextLine().trim().toLowerCase();
                                switch (subChoice) {
                                    case "a":
                                        System.out.println(Message.displayStoredSenderAndRecipient());
                                        break;
                                    case "b":
                                        System.out.println("Longest Message:\n" + Message.getLongestMessage());
                                        break;
                                    case "c":
                                        System.out.print("Enter Message ID to search: ");
                                        System.out.println(Message.searchByMessageID(scanner.nextLine()));
                                        break;
                                    case "d":
                                        System.out.print("Enter Recipient cell to search: ");
                                        System.out.println(Message.searchByRecipient(scanner.nextLine()));
                                        break;
                                    case "e":
                                        System.out.print("Enter Message Hash to delete: ");
                                        System.out.println(Message.deleteByMessageHash(scanner.nextLine()));
                                        break;
                                    case "f":
                                        System.out.println(Message.displayMessageReport());
                                        break;
                                    case "g":
                                        subRunning = false;
                                        break;
                                    default:
                                        System.out.println("Invalid option.");
                                }
                            }
                            break;
                            
                        case 4:
                            System.out.println("Exiting QuickChat. Goodbye!");
                            running = false;
                            break;
                            
                        default:
                            System.out.println("Invalid option.");
                    }
                }
            } else {
                System.out.println("Authentication failed.");
            }
        }
    }
}