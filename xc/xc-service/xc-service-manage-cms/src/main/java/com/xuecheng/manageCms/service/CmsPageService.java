package com.xuecheng.manageCms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

import java.util.List;

public interface CmsPageService {
    /**
     * 分页查询页面信息
     *
     * @param page             当前页
     * @param size             每页多少条数据
     * @param queryPageRequest 查询的条件
     * @return
     */
    public QueryResponseResult findList( int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 查询所有站点信息
     *
     * @return
     */
    public CmsSiteResult findCmsSite();

    /**
     * 添加cmsPage页面信息
     *
     * @param cmsPage 要添加的页面信息
     * @return
     */
    public CmsPageResult addCmsPage(CmsPage cmsPage);

    /**
     * 根据页面id查询页面信息
     *
     * @param id 页面id
     * @return
     */
    public CmsPageResult findCmsPageById(String id);

    /**
     * 修改页面信息
     *
     * @param id      页面id
     * @param cmsPage 要修改的页面信息
     * @return
     */
    public CmsPageResult editCmsPage(String id, CmsPage cmsPage);

    /**
     * 根据id删除页面信息
     *
     * @param id 页面id
     * @return
     */
    public void delCmsPageById(String id);

    /**
     * 查询所有页面模板信息
     *
     * @return
     */
    public CmsTemplateResult findCmsTemplates();

    public ResponseResult delCmsPageByIds(List<String> ids);
}
