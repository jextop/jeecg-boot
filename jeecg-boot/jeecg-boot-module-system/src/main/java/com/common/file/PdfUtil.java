package com.common.file;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ding
 */
@Slf4j
public class PdfUtil {
    public static String fillValues(
            String templatePath, String filePath, Map<String, String> nameMap,
            String fontPath, Integer fontSize, Map<String, Integer> sizeMap, Map<String, Integer> lenMap,
            String fontBoldPath, List<String> boldList
    ) throws IOException {
        // 映射填充内容取值
        Map<String, String> valueMap = new HashMap<String, String>();
        nameMap.values().forEach(value -> valueMap.put(value, value));

        return fillValues(
                templatePath, filePath, nameMap, valueMap,
                fontPath, fontSize, sizeMap, lenMap, fontBoldPath, boldList
        );
    }

    public static String fillValues(
            String templatePath, String filePath, Map<String, String> nameMap, Map<String, String> valueMap,
            String fontPath, Integer fontSize, Map<String, Integer> sizeMap, Map<String, Integer> lenMap,
            String fontBoldPath, List<String> boldList
    ) throws IOException {
        if (templatePath == null || valueMap == null || valueMap.size() <= 0) {
            return null;
        }

        // copy a new file and save
        if (StringUtils.isEmpty(filePath)) {
            filePath = String.format("target/filled_%s", FilenameUtils.getName(templatePath));
        }

        PdfDocument pdf = new PdfDocument(new PdfReader(templatePath), new PdfWriter(filePath));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, false);

        // 加载字体
        PdfFont font = null, fontBold = null;
        if (StringUtils.isNotEmpty(fontPath)) {
            font = PdfFontFactory.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        }

        if (StringUtils.isNotEmpty(fontBoldPath)) {
            fontBold = PdfFontFactory.createFont(fontBoldPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        }

        // 填充内容
        Map<String, PdfFormField> fieldMap = form.getFormFields();
        for (Map.Entry<String, PdfFormField> fieldEntry : fieldMap.entrySet()) {
            String key = fieldEntry.getKey();
            String name = nameMap != null && nameMap.containsKey(key) ? nameMap.get(key) : key;
            String value = valueMap.get(name);
            log.info(String.format("field: %s, name: %s, value: %s", key, name, value));

            PdfFormField field = fieldEntry.getValue();
            if (field != null && value != null) {
                // Set font
                if (fontBold != null && boldList.contains(name)) {
                    field.setFont(fontBold);
                } else if (font != null) {
                    field.setFont(font);
                }

                // Set font size
                Integer size = fontSize;
                if (sizeMap != null) {
                    if (sizeMap.containsKey(key)) {
                        size = sizeMap.get(key);
                    } else if (sizeMap.containsKey(name)) {
                        size = sizeMap.get(name);
                    }
                }

                if (!value.contains("\n") && size != null) {
                    // Check the specified length
                    Integer len = 255;
                    if (lenMap != null) {
                        if (lenMap.containsKey(key)) {
                            len = lenMap.get(key);
                        } else if (lenMap.containsKey(name)) {
                            len = lenMap.get(name);
                        }
                    }

                    if (value.length() <= len) {
                        field.setFontSize(size);
                    }
                }

                // Set value
                field.setValue(valueMap.get(name));
            }
        }

        // 关闭模板编辑状态
        form.flattenFields();

        pdf.close();
        return filePath;
    }
}
