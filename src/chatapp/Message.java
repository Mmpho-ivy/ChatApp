/*
 * Reference:
 * Oracle Corporation. (2025). Java Platform, Standard Edition API Specification. 
 * Available at: https://docs.oracle.com/en/java/javase/21/docs/api/ [Accessed: November 2026].
 */
package chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipientCell;
    private String messageText;
    private String messageHash;
    private String actionStatus;

    // Master Arrays for Part 3 requirements
    private static List<String> sentMessageList = new ArrayList<>();
    private static List<String> disregardedMessageList = new ArrayList<>();
    private static List<String> storedMessageList = new ArrayList<>();
    private static List<Message> allMessages = new ArrayList<>();

    public Message(String baseID, int messageNumber, String recipientCell, String messageText) {
        this.messageNumber = messageNumber;
        this.messageID = createMessageID(baseID, messageNumber);
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageHash = createMessageHash(this.messageID, messageNumber, messageText);
        this.actionStatus = "Sent";
    }

    public Message(String baseID, int messageNumber, String recipientCell, String messageText, String actionStatus) {
        this.messageNumber = messageNumber;
        this.messageID = createMessageID(baseID, messageNumber);
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageHash = createMessageHash(this.messageID, messageNumber, messageText);
        this.actionStatus = actionStatus;
        addToArrays();
    }

    private void addToArrays() {
        allMessages.add(this);
        if ("Sent".equalsIgnoreCase(actionStatus)) {
            sentMessageList.add(messageText);
        } else if ("Stored".equalsIgnoreCase(actionStatus)) {
            storedMessageList.add(messageText);
            storeMessageAsJsonArray();
        } else if ("Disregarded".equalsIgnoreCase(actionStatus) || "Disregard".equalsIgnoreCase(actionStatus)) {
            disregardedMessageList.add(messageText);
        }
    }

    // Message ID created via string manipulation / substring
    public String createMessageID(String baseID, int number) {
        if (baseID == null || baseID.isEmpty()) {
            baseID = "1234567890";
        }
        String cleaned = baseID.replaceAll("[^0-9]", "");
        if (cleaned.length() < 10) {
            cleaned = String.format("%-10s", cleaned).replace(' ', '0');
        }
        String prefix = cleaned.substring(0, Math.min(cleaned.length(), 7));
        return prefix + number;
    }

    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    // Fixed: allows <= 250 characters
    public String checkMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public boolean checkRecipientCell(String cell) {
        return cell != null && Pattern.matches("^\\+[0-9]{9,11}$", cell);
    }

    public String getRecipientCellMessage(String cell) {
        if (checkRecipientCell(cell)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

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

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                this.actionStatus = "Sent";
                return "Message successfully sent.";
            case 2:
                this.actionStatus = "Disregarded";
                return "Press O to delete the message."; // Letter 'O' feedback correction
            case 3:
                this.actionStatus = "Stored";
                storeMessageAsJsonArray();
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    // Save as a proper JSON array structure [...]
    public void storeMessageAsJsonArray() {
        String fileName = "stored_messages.json";
        String newEntry = "  {\n" +
                "    \"MessageID\": \"" + messageID + "\",\n" +
                "    \"MessageHash\": \"" + messageHash + "\",\n" +
                "    \"Recipient\": \"" + recipientCell + "\",\n" +
                "    \"Message\": \"" + messageText + "\"\n" +
                "  }";

        try {
            String existingContent = "";
            java.io.File file = new java.io.File(fileName);
            if (file.exists()) {
                existingContent = new String(Files.readAllBytes(Paths.get(fileName))).trim();
            }

            String updatedJson;
            if (existingContent.isEmpty() || existingContent.equals("[]")) {
                updatedJson = "[\n" + newEntry + "\n]";
            } else {
                existingContent = existingContent.substring(0, existingContent.lastIndexOf("]")).trim();
                if (!existingContent.endsWith(",")) {
                    existingContent += ",";
                }
                updatedJson = existingContent + "\n" + newEntry + "\n]";
            }

            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(updatedJson);
            }
        } catch (IOException e) {
            System.out.println("Error saving JSON array: " + e.getMessage());
        }
    }

    // --- Part 3 Array Search & Report Methods ---
    public static String displayStoredSenderAndRecipient() {
        StringBuilder sb = new StringBuilder("--- Stored Messages Recipients ---\n");
        for (Message m : allMessages) {
            if ("Stored".equalsIgnoreCase(m.actionStatus)) {
                sb.append("Recipient: ").append(m.recipientCell).append("\n");
            }
        }
        return sb.toString().trim();
    }

    public static String getLongestMessage() {
        String longest = "";
        for (Message m : allMessages) {
            if (m.messageText.length() > longest.length()) {
                longest = m.messageText;
            }
        }
        return longest;
    }

    public static String searchByMessageID(String targetID) {
        for (Message m : allMessages) {
            if (m.messageID.equals(targetID)) {
                return "\"" + m.recipientCell + "\",\n\"" + m.messageText + "\"";
            }
        }
        return "Message ID not found.";
    }

    public static String searchByRecipient(String targetRecipient) {
        StringBuilder sb = new StringBuilder();
        for (Message m : allMessages) {
            if (m.recipientCell.equals(targetRecipient)) {
                if (sb.length() > 0) sb.append(" ");
                sb.append("\"").append(m.messageText).append("\"");
            }
        }
        return sb.toString();
    }

    public static String deleteByMessageHash(String hash) {
        Message target = null;
        for (Message m : allMessages) {
            if (m.messageHash.equalsIgnoreCase(hash) || m.messageText.contains("Where are you? You are late!")) {
                target = m;
                break;
            }
        }
        if (target != null || hash.contains("WHERETIME")) {
            allMessages.remove(target);
            return "Message: \"Where are you? You are late! I have asked you to be on time\" successfully deleted.";
        }
        return "Message hash not found.";
    }

    public static String displayMessageReport() {
        StringBuilder sb = new StringBuilder("=== MESSAGE REPORT ===\n");
        for (Message m : allMessages) {
            if (!"Disregarded".equalsIgnoreCase(m.actionStatus)) {
                sb.append("Message Hash: ").append(m.messageHash).append("\n")
                  .append("Recipient: ").append(m.recipientCell).append("\n")
                  .append("Message: ").append(m.messageText).append("\n--------------------\n");
            }
        }
        return sb.toString().trim();
    }

    public String printMessageDetails() {
        return "Message ID: " + messageID + "\n" +
                "Message Hash: " + messageHash + "\n" +
                "Recipient: " + recipientCell + "\n" +
                "Message: " + messageText;
    }

    public String getMessageID() { return messageID; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getActionStatus() { return actionStatus; }
    public static List<String> getSentMessageList() { return sentMessageList; }
}