package com.example.shared.result;

public class Result {
    public Result(String message) {
        this.message = message;
    }

    /**
     * message for call
     */
    private String message;

    public Result() {

    }

    /**
     * gets message
     * @return string message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets messsage
     * @param message new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
