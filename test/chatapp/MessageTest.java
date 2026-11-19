package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Before
    public void setUpTestData() {
        // PoE Part 3 Test Data Initialization
        new Message("1111111111", 0, "+27834557896", "Did you get the cake?", "Sent");
        new Message("2222222222", 1, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        new Message("3333333333", 2, "+27834484567", "Yohoooo, I am at your gate.", "Disregarded");
        new Message("0838884567", 3, "0838884567", "It is dinner time!", "Sent");
        new Message("5555555555", 4, "+27838884567", "Ok, I am leaving without you.", "Stored");
    }

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("1234567890", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength(msg.getMessageText()));
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = "A".repeat(260);
        Message msg = new Message("1234567890", 0, "+27718693002", longMsg);
        assertTrue(msg.checkMessageLength(longMsg).contains("Message exceeds 250 characters"));
    }

    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message("1234567890", 0, "+27718693002", "Test");
        assertEquals("Cell phone number successfully captured.", msg.getRecipientCellMessage("+27718693002"));
    }

    @Test
    public void testRecipientCellFailureSpecificNumber() {
        // Lecturer feedback test number
        Message msg = new Message("1234567890", 0, "08575975889", "Test");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.getRecipientCellMessage("08575975889"));
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", msg.getMessageHash());
    }

    @Test
    public void testMessageHashLoop() {
        String[] testMessages = {"Hi Mike, can you join us for dinner tonight?", "Hi Keegan, did you receive the payment?"};
        for (int i = 0; i < testMessages.length; i++) {
            Message msg = new Message("1234567890", i, "+27718693002", testMessages[i]);
            assertNotNull(msg.getMessageHash());
        }
    }

    @Test
    public void testDisplayLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", Message.getLongestMessage());
    }

    @Test
    public void testSearchByMessageID() {
        String result = Message.searchByMessageID("0838884567");
        assertTrue(result.contains("It is dinner time!"));
    }

    @Test
    public void testDeleteByMessageHash() {
        String deleteResult = Message.deleteByMessageHash("22:1:WHERETIME");
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time\" successfully deleted.", deleteResult);
    }
}