package com.workshop.game;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class App {
    private static final String PROMPT = "Enter your guess (1-100):";
    private static final String TOO_LOW = "Too low!";
    private static final String TOO_HIGH = "Too high!";
    private static final String WIN_MESSAGE = "Correct! You guessed it in %d attempts.";
    private static final String INVALID_NUMBER = "Input is not a valid number.";
    private static final String RANGE_ERROR = "Number must be between 1 and 100.";
    private static final String EMPTY_ERROR = "Please enter a number between 1 and 100.";
    private static final String GOODBYE = "Goodbye!";

    private final InputParser inputParser;
    private final GuessEvaluator guessEvaluator;
    private final SecretNumberGenerator secretNumberGenerator;
    private final PrintStream output;

    public App() {
        this(
            new InputParser(INVALID_NUMBER, RANGE_ERROR, EMPTY_ERROR),
            new GuessEvaluator(),
            () -> ThreadLocalRandom.current().nextInt(1, 101),
            System.out
        );
    }

    App(
        InputParser inputParser,
        GuessEvaluator guessEvaluator,
        SecretNumberGenerator secretNumberGenerator,
        PrintStream output
    ) {
        this.inputParser = inputParser;
        this.guessEvaluator = guessEvaluator;
        this.secretNumberGenerator = secretNumberGenerator;
        this.output = output;
    }

    SessionStatus run(Scanner scanner) {
        output.println("Welcome to the Number Guessing Game!");
        GameSession session = new GameSession(secretNumberGenerator.generate());
        output.println(PROMPT);

        while (session.isInProgress()) {
            if (!scanner.hasNextLine()) {
                output.println(GOODBYE);
                session.markTerminatedEof();
                break;
            }

            GuessInputResult parsedInput = inputParser.parse(scanner.nextLine());
            if (parsedInput.status() != ParseStatus.VALID) {
                output.println(parsedInput.errorMessage());
                output.println(PROMPT);
                continue;
            }

            GuessOutcomeType guessOutcome = session.submitValidGuess(parsedInput.parsedNumber(), guessEvaluator);
            if (guessOutcome == GuessOutcomeType.TOO_LOW) {
                output.println(TOO_LOW);
                output.println(PROMPT);
                continue;
            }

            if (guessOutcome == GuessOutcomeType.TOO_HIGH) {
                output.println(TOO_HIGH);
                output.println(PROMPT);
                continue;
            }

            output.println(String.format(WIN_MESSAGE, session.validAttemptCount()));
        }

        return session.status();
    }

    public static void main(String[] args) {
        new App().run(new Scanner(System.in));
    }
}
