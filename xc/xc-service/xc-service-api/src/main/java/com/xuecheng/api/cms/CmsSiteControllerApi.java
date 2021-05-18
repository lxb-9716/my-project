package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value = "cms页面站点管理接口", description = "cms页面站点管理接口，提供页面站点的增、删、改、查")
public interface CmsSiteControllerApi {
    //查询页面站点信息
    @ApiOperation("分页查询页面模板列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"), @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")})
    public QueryResponseResult findList(int page, int size, QuerySiteRequest querySiteRequest);

    //新增页面站点信息
    @ApiOperation("新增页面站点")
    public CmsSiteResult addCmsSite(CmsSite cmsSite);

    //修改页面站点信息
    @ApiOperation("修改页面站点")
    public CmsSiteResult editCmsSite(String siteId, CmsSite cmsSite);

    //根据站点id查询站点信息
    @ApiOperation("根据模板id查询站点信息")
    public CmsSiteResult findByCmsSiteId(String siteId);

    //根据id删除站点信息
    @ApiOperation("根据id删除站点信息")
    public ResponseResult delCmsSiteById(String id);

    //根据站点id进行批量删除
    @ApiOperation("批量删除站点信息")
    public ResponseResult delCmsSiteByIds(List<String> ids);
}
