package com.workshop.game;

public class GameSession {
    private final int secretNumber;
    private int validAttemptCount;
    private SessionStatus status;

    public GameSession(int secretNumber) {
        if (secretNumber < 1 || secretNumber > 100) {
            throw new IllegalArgumentException("Secret number must be between 1 and 100.");
        }
        this.secretNumber = secretNumber;
        this.validAttemptCount = 0;
        this.status = SessionStatus.IN_PROGRESS;
    }

    public GuessOutcomeType submitValidGuess(int guessNumber, GuessEvaluator guessEvaluator) {
        if (guessNumber < 1 || guessNumber > 100) {
            throw new IllegalArgumentException("Guess must be between 1 and 100.");
        }

        validAttemptCount += 1;
        GuessOutcomeType guessOutcome = guessEvaluator.evaluate(guessNumber, secretNumber);
        if (guessOutcome == GuessOutcomeType.CORRECT) {
            status = SessionStatus.WON;
        }
        return guessOutcome;
    }

    public void markTerminatedEof() {
        if (status == SessionStatus.IN_PROGRESS) {
            status = SessionStatus.TERMINATED_EOF;
        }
    }

    public boolean isInProgress() {
        return status == SessionStatus.IN_PROGRESS;
    }

    public int validAttemptCount() {
        return validAttemptCount;
    }

    public SessionStatus status() {
        return status;
    }
}
