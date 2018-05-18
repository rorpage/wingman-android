package com.rorpage.wingman.util.formatters;

import android.test.suitebuilder.annotation.SmallTest;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessageType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
@SmallTest
public class WingmanMessageFormatterUnitTest {
    private WingmanMessage mWingmanMessage;
    private String mMessageMessage;
    private MessageType mMessageMessageType;
    private String mExpectedResult;

    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "Test", MessageType.GENERIC, "Test##GENERIC" },
                { "09:15 AM", MessageType.CLOCK, "09:15 AM##CLOCK" },
                { "August Burns Red\nBack Burner", MessageType.MUSIC, "August Burns Red\nBack Burner##MUSIC" },
                { null, MessageType.GENERIC, "N/A##GENERIC" },
                { "", MessageType.GENERIC, "##GENERIC" },
                { "Test", null, "Test##GENERIC" }
        });
    }

    public WingmanMessageFormatterUnitTest(String message, MessageType messageType, String expectedResult) {
        mMessageMessage = message;
        mMessageMessageType = messageType;
        mExpectedResult = expectedResult;
    }

    @Before
    public void setUp() {
        mWingmanMessage = new WingmanMessage();
        mWingmanMessage.Message = mMessageMessage;
        mWingmanMessage.Type = mMessageMessageType;
    }

    private String act() {
        return WingmanMessageFormatter.formatMessage(mWingmanMessage);
    }

    @Test
    public void formatMessage_should_format_properly() {
        final String formattedMessage = act();
        assertThat(formattedMessage, is(equalTo(mExpectedResult)));
    }

    @Test
    public void formatMessage_should_include_separator() {
        final String formattedMessage = act();
        assertThat(formattedMessage, containsString("##"));
    }
}
