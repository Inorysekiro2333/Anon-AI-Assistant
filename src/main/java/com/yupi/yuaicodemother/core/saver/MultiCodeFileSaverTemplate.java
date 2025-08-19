package com.yupi.yuaicodemother.core.saver;

import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.ai.model.MultiFileCodeResult;
import com.yupi.yuaicodemother.exception.BusinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;

/**
 * 多文件代码保存器
 *
 */
public class MultiCodeFileSaverTemplate extends CodeFileSaverTemplate<MultiFileCodeResult> {

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.MULTI_FILE;
    }

    @Override
    protected void saveFiles(MultiFileCodeResult result, String baseDirPath) {
        // 保存html文件
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
        // 保存css文件
        writeToFile(baseDirPath, "style.css", result.getCssCode());
        // 保存js文件
        writeToFile(baseDirPath, "script.js", result.getJsCode());
    }

    @Override
    protected void validateInput(MultiFileCodeResult result) {
        super.validateInput(result);
        // 至少要有HTML代码, CSS和js可以为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "HTML代码不能为空");
        }
    }
}
