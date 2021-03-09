package com.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dingxl
 * @date 3/9/2021 9:03 AM
 */
@RunWith(SpringRunner.class)
@Slf4j
public class LogTest {
    @Test
    public void testLog() {
        log.debug("Debug，调试信息，辅助程序员开发完善功能");
        log.info("Info，提示信息，系统正常运行过程中的状态、操作和结果等");
        log.warn("Warning，表示警告，需要关注，有可能引发错误");
        log.error("Error，表示错误，比如异常数据，通知运营或者运维及时处理");
    }
}
