package com.rorpage.wingman.models.messages;

public class WingmanMessage {
    public MessageType Type;
    public String Message;
    public MessagePriority Priority;

    public WingmanMessage(MessageType type, String message, MessagePriority priority) {
        this.Type = type;
        this.Message = message;
        this.Priority = priority;
    }

    public WingmanMessage() {
        Type = MessageType.GENERIC;
        Priority = MessagePriority.LOW;
    }
}