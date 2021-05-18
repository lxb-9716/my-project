package com.xuecheng.manageCms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manageCms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {

    @Autowired
    private CmsTemplateService cmsTemplateService;

    @Override
    @GetMapping("/find/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryTemplateRequest queryTemplateRequest) {
        QueryResponseResult queryResponseResult = cmsTemplateService.findList(page, size, queryTemplateRequest);
        return queryResponseResult;
    }

    @Override
    @PostMapping("/add")
    public CmsTemplateResult addCmsTemplate(@RequestBody CmsTemplate cmsTemplate) {
        CmsTemplateResult cmsTemplateResult = cmsTemplateService.addCmsTemplate(cmsTemplate);
        return cmsTemplateResult;
    }

    @Override
    @PutMapping("/edit/{templateId}")
    public CmsTemplateResult editCmsTemplate(@PathVariable("templateId") String templateId, @RequestBody CmsTemplate cmsTemplate) {
        CmsTemplateResult cmsTemplateResult = cmsTemplateService.editCmsTemplate(templateId, cmsTemplate);
        return cmsTemplateResult;
    }

    @Override
    @GetMapping("/find/{templateId}")
    public CmsTemplateResult findByCmsTemplateId(@PathVariable("templateId") String templateId) {
        CmsTemplateResult cmsTemplateResult = cmsTemplateService.findByCmsTemplateId(templateId);
        return cmsTemplateResult;
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delCmsTemplateById(@PathVariable("id") String id) {
        return cmsTemplateService.delCmsTemplateById(id);
    }

    @Override
    @PostMapping("/batch/delete")
    public ResponseResult delCmsTemplateByIds(@RequestBody List<String> ids) {
        return cmsTemplateService.delCmsTemplateByIds(ids);
    }

}
