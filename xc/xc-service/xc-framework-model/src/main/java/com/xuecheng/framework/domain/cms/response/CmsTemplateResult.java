package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;

import java.util.List;

@Data
public class CmsTemplateResult extends ResponseResult {
    private List<CmsTemplate> cmsTemplates;

    public CmsTemplateResult(ResultCode resultCode, List<CmsTemplate> cmsTemplates) {
        super(resultCode);
        this.cmsTemplates = cmsTemplates;
    }
}
