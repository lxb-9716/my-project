/*引入vue页面*/
import Home from '@/module/home/page/home.vue';

import page_list from '@/module/cms/page/page_list.vue';
import page_add from '@/module/cms/page/page_add.vue';
import page_edit from '@/module/cms/page/page_edit.vue';

import template_list from '@/module/cms/page/template_list.vue';
import template_add from '@/module/cms/page/template_add.vue';
import template_edit from '@/module/cms/page/template_edit.vue';

import site_list from '@/module/cms/page/site_list.vue';
import site_add from '@/module/cms/page/site_add.vue';
import site_edit from '@/module/cms/page/site_edit.vue';


export default [{
  path: '/',
  component: Home,
  name: 'CMS',//菜单名称
  hidden: false,
  children: [
    /*页面cmsPage*/
    {path: '/cms/page/list', name: '页面信息列表', component: page_list, hidden: false},
    {path: '/cms/page/add', name: '添加页面信息', component: page_add, hidden: true},
    /*/cms/page/edit/:pageId  restful形式获得pageId的值，固定写法*/
    {path: '/cms/page/edit/:pageId', name: '修改页面信息', component: page_edit, hidden: true},

    /*页面模板cmsTemplate*/
    {path: '/cms/template/list', name: '页面模板列表', component: template_list, hidden: false},
    {path: '/cms/template/add', name: '添加页面模板', component: template_add, hidden: true},
    {path: '/cms/template/edit/:templateId', name: '修改页面模板', component: template_edit, hidden: true},

    /*页面站点cmsSite*/
    {path: '/cms/site/list', name: '页面站点列表', component: site_list, hidden: false},
    {path: '/cms/site/add', name: '添加页面站点', component: site_add, hidden: true},
    {path: '/cms/site/edit/:siteId', name: '修改页面站点', component: site_edit, hidden: true},

  ],
}
]
