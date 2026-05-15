package com.workshop.game;

public class GuessEvaluator {
    public GuessOutcomeType evaluate(int guessNumber, int secretNumber) {
        if (guessNumber < secretNumber) {
            return GuessOutcomeType.TOO_LOW;
        }

        if (guessNumber > secretNumber) {
            return GuessOutcomeType.TOO_HIGH;
        }

        return GuessOutcomeType.CORRECT;
    }
}
