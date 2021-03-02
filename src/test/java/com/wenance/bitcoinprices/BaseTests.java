package com.wenance.bitcoinprices;

import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseTests {

    @Before
    public void setup() {
        openMocks(this);
    }
}
