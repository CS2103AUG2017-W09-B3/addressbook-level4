package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagRemoveCommand;
import seedu.address.logic.commands.UndoCommand;

import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    public HelpCommand parse(String args) throws ParseException {
        String input = args.toLowerCase().trim();

        if (input.equals(AddCommand.COMMAND_WORD) || input.equals(AddCommand.COMMAND_WORD_2)
            || input.equals(AddCommand.COMMAND_WORD_3)) {
            return new HelpCommand("add");
        } else if (input.equals(ClearCommand.COMMAND_WORD)) {
            return new HelpCommand("clear");
        } else if (input.equals(DeleteCommand.COMMAND_WORD) || input.equals(DeleteCommand.COMMAND_WORD_2)
                || input.equals(DeleteCommand.COMMAND_WORD_3)) {
            return new HelpCommand("delete");
        } else if (input.equals(EditCommand.COMMAND_WORD) || input.equals(EditCommand.COMMAND_WORD_2)
                || input.equals(EditCommand.COMMAND_WORD_3)) {
            return new HelpCommand("edit");
        } else if (input.equals(ExitCommand.COMMAND_WORD)) {
            return new HelpCommand("exit");
        } else if (input.equals(FindCommand.COMMAND_WORD) || input.equals(FindCommand.COMMAND_WORD_2)
                || input.equals(FindCommand.COMMAND_WORD_3)) {
            return new HelpCommand("find");
        } else if (input.equals(HistoryCommand.COMMAND_WORD) || input.equals(HistoryCommand.COMMAND_WORD_2)) {
            return new HelpCommand("history");
        } else if (input.equals(ListCommand.COMMAND_WORD) || input.equals(ListCommand.COMMAND_WORD_2)
                || input.equals(ListCommand.COMMAND_WORD_3)) {
            return new HelpCommand("list");
        } else if (input.equals(RedoCommand.COMMAND_WORD)) {
            return new HelpCommand("redo");
        } else if (input.equals(SelectCommand.COMMAND_WORD) || input.equals(SelectCommand.COMMAND_WORD_2)) {
            return new HelpCommand("select");
        } else if (input.equals(SortCommand.COMMAND_WORD)) {
            return new HelpCommand("sort");
        } else if (input.equals(TagAddCommand.COMMAND_WORD)) {
            return new HelpCommand("tagadd");
        } else if (input.equals(TagRemoveCommand.COMMAND_WORD)) {
            return new HelpCommand("tagremove");
        } else if (input.equals(UndoCommand.COMMAND_WORD)) {
            return new HelpCommand("undo");
        } else {
            return new HelpCommand();
        }

    }
}
