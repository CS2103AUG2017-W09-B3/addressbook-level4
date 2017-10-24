package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;


import org.junit.Test;
import seedu.address.logic.commands.*;

public class HelpCommandParserTest {
    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_success() {
        assertParseSuccess(parser, AddCommand.COMMAND_WORD, new HelpCommand("add"));

        assertParseSuccess(parser, AddCommand.COMMAND_WORD_2, new HelpCommand("add"));

        assertParseSuccess(parser, AddCommand.COMMAND_WORD_3, new HelpCommand("add"));

        assertParseSuccess(parser, ClearCommand.COMMAND_WORD, new HelpCommand("clear"));

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD, new HelpCommand("delete"));

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD_2, new HelpCommand("delete"));

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD_3, new HelpCommand("delete"));

        assertParseSuccess(parser, EditCommand.COMMAND_WORD, new HelpCommand("edit"));

        assertParseSuccess(parser, EditCommand.COMMAND_WORD_2, new HelpCommand("edit"));

        assertParseSuccess(parser, EditCommand.COMMAND_WORD_3, new HelpCommand("edit"));

        assertParseSuccess(parser, ExitCommand.COMMAND_WORD, new HelpCommand("exit"));

        assertParseSuccess(parser, FindCommand.COMMAND_WORD, new HelpCommand("find"));

        assertParseSuccess(parser, FindCommand.COMMAND_WORD_2, new HelpCommand("find"));

        assertParseSuccess(parser, FindCommand.COMMAND_WORD_3, new HelpCommand("find"));

        assertParseSuccess(parser, HistoryCommand.COMMAND_WORD, new HelpCommand("history"));

        assertParseSuccess(parser, HistoryCommand.COMMAND_WORD_2, new HelpCommand("history"));

        assertParseSuccess(parser, ListCommand.COMMAND_WORD, new HelpCommand("list"));

        assertParseSuccess(parser, ListCommand.COMMAND_WORD_2, new HelpCommand("list"));

        assertParseSuccess(parser, ListCommand.COMMAND_WORD_3, new HelpCommand("list"));

        assertParseSuccess(parser, RedoCommand.COMMAND_WORD, new HelpCommand("redo"));

        assertParseSuccess(parser, SelectCommand.COMMAND_WORD, new HelpCommand("select"));

        assertParseSuccess(parser, SelectCommand.COMMAND_WORD_2, new HelpCommand("select"));

        assertParseSuccess(parser, SortCommand.COMMAND_WORD, new HelpCommand("sort"));

        assertParseSuccess(parser, TagAddCommand.COMMAND_WORD, new HelpCommand("tagadd"));

        assertParseSuccess(parser, TagRemoveCommand.COMMAND_WORD, new HelpCommand("tagremove"));

        assertParseSuccess(parser, UndoCommand.COMMAND_WORD, new HelpCommand("undo"));

    }

}