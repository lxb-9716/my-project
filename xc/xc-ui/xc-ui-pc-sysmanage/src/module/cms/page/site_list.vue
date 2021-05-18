<template>
  <div>
    <!--查询表单-->
    <el-form :model="params" v-on:click="queryCmsSite">
      站点名称
      <el-input v-model="params.siteName" style="width: 100px" size="small"></el-input>
      站点ip
      <el-input v-model="params.siteDomain" style="width: 100px" size="small"></el-input>
      站点端口
      <el-input v-model="params.sitePort" style="width: 100px" size="small"></el-input>
      <el-button type="primary" @click="query" size="small">查询</el-button>
      <!--router-link：vue提供的路由功能，html页面渲染完成之后就是<a>链接标签
      :to：指定要跳转页面的路径
      query：用于将当前页面的page，siteId等属性的值带到跳转add页面的路径上
      -->
      <router-link class="mui-tab-item" :to="{path:'/cms/site/add',query:{
          page: this.params.page,
          siteName:this.params.siteName,
          siteDomain:this.params.siteDomain,
          sitePort:this.params.sitePort
      }}">
        <el-button type="success" size="small">新增站点</el-button>
      </router-link>
      <!--:disabled="this.selectedList.length === 0||this.disabled"
       含义：当复选框没有一个被选中的时候，批量删除的按钮不能被点击
      -->
      <el-button class="small" type="warning" size="small" v-on:click="removeSelected(selectedList)"
                 :disabled="this.selectedList.length === 0||Boolean.true">
        批量删除
      </el-button>
    </el-form>
    <el-table
      :data="list"
      stripe
      @selection-change="changeBox"
      style="width: 100%">
      <el-table-column type="selection" width="65" class-name="isCheck" v-model="selectedList">
      </el-table-column>
      <el-table-column type="index" align="center" label="序号" width="65" aria-checked="true">
      </el-table-column>
      <el-table-column prop="siteId" label="站点id" width="240">
      </el-table-column>
      <el-table-column prop="siteName" label="站点名称" width="120">
      </el-table-column>
      <el-table-column prop="siteDomain" label="站点ip" width="150">
      </el-table-column>
      <el-table-column prop="sitePort" label="站点端口" width="250">
      </el-table-column>
      <el-table-column prop="siteWebPath" label="站点访问路径" width="250">
      </el-table-column>
      <el-table-column prop="siteCreateTime" label="创建时间" width="250">
      </el-table-column>
      <el-table-column label="操作" width="160px">
        <template slot-scope="page">
          <el-button
            size="mini"
            @click="handleEdit(page.row.siteId)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(page.row.siteId)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      layout="total,sizes,prev,pager, next"
      :total="total"
      :page-sizes="pageSizes"
      :page-size="params.size"
      :current-page="params.page"
      v-on:size-change="handleSizeChange"
      v-on:current-change="changePage"
      style="float:right">
    </el-pagination>
  </div>
</template>
<script>
  /*编写页面静态部分，即model及vm部分。*/
  import * as cmsApi from '../api/cms'

  export default {
    data() {
      return {
        selectedList: [],
        siteList: [],//增加的站点集合数据
        pageTypeList: [],//增加的站点集合数据
        list: [],
        total: 0,
        pageSizes: [5, 10, 15, 20],
        params:
          {
            siteId: '',
            siteName: '',
            siteDomain:'',
            sitePort:'',
            page: 0,
            size: 5,
          }
      }
    },
    methods: {
      /*当选中复选框的时候可以获得当前选中已选中复选框表格所在行的内容*/
      changeBox: function (val) {
        this.selectedList = val;
      },
      /*批量删除复选框选中的内容*/
      removeSelected: function (selectedList) {
        var ids = [];
        selectedList.forEach(element => {
          ids.push(element.siteId)
        });
        this.$confirm('确定要删除选中的页面信息吗?', '提示').then(() => {
          cmsApi.cms_site_delete_batch(ids).then((res) => {
            console.log(res);
            if (res.success) {
              this.$message({
                message: '批量删除成功',
                type: 'success'
              });
              /*成功删除页面之后调用query方法刷新整个列表信息*/
              this.query();
            } else {
              this.$message.error('批量删除失败');
            }
          });
        })
      },
      /*动态修改每页显示的数量*/
      handleSizeChange: function (newSize) {
        this.params.size = parseInt(newSize || 2);
        //每页显示的数量更改后，调用查询方法进行刷新页面
        this.query();
      },
      handleEdit: function (siteId) {
        this.$router.push({
          path: '/cms/site/edit/' + siteId,
          query: {
            //获得当前页面路由url的参数
            page:this.params.page,
            siteName: this.params.siteName,
            siteDomain:this.params.siteDomain,
            sitePort:this.params.sitePort
          }
        })
      },
      /*删除页面*/
      handleDelete: function (templateId) {
        this.$confirm('确认删除该页面吗？', '提示', {}).then(() => {
          cmsApi.cms_site_delete(templateId).then((res) => {
            console.log(res);
            if (res.success) {
              this.$message({
                message: '删除成功',
                type: 'success'
              });
              /*成功删除页面之后调用query方法刷新整个列表信息*/
              this.query();
            } else {
              this.$message.error('删除失败');
            }
          });
        });
      },
      /*查询所有页面信息*/
      query: function () {
        // alert('查询')
        //调用服务端的接口
        cmsApi.cms_findCmsSite_list(this.params.page, this.params.size, this.params).then((res) => {
          //将res结果数据赋值给数据模型对象
          this.list = res.queryResult.list;
          this.total = res.queryResult.total;
        })
      },
      changePage: function (page) {//形参就是当前页码
        //调用query方法
        // alert(page)
        this.params.page = page;
        this.query()
      },
      queryCmsSite: function () {
        cmsApi.cms_site_list().then((res) => {
          this.siteList = res.cmsSites;
        })
      }
    },
    /*页面渲染完成之前调用created钩子方法*/
    created() {
      this.params.page = parseInt(this.$route.query.page || 1);
      this.params.siteName = this.$route.query.siteName || '';
      this.params.siteDomain = this.$route.query.siteDomain || '';
      this.params.sitePort = this.$route.query.sitePort || '';
    },
    /*页面渲染完成之后调用mounted钩子方法*/
    mounted() {
      //当DOM元素渲染完成后调用query()方法查询cmsPage的相关信息
      this.query();
      //在钩子方法中执行queryCmsSite()方法初始化站点列表信息
      this.queryCmsSite();
      //钩子方法中给pageTypeList赋值在页面中显示
      this.pageTypeList = [{pageType: '0', pageTypeName: '静态'}, {pageType: '1', pageTypeName: '动态'}];
    }
  }
</script>
<style>
  /*编写页面样式，不是必须*/
</style>
