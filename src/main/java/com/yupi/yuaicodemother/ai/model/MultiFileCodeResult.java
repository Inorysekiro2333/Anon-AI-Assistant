package com.yupi.yuaicodemother.ai.model;

import jdk.jfr.Description;
import lombok.Data;

/**
 * AI 多文件结构化输出结果
 */
@Description("生成多文件代码的结果")
@Data
public class MultiFileCodeResult {

    /**
     * HTML代码
     */
    @Description("HTML代码")
    private String htmlCode;

    /**
     * CSS代码
     */
    @Description("CSS代码")
    private String cssCode;

    /**
     * JS代码
     */
    @Description("JavaScript代码")
    private String jsCode;

    /**
     * 描述
     */
    @Description("生成代码的描述")
    private String description;
}
