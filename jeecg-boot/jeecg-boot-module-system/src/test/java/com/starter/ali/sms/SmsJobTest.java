package com.starter.ali.sms;

import com.starter.ali.sms.service.IApiCallSmsService;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dingxl
 * @date 3/11/2021 10:43 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JeecgApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SmsJobTest {
    @Test
    public void testTask() {
        // mock & stub
        IApiCallSmsService smsService = Mockito.mock(IApiCallSmsService.class);
        Mockito.when(smsService.remove(Mockito.any())).thenReturn(true);

        // test
        SmsJob job = new SmsJob(smsService);
        job.task();

        // verify
        Mockito.verify(smsService, Mockito.atLeastOnce()).remove(Mockito.any());
    }
}
