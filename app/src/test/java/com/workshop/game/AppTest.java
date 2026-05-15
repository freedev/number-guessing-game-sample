package com.workshop.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    void run_validGuesses_printsHintsAndWinningAttemptCount() {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        App app = new App(
            new InputParser("Input is not a valid number.", "Number must be between 1 and 100.", "Please enter a number between 1 and 100."),
            new GuessEvaluator(),
            () -> 42,
            new PrintStream(outputBuffer)
        );

        SessionStatus status = app.run(new Scanner("10\n60\n42\n"));
        String output = outputBuffer.toString();

        assertEquals(SessionStatus.WON, status);
        assertTrue(output.contains("Too low!"));
        assertTrue(output.contains("Too high!"));
        assertTrue(output.contains("Correct! You guessed it in 3 attempts."));
    }

    @Test
    void run_invalidInputs_doNotIncreaseAttemptCount() {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        App app = new App(
            new InputParser("Input is not a valid number.", "Number must be between 1 and 100.", "Please enter a number between 1 and 100."),
            new GuessEvaluator(),
            () -> 50,
            new PrintStream(outputBuffer)
        );

        app.run(new Scanner("abc\n\n101\n50\n"));
        String output = outputBuffer.toString();

        assertTrue(output.contains("Input is not a valid number."));
        assertTrue(output.contains("Please enter a number between 1 and 100."));
        assertTrue(output.contains("Number must be between 1 and 100."));
        assertTrue(output.contains("Correct! You guessed it in 1 attempts."));
    }

    @Test
    void run_eofInput_printsGoodbyeAndTerminatesCleanly() {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        App app = new App(
            new InputParser("Input is not a valid number.", "Number must be between 1 and 100.", "Please enter a number between 1 and 100."),
            new GuessEvaluator(),
            () -> 75,
            new PrintStream(outputBuffer)
        );

        SessionStatus status = app.run(new Scanner(""));
        String output = outputBuffer.toString();

        assertEquals(SessionStatus.TERMINATED_EOF, status);
        assertTrue(output.contains("Goodbye!"));
    }
}
