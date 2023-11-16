package com.message.same_process;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InitiatorTest {
    private Initiator initiator;
    private MessageService receiver;

    @BeforeEach
    void setUp() {
        initiator = new Initiator();
        receiver = mock(MessageService.class); // Mocking the Player interface
    }

    @Test
    void whenReceiveMessage_thenLastReceivedMessageIsUpdated() {
        String testMessage = "Test Message";
        initiator.receiveMessage(testMessage);
        assertEquals(testMessage, initiator.getLastReceivedMessage());
    }

    @Test
    void whenSendMessage_thenReceiverReceivesMessageWithIncrementedCount() {
        initiator.sendMessage(receiver, "Test");
        verify(receiver).receiveMessage("Test1");
        initiator.sendMessage(receiver, "Test");
        verify(receiver).receiveMessage("Test2");
    }
}


