package com.runyuanj.stream;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StreamApplication.class)
@WebAppConfiguration
@DirtiesContext
public class StreamApplicationTest {

   /* @Autowired
    private Sink sink;

    @Test
    public void contextLoads() {
        Assert.notNull(this.sink.input());
    }*/
}
