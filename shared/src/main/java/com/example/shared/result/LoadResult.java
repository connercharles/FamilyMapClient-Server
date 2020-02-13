package com.example.shared.result;

/**
 * response body for load call
 */
public class LoadResult extends Result {

    /**
     * creates LoadResult
     * @param message new message
     */
    public LoadResult(String message) {
        super(message);
    }

    public LoadResult() {
        super(null);
    }

    public void setMessage(int users, int persons, int events){
        String message = String.format("Successfully added %s users, " +
                "%s persons, and %s events to the database", users, persons, events);
        super.setMessage(message);
    }
}
