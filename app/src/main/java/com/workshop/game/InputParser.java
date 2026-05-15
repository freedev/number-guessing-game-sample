package com.workshop.game;

public class InputParser {
    private final String invalidNumberMessage;
    private final String outOfRangeMessage;
    private final String emptyLineMessage;

    public InputParser(String invalidNumberMessage, String outOfRangeMessage, String emptyLineMessage) {
        this.invalidNumberMessage = invalidNumberMessage;
        this.outOfRangeMessage = outOfRangeMessage;
        this.emptyLineMessage = emptyLineMessage;
    }

    public GuessInputResult parse(String rawInput) {
        if (rawInput == null) {
            return GuessInputResult.eof();
        }

        String trimmedInput = rawInput.trim();
        if (trimmedInput.isEmpty()) {
            return GuessInputResult.invalidNonNumeric(emptyLineMessage);
        }

        int number;
        try {
            number = Integer.parseInt(trimmedInput);
        } catch (NumberFormatException parseException) {
            return GuessInputResult.invalidNonNumeric(invalidNumberMessage);
        }

        if (number < 1 || number > 100) {
            return GuessInputResult.invalidOutOfRange(outOfRangeMessage);
        }

        return GuessInputResult.valid(number);
    }
}
