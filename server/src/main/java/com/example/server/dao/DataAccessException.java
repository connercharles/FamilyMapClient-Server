package com.example.server.dao;

/**
 * Created by westenm on 2/4/19.
 */
public class DataAccessException extends Exception {
    /**
     * creates the exection with a passed in message string
     * @param message string to display
     */
    public DataAccessException(String message)
    {
        super(message);
    }

    /**
     * creates standard exception
     */
    DataAccessException()
    {
        super();
    }
}
