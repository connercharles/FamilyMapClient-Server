package com.example.shared.result;

/**
 * response body for clear call
 */
public class ClearResult extends Result {

    /**
     * creates ClearResult with filled values
     * @param message new message
     */
    public ClearResult(String message) {
        super(message);
    }

    /**
     * creates ClearResult with nothing
     */
    public ClearResult() {
        super(null);
    }
}
