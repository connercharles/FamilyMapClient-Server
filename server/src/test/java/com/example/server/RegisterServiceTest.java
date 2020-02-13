package com.example.server;

import com.example.server.dao.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.shared.request.RegisterRequest;
import com.example.shared.result.RegisterResult;
import com.example.server.service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;


public class RegisterServiceTest extends ServiceTest {

    @BeforeEach
    public void setUp() throws Exception {
        setUpDB();
    }

    @AfterEach
    public void tearDown() throws Exception {
        tearDownDB();
    }

    @Test
    public void registerPass() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(userNew.getEmail());
        registerRequest.setPassword(userNew.getPassword());
        registerRequest.setUserName(userNew.getUserName());
        registerRequest.setFirstName(userNew.getFirstName());
        registerRequest.setLastName(userNew.getLastName());
        registerRequest.setGender(userNew.getGender());

        RegisterResult loginResult = new RegisterResult();
        loginResult.setUserName(userNew.getUserName());

        RegisterService registerService = new RegisterService();
        RegisterResult result = registerService.register(registerRequest);

        assertEquals(loginResult.getUserName(), result.getUserName());
        assertNotNull(result.getAuthToken());
        assertNotNull(result.getPersonID());
    }

    @Test
    public void registerFail() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(user1.getEmail());
        registerRequest.setPassword(user1.getPassword());
        registerRequest.setUserName(user1.getUserName());
        registerRequest.setFirstName(user1.getFirstName());
        registerRequest.setLastName(user1.getLastName());
        registerRequest.setGender(user1.getGender());

        RegisterResult loginResult = new RegisterResult();
        loginResult.setUserName(user1.getUserName());

        RegisterService registerService = new RegisterService();
        RegisterResult result = registerService.register(registerRequest);

        assertNotEquals(loginResult.getUserName(), result.getUserName());
    }


}
