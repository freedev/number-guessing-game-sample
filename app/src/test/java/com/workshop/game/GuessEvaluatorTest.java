package com.workshop.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GuessEvaluatorTest {
    private final GuessEvaluator guessEvaluator = new GuessEvaluator();

    @Test
    void evaluate_guessLowerThanSecret_returnsTooLow() {
        assertEquals(GuessOutcomeType.TOO_LOW, guessEvaluator.evaluate(10, 50));
    }

    @Test
    void evaluate_guessHigherThanSecret_returnsTooHigh() {
        assertEquals(GuessOutcomeType.TOO_HIGH, guessEvaluator.evaluate(90, 50));
    }

    @Test
    void evaluate_guessMatchesSecret_returnsCorrect() {
        assertEquals(GuessOutcomeType.CORRECT, guessEvaluator.evaluate(50, 50));
    }
}
