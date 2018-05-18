package com.rorpage.wingman.util.formatters;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessageType;

public class WingmanMessageFormatter {
    public static String formatMessage(WingmanMessage wingmanMessage) {
        final String messageMessage = wingmanMessage.Message == null ? "N/A" : wingmanMessage.Message;
        final MessageType messageType = wingmanMessage.Type == null ? MessageType.GENERIC : wingmanMessage.Type;
        return String.format("%s##%s", messageMessage, messageType);
    }
}
