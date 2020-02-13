package com.example.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.shared.request.FillRequest;
import com.example.shared.result.FillResult;
import com.example.server.service.FillService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class FillServiceTest extends ServiceTest {

    @BeforeEach
    public void setUp() throws Exception {
        setUpDB();
    }

    @AfterEach
    public void tearDown() throws Exception {
        tearDownDB();
    }

    @Test
    public void fillPass() {
        FillRequest fillRequest = new FillRequest();
        fillRequest.setUserName(user1.getUserName());
        FillResult fillResult = new FillResult();
        fillResult.setMessage(31, 76);

        FillService fillService = new FillService();
        FillResult result = fillService.fill(fillRequest);

        assertEquals(fillResult.getMessage(), result.getMessage());
    }

    @Test
    public void fillFail() {
        FillRequest fillRequest = new FillRequest();
        fillRequest.setUserName(user1.getUserName());
        FillResult fillResult = new FillResult();
        fillResult.setMessage(1, 1);

        FillService fillService = new FillService();
        FillResult result = fillService.fill(fillRequest);

        assertNotEquals(fillResult.getMessage(), result.getMessage());
    }


}