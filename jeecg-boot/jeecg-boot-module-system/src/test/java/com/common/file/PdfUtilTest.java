package com.common.file;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ding
 */
@RunWith(SpringRunner.class)
@Slf4j
public class PdfUtilTest {
    @Test
    public void testFillValues() throws IOException {
        // 测试填充模板
        String templatePath = "../deploy/template/AfterSale.pdf";
        Map<String, String> nameMap = new HashMap<String, String>() {{
            put("fill_26", "issue");
            put("fill_25", "depart");
            put("fill_2", "name");
            put("fill_3", "date");
            put("fill_6", "comment");
        }};

        String filePath = PdfUtil.fillValues(
                templatePath, null, nameMap,
                "../deploy/template/simhei.ttf", null, null, null,
                null, null
        );
        log.info(filePath);

        // 映射取值
        Map<String, String> valueMap = new HashMap<String, String>() {{
            put("issue", "SHFW20190002");
            put("depart", "量化管理部");
            put("name", "熊总1");
            put("date", "2019-03-04");
            put("comment", "验证名称0011");
        }};

        filePath = PdfUtil.fillValues(
                templatePath, null, nameMap, valueMap,
                "../deploy/template/simhei.ttf", null, new HashMap<String, Integer>() {{
                    put("name", 8);
                    put("fill_26", 8);
                }}, new HashMap<String, Integer>() {{
                    put("name", 24);
                    put("fill_26", 24);
                }},
                "../deploy/template/simfang.ttf", new ArrayList<String>() {{
                    add("name");
                }}
        );
        log.info(filePath);
        Assert.assertNotNull(filePath);

        // 异常代码覆盖测试
        Assert.assertNull(PdfUtil.fillValues(
                null, null, nameMap, "../deploy/template/simhei.ttf", null,
                null, null, null, null
        ));
    }

    @Test
    public void testTemplate() throws IOException {
        // template
        String templatePath = "../deploy/template/template_form.pdf";
        String filePath = "target/filled_template_form.pdf";

        // values
        Map<String, String> valueMap = new HashMap<String, String>() {{
            put("fill_1", "hello template");
            put("fill_2", String.format("雄安新区%s", new Date().toString()));
            put("Check Box2", "Yes");
            put("Check Box3", "Off");
            put("Group4.1", "Choice2");
            put("Group4.2", "Choice2");
            put("undefined_5", "On");
            put("undefined_6", "Off");
            put("undefined_7", "Off");
            put("undefined_8", "On");
            put("undefined_9", "On");
            put("undefined_10", "Off");
            put("undefined_11", "Off");
            put("undefined_12", "On");
            put("toggle_13", "On");
            put("toggle_14", "Off");
        }};

        // fill
        PdfFont font = PdfFontFactory.createFont("../deploy/template/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        PdfDocument pdf = new PdfDocument(new PdfReader(templatePath), new PdfWriter(filePath));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, false);
        if (form != null) {
            Map<String, PdfFormField> fieldMap = form.getFormFields();
            for (Map.Entry<String, PdfFormField> fieldEntry : fieldMap.entrySet()) {
                String name = fieldEntry.getKey();
                String value = valueMap.get(name);
                log.info(String.format("field name: %s, value: %s", name, value));

                PdfFormField field = fieldEntry.getValue();
                if (field != null && value != null) {
                    field.setFont(font);
                    field.setValue(valueMap.get(name));
                }
            }

            // 关闭模板的编辑状态
            form.flattenFields();
        }
        pdf.close();
    }

    @Test
    public void testHelloWorld() throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter("target/hello.pdf"));
        Document document = new Document(pdf);
        document.add(new Paragraph(String.format("Hello World from pdf util iText7! %s", new Date().toString())));
        document.close();
    }

    /**
     * https://blog.csdn.net/qq_37150258/article/details/81481806
     * 使用系统本地字体，可以解决生成的pdf中无法显示中文问题，本处字体为宋体
     * 可以直接把例如： C:\Windows\Fonts|ADOBESONGSTD-LIGHT.OTF字体文件拷贝到项目中
     * 在创建字体时直接使用即可解决中文问题
     */
    @Test
    public void testChineseFont() throws IOException {
        PdfFont font = PdfFontFactory.createFont("../deploy/template/simfang.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        PdfDocument pdf = new PdfDocument(new PdfWriter("target/chinese.pdf"));
        Document document = new Document(pdf);
        document.add(
                new Paragraph(
                        String.format("Hello World 你好，世界 \nfrom pdf util iText7! %s", new Date().toString())
                ).setFont(font)
        );
        document.close();
    }
}
