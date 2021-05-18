package com.xuecheng.manageCms.service.imp;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manageCms.dao.CmsPageRepository;
import com.xuecheng.manageCms.dao.CmsSiteRepository;
import com.xuecheng.manageCms.dao.CmsTemplatesRepository;
import com.xuecheng.manageCms.service.CmsPageService;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CmsPageServiceImp implements CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Autowired
    private CmsTemplatesRepository cmsTemplatesRepository;

    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        //设置条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                //页面别名模糊查询
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                //页面名称模糊查询
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());
        if (null == queryPageRequest) {
            queryPageRequest = new QueryPageRequest();
        }
        CmsPage cmsPage = new CmsPage();
        //根据站点id精确匹配查询
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //根据模板id精确匹配查询
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //根据页面别名模糊查询
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //根据页面名称模糊查询
        if (StringUtils.isNotEmpty(queryPageRequest.getPageName())) {
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        //根据页面类型精确查询("0"-静态  "1"-动态)
        if (StringUtils.isNotEmpty(queryPageRequest.getPageType())) {
            cmsPage.setPageType(queryPageRequest.getPageType());
        }
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        page = page - 1;
        Pageable pageable = new PageRequest(page, size);
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        if (null != all) {
            queryResult.setTotal(all.getTotalElements());
            queryResult.setList(all.getContent());
        }
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    @Override
    public CmsSiteResult findCmsSite() {
        List<CmsSite> all = cmsSiteRepository.findAll();
        CmsSiteResult cmsSiteResult = new CmsSiteResult(CommonCode.SUCCESS, all);
        return cmsSiteResult;
    }

    /**
     * 添加cmsPage页面信息
     *
     * @param cmsPage 要添加的页面信息
     * @return
     */
    @Override
    public CmsPageResult addCmsPage(CmsPage cmsPage) {
        if (cmsPage == null) {
            cmsPage = new CmsPage();
        }
        //查询要添加的页面是否已经存在
        CmsPage newCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (newCmsPage == null) {
            //主键id自动生成
            cmsPage.setPageId(null);
            //要添加的页面不存在可以添加该页面
            cmsPage = cmsPageRepository.save(cmsPage);
            CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);
            return cmsPageResult;
        }
        //要添加的新页面已经存在
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    @Override
    public CmsPageResult findCmsPageById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    @Override
    public CmsPageResult editCmsPage(String id, CmsPage cmsPage) {
        if (null == cmsPage) {
            cmsPage = new CmsPage();
        }
        //根据id查询要修改的cmsPage页面是否存在
        CmsPageResult cmsPageResult = findCmsPageById(id);
        if (null != cmsPageResult) {
            CmsPage editCmsPage = cmsPageResult.getCmsPage();
            if (null != editCmsPage) {
                //更新模板
                editCmsPage.setTemplateId(cmsPage.getTemplateId());
                //更新所属站点
                editCmsPage.setSiteId(cmsPage.getSiteId());
                //更新页面名称
                editCmsPage.setPageName(cmsPage.getPageName());
                //更新页面别名
                editCmsPage.setPageAliase(cmsPage.getPageAliase());
                //更新web访问路径
                editCmsPage.setPageWebPath(cmsPage.getPageWebPath());
                //更新物理访问路径
                editCmsPage.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
                //更新要修改的页面信息
                CmsPage save = cmsPageRepository.save(editCmsPage);
                if (null != save) {
                    return new CmsPageResult(CommonCode.SUCCESS, save);
                }
            }
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    @Override
    public void delCmsPageById(String id) {
        cmsPageRepository.deleteById(id);
    }

    @Override
    public CmsTemplateResult findCmsTemplates() {
        List<CmsTemplate> all = cmsTemplatesRepository.findAll();
        CmsTemplateResult cmsTempatesResult = new CmsTemplateResult(CommonCode.SUCCESS, all);
        return cmsTempatesResult;
    }

    @Override
    public ResponseResult delCmsPageByIds(List<String> ids) {
        if (null != ids && ids.size() > 0) {
            for (String id : ids) {
                cmsPageRepository.deleteById(id);
            }
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
