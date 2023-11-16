package com.message.same_process;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ReceiverTest {
    private Receiver receiver;
    private MessageService initiator;

    @BeforeEach
    void setUp() {
        receiver = new Receiver();
        initiator = mock(MessageService.class); // Mocking the Player interface
    }

    @Test
    void whenReceiveMessage_thenLastReceivedMessageIsUpdated() {
        String testMessage = "Test Message";
        receiver.receiveMessage(testMessage);
        assertEquals(testMessage, receiver.getLastReceivedMessage());
    }

    @Test
    void whenSendMessage_thenInitiatorReceivesMessageWithIncrementedCount() {
        receiver.sendMessage(initiator, "Test");
        verify(initiator).receiveMessage("Test1");
        receiver.sendMessage(initiator, "Test");
        verify(initiator).receiveMessage("Test2");
    }
}

