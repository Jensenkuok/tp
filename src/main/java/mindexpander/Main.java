package mindexpander;

import mindexpander.commands.CommandResult;
import mindexpander.parser.Parser;

import mindexpander.data.QuestionBank;
import mindexpander.exceptions.IllegalCommandException;
import mindexpander.ui.TextUi;

import mindexpander.commands.Command;

public class Main {
    // Attributes
    private static QuestionBank questionBank;
    private TextUi ui;

    // Constructor
    public static void main(String[] args) {
        new Main().run();
        questionBank = new QuestionBank();
    }

    // Methods
    // Run MindExpander
    public void run() {
        start();
        runUserCommandUntilTermination();
    }

    // Start function: Instantiates the TextUI class
    private void start() {
        try {
            this.ui = new TextUi();
        } catch (Exception e) {
            ui.printInitFailedMessage();
        }
    }

    // Runs the program until user enters 'exit'
    private void runUserCommandUntilTermination() {
        // New command variable
        Command command;
        // Temporary state for exit, before adding in command class
        boolean isRunning = true;

        do {
            String userCommand = ui.getUserCommand();

            try {
                command = new Parser().parseCommand(userCommand);

                CommandResult commandResult = command.execute();
                ui.displayResults(commandResult);

                isRunning = command.keepProgramRunning();
            } catch (IllegalCommandException e) {
                ui.printToUser(e.getMessage());
            }

        } while (isRunning);
    }
}
