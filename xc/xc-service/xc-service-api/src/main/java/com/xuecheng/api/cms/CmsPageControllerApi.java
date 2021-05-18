package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value = "cms页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //根据站点id,页面别名，模板id查询cmsPage页面信息
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"), @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")})
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    //查询所有站点的相关信息
    @ApiOperation("页面所属站点列表")
    public CmsSiteResult findCmsSite();

    //添加cms页面
    @ApiOperation("添加页面信息")
    public CmsPageResult addCmsPage(CmsPage cmsPage);

    //根据页面id查询cmsPage页面信息
    @ApiOperation("根据页面id查询页面信息")
    @ApiImplicitParam(name = "id", value = "页面id", required = true, paramType = "path", dataType = "string")
    public CmsPageResult findCmsPageById(String id);

    //修改页面
    @ApiOperation("修改页面信息")
    public CmsPageResult editCmsPage(String id, CmsPage cmsPage);
    //根据页面id删除页面信息
    @ApiOperation("删除页面信息")
    public ResponseResult delCmsPageById(String id);
    //根据页面id进行批量删除
    @ApiOperation("批量删除页面信息")
    public ResponseResult delCmsPageByIds(List<String> ids);

    //查询所有页面模板的相关信息
    @ApiOperation("页面模板列表")
    public CmsTemplateResult findCmsTemplates();

}
