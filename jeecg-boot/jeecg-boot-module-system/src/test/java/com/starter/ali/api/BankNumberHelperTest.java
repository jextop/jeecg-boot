package com.starter.ali.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@Slf4j
public class BankNumberHelperTest {
    @Test
    public void testIsValid() throws IOException {
        Map<String, Boolean> map = new HashMap<String, Boolean>() {{
            put("6222031100223285899", true);
            put("6222030as3285899", false);
            put("", false);
        }};

        for (Map.Entry<String, Boolean> io : map.entrySet()) {
            BankNumberModel ret = BankNumberHelper.isValid(io.getKey());
            log.info(ret.toJSONString());
            Assert.assertEquals(io.getValue(), ret.isValid());
            Assert.assertEquals(io.getValue(), StringUtils.isNotEmpty(ret.getBankCode()));
            Assert.assertEquals(io.getValue(), StringUtils.isNotEmpty(ret.getBankName()));
        }
    }
}
