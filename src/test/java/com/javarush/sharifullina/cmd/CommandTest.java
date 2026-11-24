package com.javarush.sharifullina.cmd;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CommandTest {

    @Mock
    private HttpServletRequest request;

    private static class SimpleCommand implements Command {
    }

    private static class MultiWordCommandName implements Command {
    }

    @Test
    void getViewShouldConvertCamelCaseToKebabCase() {
        Command simple = new SimpleCommand();
        Command multiWord = new MultiWordCommandName();

        assertEquals("simple-command", simple.getView());
        assertEquals("multi-word-command-name", multiWord.getView());
    }

    @Test
    void defaultDoGetAndDoPostShouldReturnViewName() {
        Command command = new SimpleCommand();

        assertEquals("simple-command", command.doGet(request));
        assertEquals("simple-command", command.doPost(request));
    }
}
