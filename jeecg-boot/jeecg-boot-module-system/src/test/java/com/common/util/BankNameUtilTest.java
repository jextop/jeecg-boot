package com.common.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dingxl
 * @date 3/6/2021 8:22 AM
 */
@RunWith(SpringRunner.class)
public class BankNameUtilTest {
    @Test
    public void testGetName() {
        Assert.assertNull(BankNameUtil.getName(null));
        Assert.assertNull(BankNameUtil.getName("no-bank"));
        Assert.assertEquals("中国工商银行", BankNameUtil.getName("icbc"));
        Assert.assertEquals("中国工商银行", BankNameUtil.getName("ICBC"));
    }
}
