package com.xuecheng.manageCms.service.imp;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manageCms.dao.CmsSiteRepository;
import com.xuecheng.manageCms.service.CmsSiteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CmsSiteServiceImp implements CmsSiteService {

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Override
    public QueryResponseResult findList(int page, int size, QuerySiteRequest querySiteRequest) {
        if (null == querySiteRequest) {
            querySiteRequest = new QuerySiteRequest();
        }
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().
                //站点名称模糊查询
                        withMatcher("siteName", ExampleMatcher.GenericPropertyMatchers.contains()).
                //站点ip模糊查询
                        withMatcher("siteDomain", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsSite cmsSite = new CmsSite();
        //站点名称模糊查询
        if (StringUtils.isNotEmpty(querySiteRequest.getSiteName())) {
            cmsSite.setSiteName(querySiteRequest.getSiteName());
        }
        //站点ip模糊查询
        if (StringUtils.isNotEmpty(querySiteRequest.getSiteDomain())) {
            cmsSite.setSiteDomain(querySiteRequest.getSiteDomain());
        }
        //站点端口号精确查询
        if (StringUtils.isNotEmpty(querySiteRequest.getSitePort())) {
            cmsSite.setSitePort(querySiteRequest.getSitePort());
        }
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        page = page - 1;
        Example<CmsSite> example = Example.of(cmsSite, exampleMatcher);
        Pageable pageable = new PageRequest(page, size);
        QueryResult<CmsSite> cmsSiteQueryResult = new QueryResult<>();
        Page<CmsSite> all = cmsSiteRepository.findAll(example, pageable);
        if (null != all) {
            cmsSiteQueryResult.setList(all.getContent());
            cmsSiteQueryResult.setTotal(all.getTotalElements());
        }
        return new QueryResponseResult(CommonCode.SUCCESS, cmsSiteQueryResult);
    }

    @Override
    public CmsSiteResult addCmsSite(CmsSite cmsSite) {
        if (null == cmsSite) {
            cmsSite = new CmsSite();
        }
        //添加页面站点信息之前，先判断页面站点是否已经存在
        CmsSite newCmsSite = cmsSiteRepository.findBySiteDomainAndSitePortAndSiteName(cmsSite.getSiteDomain(), cmsSite.getSitePort(), cmsSite.getSiteName());
        if (null == newCmsSite) {
            //当前要添加的页面不存在
            cmsSite.setSiteId(null);
            CmsSite save = cmsSiteRepository.save(cmsSite);
            if (null != save) {
                List<CmsSite> cmsSites = new ArrayList<>();
                cmsSites.add(save);
                return new CmsSiteResult(CommonCode.SUCCESS, cmsSites);
            }
        }
        return new CmsSiteResult(CommonCode.FAIL, null);
    }

    @Override
    public CmsSiteResult editCmsSite(String siteId, CmsSite cmsSite) {
        if (null == cmsSite) {
            cmsSite = new CmsSite();
        }
        //根据站点id查询要修改的页面站点是否存在
        Optional<CmsSite> editCmsSite = cmsSiteRepository.findById(siteId);
        if (editCmsSite.isPresent()) {
            CmsSite cmsSite1 = editCmsSite.get();
            if (cmsSite1 != null) {
                cmsSite1.setSitePort(cmsSite.getSitePort());
                cmsSite1.setSiteDomain(cmsSite.getSiteDomain());
                cmsSite1.setSiteName(cmsSite.getSiteName());
                cmsSite1.setSiteWebPath(cmsSite.getSiteWebPath());
                CmsSite save = cmsSiteRepository.save(cmsSite1);
                if (null != save) {
                    List<CmsSite> cmsSites = new ArrayList<>();
                    cmsSites.add(save);
                    return new CmsSiteResult(CommonCode.SUCCESS, cmsSites);
                }
            }
        }
        return new CmsSiteResult(CommonCode.FAIL, null);
    }

    @Override
    public CmsSiteResult findByCmsSiteId(String siteId) {
        Optional<CmsSite> optionalCmsSite = cmsSiteRepository.findById(siteId);
        if (optionalCmsSite.isPresent()) {
            CmsSite cmsSite = optionalCmsSite.get();
            if (null != cmsSite) {
                List<CmsSite> cmsSites = new ArrayList<>();
                cmsSites.add(cmsSite);
                return new CmsSiteResult(CommonCode.SUCCESS, cmsSites);
            }
        }
        return new CmsSiteResult(CommonCode.FAIL, null);
    }

    @Override
    public ResponseResult delCmsSiteById(String id) {
        cmsSiteRepository.deleteById(id);
        ResponseResult responseResult = new ResponseResult(CommonCode.SUCCESS);
        return responseResult;
    }

    @Override
    public ResponseResult delCmsSiteByIds(List<String> ids) {
        if (null != ids && ids.size() > 0) {
            for (String id : ids) {
                cmsSiteRepository.deleteById(id);
            }
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
