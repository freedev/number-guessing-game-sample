package com.workshop.game;

public record GuessInputResult(ParseStatus status, Integer parsedNumber, String errorMessage) {
    public static GuessInputResult valid(int number) {
        return new GuessInputResult(ParseStatus.VALID, number, null);
    }

    public static GuessInputResult invalidNonNumeric(String message) {
        return new GuessInputResult(ParseStatus.INVALID_NON_NUMERIC, null, message);
    }

    public static GuessInputResult invalidOutOfRange(String message) {
        return new GuessInputResult(ParseStatus.INVALID_OUT_OF_RANGE, null, message);
    }

    public static GuessInputResult eof() {
        return new GuessInputResult(ParseStatus.EOF, null, null);
    }
}
