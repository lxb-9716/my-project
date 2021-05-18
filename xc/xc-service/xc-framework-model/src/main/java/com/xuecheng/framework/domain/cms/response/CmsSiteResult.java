package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;

import java.util.List;

/**
 * 页面所属站点相关信息
 */
@Data
public class CmsSiteResult extends ResponseResult {
    List<CmsSite> cmsSites;

    public CmsSiteResult(ResultCode resultCode, List<CmsSite> cmsSites) {
        super(resultCode);
        this.cmsSites = cmsSites;
    }
}
