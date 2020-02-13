package com.example.server;

import com.example.server.dao.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.shared.request.LoginRequest;
import com.example.shared.result.LoginResult;
import com.example.server.service.LoginService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class LoginServiceTest extends ServiceTest {

    @BeforeEach
    public void setUp() throws Exception {
        setUpDB();
    }

    @AfterEach
    public void tearDown() throws Exception {
        tearDownDB();
    }

    @Test
    public void loginPass() throws DataAccessException {
        LoginRequest loginRequest = new LoginRequest(user1.getUserName(), user1.getPassword());
        LoginResult loginResult = new LoginResult();
        loginResult.setPersonID(user1.getpersonID());
        loginResult.setUserName(user1.getUserName());
        loginResult.setAuthToken(authToken1.getAuthKey());

        LoginService loginService = new LoginService();
        LoginResult result = loginService.login(loginRequest);

        assertEquals(loginResult, result);
    }

    @Test
    public void loginFail() throws DataAccessException {
        LoginRequest loginRequest = new LoginRequest(user1.getUserName(), user1.getPassword());
        LoginResult loginResult = new LoginResult();
        loginResult.setPersonID(user2.getpersonID());
        loginResult.setUserName(user2.getUserName());
        loginResult.setAuthToken(authToken2.getAuthKey());

        LoginService loginService = new LoginService();
        LoginResult result = loginService.login(loginRequest);

        assertNotEquals(loginResult, result);
    }


}