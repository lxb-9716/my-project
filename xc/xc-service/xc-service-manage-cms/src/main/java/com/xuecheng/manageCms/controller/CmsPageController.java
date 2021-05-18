package com.xuecheng.manageCms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manageCms.service.CmsPageService;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cms")
public class CmsPageController implements CmsPageControllerApi {


    @Autowired
    private CmsPageService cmsPageService;

    /**
     * CMS分页查询
     *
     * @param page             当前页
     * @param size             每页显示的数据
     * @param queryPageRequest 查询条件
     * @return
     */
    @Override
    @GetMapping("/page/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        QueryResponseResult queryResponseResult = cmsPageService.findList(page, size, queryPageRequest);
        return queryResponseResult;
    }

    /**
     * 查询站点列表信息
     *
     * @return
     */
    @Override
    @GetMapping("/site/list")
    public CmsSiteResult findCmsSite() {
        return cmsPageService.findCmsSite();
    }

    /**
     * 添加cmsPage页面信息
     *
     * @param cmsPage 要添加的页面信息
     * @return
     */
    @Override
    @PostMapping("/page/add")
    public CmsPageResult addCmsPage(@RequestBody CmsPage cmsPage) {
        return cmsPageService.addCmsPage(cmsPage);
    }

    /**
     * 根据id查询页面信息
     *
     * @param id
     * @return
     */
    @Override
    @GetMapping("/page/find/{id}")
    public CmsPageResult findCmsPageById(@PathVariable("id") String id) {
        return cmsPageService.findCmsPageById(id);
    }

    /**
     * 修改页面信息
     *
     * @param id      页面id
     * @param cmsPage 要修改的页面信息
     * @return
     */
    @Override
    @PutMapping("/page/edit/{id}")
    public CmsPageResult editCmsPage(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return cmsPageService.editCmsPage(id, cmsPage);
    }

    /**
     * 根据id删除页面信息
     *
     * @param id 页面id
     * @return
     */
    @Override
    @DeleteMapping("/page/delete/{id}")
    public ResponseResult delCmsPageById(@PathVariable("id") String id) {
        cmsPageService.delCmsPageById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 批量删除页面信息
     *
     * @param ids 页面id集合
     * @return
     */
    @Override
    @PostMapping("/page/batch/delete")
    public ResponseResult delCmsPageByIds(@RequestBody List<String> ids) {
        return cmsPageService.delCmsPageByIds(ids);
    }

    /**
     * 查询所有页面模板信息
     *
     * @return
     */
    @Override
    @GetMapping("/templates/list")
    public CmsTemplateResult findCmsTemplates() {
        return cmsPageService.findCmsTemplates();
    }

}