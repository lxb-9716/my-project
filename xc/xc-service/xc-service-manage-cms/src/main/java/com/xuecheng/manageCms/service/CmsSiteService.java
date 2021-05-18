package com.xuecheng.manageCms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

public interface CmsSiteService {

    //查询页面站点信息
    public QueryResponseResult findList(int page, int size, QuerySiteRequest querySiteRequest);

    //新增页面站点信息
    public CmsSiteResult addCmsSite(CmsSite cmsSite);

    //修改页面站点信息
    public CmsSiteResult editCmsSite(String siteId, CmsSite cmsSite);

    //根据站点id查询站点信息
    public CmsSiteResult findByCmsSiteId(String siteId);

    //根据id删除站点信息
    public ResponseResult delCmsSiteById(String id);

    //根据站点id进行批量删除
    public ResponseResult delCmsSiteByIds(List<String> ids);
}
