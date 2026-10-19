/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

/**
 *
 * @author Student
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipientCell;
    private String messageText;
    private String messageHash;
    private String actionStatus;

    // Constructor
    public Message(String messageID, int messageNumber, String recipientCell, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageHash = createMessageHash(messageID, messageNumber, messageText);
    }

    // 1. Check message ID length (must not exceed 10 characters)
    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    // 2. Check message length (must be less than 250 characters)
    public String checkMessageLength(String message) {
        if (message.length() < 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    // 3. Check recipient cell phone number (reusing Part 1 international format logic)
    public boolean checkRecipientCell(String cell) {
        return Pattern.matches("^\\+[0-9]{9,11}$", cell);
    }

    public String getRecipientCellMessage(String cell) {
        if (checkRecipientCell(cell)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // 4. Create Message Hash: First 2 chars of ID : message number : First and Last word in uppercase
    public String createMessageHash(String id, int num, String text) {
        if (id == null || id.length() < 2 || text == null || text.trim().isEmpty()) {
            return "";
        }
        String prefix = id.substring(0, 2);
        String[] words = text.trim().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 0 ? words[words.length - 1].toUpperCase() : "";

        return prefix + ":" + num + ":" + firstWord + lastWord;
    }

    // 5. Handle user selection for sending, disregarding, or storing the message
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                this.actionStatus = "Sent";
                return "Message successfully sent.";
            case 2:
                this.actionStatus = "Disregarded";
                return "Press O to delete the message.";
            case 3:
                this.actionStatus = "Stored";
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid selection.";
        }
    }

    // 6. Store message in a JSON file (Attributed to standard Java file IO handling)
    // Research reference for JSON file writing in Java: Oracle Java Documentation on FileWriter.
    public void storeMessage() {
        String jsonContent = "{\n" +
                "  \"MessageID\": \"" + messageID + "\",\n" +
                "  \"MessageHash\": \"" + messageHash + "\",\n" +
                "  \"Recipient\": \"" + recipientCell + "\",\n" +
                "  \"Message\": \"" + messageText + "\",\n" +
                "  \"Status\": \"" + actionStatus + "\"\n" +
                "}";

        try (FileWriter file = new FileWriter("message_" + messageID + ".json", true)) {
            file.write(jsonContent + "\n");
        } catch (IOException e) {
            System.out.println("Error storing message to JSON: " + e.getMessage());
        }
    }

    // 7. Print full message details
    public String printMessageDetails() {
        return "Message ID: " + messageID + "\n" +
                "Message Hash: " + messageHash + "\n" +
                "Recipient: " + recipientCell + "\n" +
                "Message: " + messageText + "\n" +
                "Status: " + actionStatus;
    }

    // Getters
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
}
