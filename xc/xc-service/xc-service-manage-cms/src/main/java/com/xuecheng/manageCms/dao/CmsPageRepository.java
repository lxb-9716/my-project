package com.xuecheng.manageCms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    /**
     * 根据页面站点id.页面名称，页面的web path查询页面信息(这三个信息可以用来确认cmsPage页面是否唯一)
     *
     * @param pageName    页面名称
     * @param siteId      站点id
     * @param pageWebPath 页面的web路径
     * @return
     */
    public CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
