package com.xuecheng.manageCms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CmsTemplateService {
    /**
     * 分页查询模板页面信息
     *
     * @param page                 当前页
     * @param size                 每页多少条数据
     * @param queryTemplateRequest 查询的条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest);

    /**
     * 添加页面模板
     *
     * @param cmsTemplate
     * @return
     */
    public CmsTemplateResult addCmsTemplate(CmsTemplate cmsTemplate);

    /**
     * 修改页面模板信息
     *
     * @param templateId
     * @param cmsTemplate
     * @return
     */
    public CmsTemplateResult editCmsTemplate(String templateId, CmsTemplate cmsTemplate);

    /**
     * 根据模板id查询页面信息
     *
     * @param templateId
     * @return
     */
    public CmsTemplateResult findByCmsTemplateId(String templateId);

    /**
     * 根据id删除模板信息
     *
     * @param id
     * @return
     */
    public ResponseResult delCmsTemplateById(String id);

    /**
     * 根据多个模板id批量删除模板信息
     *
     * @param ids
     * @return
     */
    public ResponseResult delCmsTemplateByIds(List<String> ids);

}
