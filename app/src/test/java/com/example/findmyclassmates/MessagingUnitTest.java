package com.example.findmyclassmates;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessagingUnitTest {

    private Message message;

    @Before
    public void setUp() {
        message = new Message("sender", "content");
    }

    @Test
    public void testMessageGettersAndSetters() {
        assertEquals("sender", message.getSender());
        assertEquals("content", message.getContent());

        message.setSender("newSender");
        message.setContent("newContent");

        assertEquals("newSender", message.getSender());
        assertEquals("newContent", message.getContent());
    }
}
