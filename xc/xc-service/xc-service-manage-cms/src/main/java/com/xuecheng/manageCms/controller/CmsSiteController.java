package com.xuecheng.manageCms.controller;

import com.xuecheng.api.cms.CmsSiteControllerApi;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manageCms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cms/site")
public class CmsSiteController implements CmsSiteControllerApi {
    @Autowired
    private CmsSiteService cmsSiteService;

    @Override
    @GetMapping("/find/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QuerySiteRequest querySiteRequest) {
        QueryResponseResult queryResponseResult = cmsSiteService.findList(page, size, querySiteRequest);
        return queryResponseResult;
    }

    @Override
    @PostMapping("/add")
    public CmsSiteResult addCmsSite(@RequestBody CmsSite cmsSite) {
        CmsSiteResult cmsSiteResult = cmsSiteService.addCmsSite(cmsSite);
        return cmsSiteResult;
    }

    @Override
    @PutMapping("/edit/{siteId}")
    public CmsSiteResult editCmsSite(@PathVariable("siteId") String siteId, @RequestBody CmsSite cmsSite) {
        CmsSiteResult cmsSiteResult = cmsSiteService.editCmsSite(siteId, cmsSite);
        return cmsSiteResult;
    }

    @Override
    @GetMapping("find/{siteId}")
    public CmsSiteResult findByCmsSiteId(@PathVariable("siteId") String siteId) {
        CmsSiteResult cmsSiteResult = cmsSiteService.findByCmsSiteId(siteId);
        return cmsSiteResult;
    }

    @Override
    @DeleteMapping("delete/{id}")
    public ResponseResult delCmsSiteById(@PathVariable("id") String id) {
        ResponseResult responseResult = cmsSiteService.delCmsSiteById(id);
        return responseResult;
    }

    @Override
    @PostMapping("/batch/delete")
    public ResponseResult delCmsSiteByIds(@RequestBody List<String> ids) {
        ResponseResult responseResult = cmsSiteService.delCmsSiteByIds(ids);
        return responseResult;
    }
}
