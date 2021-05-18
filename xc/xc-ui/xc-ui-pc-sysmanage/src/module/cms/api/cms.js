//public是对axios的工具类封装，定义了http请求方法
import http from './../../../base/api/public'
//导入querystring工具组件，querystring在package-lock.json已配置
import querystring from 'querystring'

let sysConfig = require('@/../config/sysConfig')
let apiUrl = sysConfig.xcApiUrlPre;

//页面查询
export const page_list = (page, size, params) => {
  //将json对象转成key/value对
  let query = querystring.stringify(params)
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl + '/cms/page/list/' + page + '/' + size + '?' + query);
}

//站点信息查询
export const cms_site_list = () => {
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl + '/cms/site/list');
}

//模板信息查询
export const cms_template_list = () => {
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl + '/cms/templates/list');
}
//添加页面信息
export const cms_page_add = (params) => {
  //请求服务端的页面查询接口
  return http.requestPost(apiUrl + '/cms/page/add', params);
}

//删除页面信息
export const cms_page_delete = (id) => {
  //请求服务端的页面查询接口
  return http.requestDelete(apiUrl + '/cms/page/delete/' + id);
}

//根据页面id查询页面信息
export const cms_page_findByPageId = (id) => {
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl + '/cms/page/find/' + id);
}

//修改页面信息
export const cms_page_edit = (id, params) => {
  //请求服务端的页面查询接口
  return http.requestPut(apiUrl + '/cms/page/edit/' + id, params);
}

//根据页面id批量删除页面信息
export const cms_page_delete_batch = (ids) => {
  //请求服务端的页面查询接口
  return http.requestPost(apiUrl + '/cms/page/batch/delete', ids);
}

/**
 * 页面模板相关接口api
 * @param page
 * @param size
 * @param params
 * @returns {*}
 */
//查询页面模板列表信息
export const cms_findTemplate_list = (page, size, params) => {
  //将json对象转成key/value对
  let query = querystring.stringify(params)
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl + '/cms/template/find/' + page + '/' + size + '?' + query);
}
//添加模板信息
export const cms_template_add = (params) => {
  //请求服务端的页面查询接口
  return http.requestPost(apiUrl + '/cms/template/add', params);
}

//修改页面模板
export const cms_template_edit = (id, params) => {
  //请求服务端的页面查询接口
  return http.requestPut(apiUrl + '/cms/template/edit/' + id, params);
}

//根据模板id查询页面信息
export const cms_page_findByCmsTemplateId = (id) => {
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl + '/cms/template/find/' + id);
}

//删除模板信息
export const cms_template_delete = (id) => {
  //请求服务端的页面查询接口
  return http.requestDelete(apiUrl + '/cms/template/delete/' + id);
}

//批量删除模板信息
export const cms_template_delete_batch = (ids) => {
  //请求服务端的页面查询接口
  return http.requestPost(apiUrl + '/cms/template/batch/delete', ids);
}

/**
 * 站点操作相关接口api
 */
//查询页面模板列表信息
export const cms_findCmsSite_list = (page, size, params) => {
  //将json对象转成key/value对
  let query = querystring.stringify(params)
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl + '/cms/site/find/list/' + page + '/' + size + '?' + query);
}

//添加站点信息
export const cms_site_add = (params) => {
  //请求服务端的页面查询接口
  return http.requestPost(apiUrl + '/cms/site/add', params);
}
//修改站点信息
export const cms_site_edit = (siteId, params) => {
  //请求服务端的页面查询接口
  return http.requestPut(apiUrl + '/cms/site/edit/' + siteId, params);
}

//根据站点id查询页面信息
export const cms_site_findByCmsSiteId = (siteId) => {
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl + '/cms/site/find/' + siteId);
}

//删除站点信息
export const cms_site_delete = (id) => {
  //请求服务端的页面查询接口
  return http.requestDelete(apiUrl + '/cms/site/delete/' + id);
}

//批量删除站点信息
export const cms_site_delete_batch = (ids) => {
  //请求服务端的页面查询接口
  return http.requestPost(apiUrl + '/cms/site/batch/delete', ids);
}


