package com.common.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dingxl
 * @date 3/6/2021 4:18 PM
 */
@RunWith(SpringRunner.class)
public class YesNoTest {
    @Test
    public void testYes() {
        Assert.assertFalse(YesNo.isYes(null));
        Assert.assertFalse(YesNo.isYes(0));
        Assert.assertTrue(YesNo.isYes(1));
    }
}
