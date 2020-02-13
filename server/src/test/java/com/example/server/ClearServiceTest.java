package com.example.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.shared.result.ClearResult;
import com.example.server.service.ClearService;

import static org.junit.jupiter.api.Assertions.*;


public class ClearServiceTest extends ServiceTest {

    @BeforeEach
    public void setUp() throws Exception {
        setUpDB();
    }

    @AfterEach
    public void tearDown() throws Exception {
        tearDownDB();
    }

    @Test
    public void clearPass() throws Exception {
        ClearResult compareResult = new ClearResult();
        compareResult.setMessage("Clear succeeded.");

        ClearService clearService = new ClearService();
        ClearResult result = clearService.clear();

        assertEquals(compareResult.getMessage(), result.getMessage());
    }

    @Test
    public void clearFail() throws Exception {
        ClearResult compareResult = new ClearResult();

        ClearService clearService = new ClearService();
        ClearResult result = clearService.clear();

        assertNull(compareResult.getMessage());
        assertNotEquals(compareResult.getMessage(), result.getMessage());
    }


}
