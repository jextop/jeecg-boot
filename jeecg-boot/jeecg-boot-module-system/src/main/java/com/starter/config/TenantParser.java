package com.starter.config;

import com.starter.auth.helper.UserInfoHelper;
import com.starter.auth.model.UserInfo;
import com.starter.auth.util.UserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import org.jeecg.config.mybatis.JeecgTenantParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dingxl
 * @date 2/22/2021 12:03 PM
 */
public class TenantParser extends JeecgTenantParser {
    final private UserInfoHelper userInfoHelper;

    @Autowired
    public TenantParser(UserInfoHelper userInfoHelper) {
        this.userInfoHelper = userInfoHelper;
    }

    @Override
    protected void processPlainSelect(PlainSelect plainSelect, boolean addColumn) {
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;

            // 判断是否需要绕过多租户：超级管理员，平台管理员，admin
            UserInfo userInfo = userInfoHelper.getUserInfo();
            boolean ignoreTenantId = UserUtil.isAdmin(userInfo);

            // 拼接查询条件限制
            if (!ignoreTenantId && !this.getTenantHandler().doTableFilter(fromTable.getName())) {
                plainSelect.setWhere(builderExpression(plainSelect.getWhere(), fromTable));
                if (addColumn) {
                    plainSelect.getSelectItems().add(
                            new SelectExpressionItem(new Column(this.getTenantHandler().getTenantIdColumn()))
                    );
                }
            }
        } else {
            processFromItem(fromItem);
        }

        List<Join> joins = plainSelect.getJoins();
        if (joins != null && joins.size() > 0) {
            joins.forEach(j -> {
                processJoin(j);
                processFromItem(j.getRightItem());
            });
        }
    }
}
