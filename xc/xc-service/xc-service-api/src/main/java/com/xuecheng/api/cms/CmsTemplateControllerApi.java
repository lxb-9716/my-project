package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value = "cms页面模板管理接口", description = "cms页面模板管理接口，提供页面模板的增、删、改、查")
public interface CmsTemplateControllerApi {
    //查询页面模板信息
    @ApiOperation("分页查询页面模板列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"), @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")})
    public QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest);


    //新增页面模板信息
    @ApiOperation("新增页面模板")
    public CmsTemplateResult addCmsTemplate(CmsTemplate cmsTemplate);

    //修改页面模板信息
    @ApiOperation("修改页面模板")
    public CmsTemplateResult editCmsTemplate(String templateId, CmsTemplate cmsTemplate);

    //根据模板id查询模板信息
    @ApiOperation("根据模板id查询模板信息")
    public CmsTemplateResult findByCmsTemplateId(String templateId);

    //根据id删除模板信息
    @ApiOperation("根据id删除模板信息")
    public ResponseResult delCmsTemplateById(String id);

    //根据页面id进行批量删除
    @ApiOperation("批量删除模板信息")
    public ResponseResult delCmsTemplateByIds(List<String> ids);

}
