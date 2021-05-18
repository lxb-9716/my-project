package com.xuecheng.manageCms.service.imp;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manageCms.dao.CmsTemplatesRepository;
import com.xuecheng.manageCms.service.CmsTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CmsTemplateServiceImp implements CmsTemplateService {
    @Autowired
    private CmsTemplatesRepository cmsTemplatesRepository;

    @Override
    public QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest) {
        if (null == queryTemplateRequest) {
            queryTemplateRequest = new QueryTemplateRequest();
        }
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("templateName", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsTemplate cmsTemplate = new CmsTemplate();
        if (StringUtils.isNotEmpty(queryTemplateRequest.getTemplateName())) {
            cmsTemplate.setTemplateName(queryTemplateRequest.getTemplateName());
        }
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        page = page - 1;
        Example<CmsTemplate> example = Example.of(cmsTemplate, exampleMatcher);
        Pageable pageable = new PageRequest(page, size);
        QueryResult<CmsTemplate> cmsTemplateQueryResult = new QueryResult<>();
        Page<CmsTemplate> all = cmsTemplatesRepository.findAll(example, pageable);
        if (null != all) {
            cmsTemplateQueryResult.setList(all.getContent());
            cmsTemplateQueryResult.setTotal(all.getTotalElements());
        }
        return new QueryResponseResult(CommonCode.SUCCESS, cmsTemplateQueryResult);
    }

    @Override
    public CmsTemplateResult addCmsTemplate(CmsTemplate cmsTemplate) {
        if (null == cmsTemplate) {
            cmsTemplate = new CmsTemplate();
        }
        //添加页面模板信息之前，先判断页面模板是否已经存在
        CmsTemplate newCmsTemplate = cmsTemplatesRepository.findBySiteIdAndTemplateNameAndTemplateFileId(cmsTemplate.getSiteId(), cmsTemplate.getTemplateName(), cmsTemplate.getTemplateFileId());
        if (null == newCmsTemplate) {
            //当前要添加的页面不存在
            cmsTemplate.setTemplateId(null);
            CmsTemplate save = cmsTemplatesRepository.save(cmsTemplate);
            if (null != save) {
                List<CmsTemplate> cmsTemplateList = new ArrayList<>();
                cmsTemplateList.add(save);
                return new CmsTemplateResult(CommonCode.SUCCESS, cmsTemplateList);
            }
        }
        return new CmsTemplateResult(CommonCode.FAIL, null);
    }

    @Override
    public CmsTemplateResult editCmsTemplate(String templateId, CmsTemplate cmsTemplate) {
        if (null == cmsTemplate) {
            cmsTemplate = new CmsTemplate();
        }
        //根据模板id查询要修改的模板是否存在
        Optional<CmsTemplate> editCmsTemplate = cmsTemplatesRepository.findById(templateId);
        if (editCmsTemplate.isPresent()) {
            CmsTemplate cmsTemplate1 = editCmsTemplate.get();
            if (cmsTemplate1 != null) {
                cmsTemplate1.setTemplateName(cmsTemplate.getTemplateName());
                cmsTemplate1.setTemplateFileId(cmsTemplate.getTemplateFileId());
                cmsTemplate1.setTemplateParameter(cmsTemplate.getTemplateParameter());
                cmsTemplate1.setSiteId(cmsTemplate.getSiteId());
                CmsTemplate save = cmsTemplatesRepository.save(cmsTemplate1);
                if (null != save) {
                    List<CmsTemplate> cmsTemplates = new ArrayList<>();
                    cmsTemplates.add(save);
                    return new CmsTemplateResult(CommonCode.SUCCESS, cmsTemplates);
                }
            }
        }
        return new CmsTemplateResult(CommonCode.FAIL, null);
    }

    @Override
    public CmsTemplateResult findByCmsTemplateId(String templateId) {
        Optional<CmsTemplate> optionalCmsTemplate = cmsTemplatesRepository.findById(templateId);
        if (optionalCmsTemplate.isPresent()) {
            CmsTemplate cmsTemplate = optionalCmsTemplate.get();
            if (null != cmsTemplate) {
                List<CmsTemplate> cmsTemplates = new ArrayList<>();
                cmsTemplates.add(cmsTemplate);
                return new CmsTemplateResult(CommonCode.SUCCESS, cmsTemplates);
            }
        }
        return new CmsTemplateResult(CommonCode.FAIL, null);
    }

    @Override
    public ResponseResult delCmsTemplateById(String id) {
        cmsTemplatesRepository.deleteById(id);
        ResponseResult responseResult = new ResponseResult(CommonCode.SUCCESS);
        return responseResult;
    }

    @Override
    public ResponseResult delCmsTemplateByIds(List<String> ids) {
        if (null != ids && ids.size() > 0) {
            for (String id : ids) {
                cmsTemplatesRepository.deleteById(id);
            }
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
