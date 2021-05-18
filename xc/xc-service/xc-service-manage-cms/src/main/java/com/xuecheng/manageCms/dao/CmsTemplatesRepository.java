package com.xuecheng.manageCms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsTemplatesRepository extends MongoRepository<CmsTemplate, String> {
    public CmsTemplate findBySiteIdAndTemplateNameAndTemplateFileId(String siteId, String templateName, String templateFileId);
}
