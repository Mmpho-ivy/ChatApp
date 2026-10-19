/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package chatapp;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

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
    public void testRecipientCellFailure() {
        Message msg = new Message("1234567890", 0, "08966553", "Test");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.getRecipientCellMessage("08966553"));
    }

    @Test
    public void testCreateMessageHash() {
        // Test Case 1 data: ID starting with "00", message number 0, text "Hi Mike, can you join us for dinner tonight?"
        // Note: first word "HI", last word "TONIGHT" -> 00:0:HITONIGHT
        Message msg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", msg.getMessageHash());
    }

    @Test
    public void testMessageIDValidation() {
        Message msg = new Message("1234567890", 0, "+27718693002", "Test");
        assertTrue(msg.checkMessageID(msg.getMessageID()));
    }

    @Test
    public void testSentMessageActions() {
        Message msg = new Message("1234567890", 0, "+27718693002", "Test");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
        assertEquals("Press O to delete the message.", msg.SentMessage(2));
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }
}