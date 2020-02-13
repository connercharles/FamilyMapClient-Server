package com.example.shared.request;

/**
 * Request body for fill call
 */
public class FillRequest {
    private final int DEFAULT_GEN = 4;

    /**
     * unique username for person
     */
    private String userName;
    /**
     * number of generations to be filled
     */
    private int generations;

    /**
     * creates FillRequest with full values
     * @param userName new username
     * @param generations new generations
     */
    public FillRequest(String userName, int generations) {
        this.userName = userName;
        this.generations = generations;
    }

    public FillRequest() {
        this.generations = DEFAULT_GEN;
    }

    /**
     * gets username
     * @return string username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets username
     * @param userName new username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * gets generations
     * @return int generations
     */
    public int getGenerations() {
        return generations;
    }

    /**
     * sets generations
     * @param generations new generations
     */
    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
