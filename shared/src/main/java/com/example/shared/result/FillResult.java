package com.example.shared.result;

/**
 * Response body for fill call
 */
public class FillResult extends Result {

    /**
     * creates FillResult
     * @param message new message
     */
    public FillResult(String message) {
        super(message);
    }

    public FillResult() {
        super(null);
    }

    public void setMessage(int persons, int events){
        String message = String.format("Successfully added " +
                "%s persons and %s events to the database", persons, events);
        super.setMessage(message);
    }
}
