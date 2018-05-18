package com.rorpage.wingman.models.messages;

public class SmsWingmanMessage extends WingmanMessage {
    public SmsWingmanMessage(String fromDisplay, String body) {
        super(MessageType.MESSAGE,
                String.format("%s\n%s", fromDisplay, body),
                MessagePriority.HIGH);
    }
}
