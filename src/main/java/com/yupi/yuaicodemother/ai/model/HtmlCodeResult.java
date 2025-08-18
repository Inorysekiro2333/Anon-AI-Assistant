package com.yupi.yuaicodemother.ai.model;

import jdk.jfr.Description;
import lombok.Data;

/**
 * AI html结构化输出结果
 */
@Description("生成HTML代码的结果")
@Data
public class HtmlCodeResult {

    /**
     * HTML 代码
     */
    @Description("HTML代码")
    private String htmlCode;

    /**
     * 描述性输出
     */
    @Description("生成代码的描述")
    private String description;
}
