package com.xuecheng.manageCms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
    public CmsSite findBySiteDomainAndSitePortAndSiteName(String siteDomain, String sitePort, String siteName);
}
