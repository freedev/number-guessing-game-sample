package com.workshop.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GameSessionTest {
    @Test
    void constructor_secretOutsideRange_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new GameSession(0));
    }

    @Test
    void submitValidGuess_nonWinningGuess_incrementsAttemptsAndRemainsInProgress() {
        GameSession gameSession = new GameSession(50);

        GuessOutcomeType outcome = gameSession.submitValidGuess(10, new GuessEvaluator());

        assertEquals(GuessOutcomeType.TOO_LOW, outcome);
        assertEquals(1, gameSession.validAttemptCount());
        assertEquals(SessionStatus.IN_PROGRESS, gameSession.status());
    }

    @Test
    void submitValidGuess_correctGuess_marksSessionWonAndIncrementsAttempts() {
        GameSession gameSession = new GameSession(50);

        GuessOutcomeType outcome = gameSession.submitValidGuess(50, new GuessEvaluator());

        assertEquals(GuessOutcomeType.CORRECT, outcome);
        assertEquals(1, gameSession.validAttemptCount());
        assertEquals(SessionStatus.WON, gameSession.status());
    }

    @Test
    void markTerminatedEof_inProgressSession_setsTerminatedEofStatus() {
        GameSession gameSession = new GameSession(25);

        gameSession.markTerminatedEof();

        assertEquals(SessionStatus.TERMINATED_EOF, gameSession.status());
    }
}
