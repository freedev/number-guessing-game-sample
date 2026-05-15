package com.workshop.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InputParserTest {
    private final InputParser inputParser = new InputParser(
        "Input is not a valid number.",
        "Number must be between 1 and 100.",
        "Please enter a number between 1 and 100."
    );

    @Test
    void parse_validInput_returnsValidStatusAndParsedNumber() {
        GuessInputResult result = inputParser.parse("42");

        assertEquals(ParseStatus.VALID, result.status());
        assertEquals(42, result.parsedNumber());
    }

    @Test
    void parse_nonNumericInput_returnsInvalidNonNumericStatus() {
        GuessInputResult result = inputParser.parse("abc");

        assertEquals(ParseStatus.INVALID_NON_NUMERIC, result.status());
        assertEquals("Input is not a valid number.", result.errorMessage());
    }

    @Test
    void parse_decimalInput_returnsInvalidNonNumericStatus() {
        GuessInputResult result = inputParser.parse("3.5");

        assertEquals(ParseStatus.INVALID_NON_NUMERIC, result.status());
        assertEquals("Input is not a valid number.", result.errorMessage());
    }

    @Test
    void parse_emptyInput_returnsInvalidNonNumericStatusWithEmptyLineMessage() {
        GuessInputResult result = inputParser.parse("   ");

        assertEquals(ParseStatus.INVALID_NON_NUMERIC, result.status());
        assertEquals("Please enter a number between 1 and 100.", result.errorMessage());
    }

    @Test
    void parse_outOfRangeInput_returnsInvalidOutOfRangeStatus() {
        GuessInputResult lowResult = inputParser.parse("0");
        GuessInputResult highResult = inputParser.parse("101");

        assertEquals(ParseStatus.INVALID_OUT_OF_RANGE, lowResult.status());
        assertEquals(ParseStatus.INVALID_OUT_OF_RANGE, highResult.status());
        assertEquals("Number must be between 1 and 100.", lowResult.errorMessage());
        assertEquals("Number must be between 1 and 100.", highResult.errorMessage());
    }

    @Test
    void parse_nullInput_returnsEofStatus() {
        GuessInputResult result = inputParser.parse(null);

        assertEquals(ParseStatus.EOF, result.status());
    }
}
