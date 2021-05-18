<template>
  <div>
    <el-form :model="pageForm" label-width="80px" :rules="pageFormRules" ref="pageForm">
      <el-form-item label="所属站点" prop="siteId" v-on:click="queryCmsSite">
        <el-select v-model="pageForm.siteId" placeholder="请选择站点">
          <el-option
            v-for="item in siteList"
            :key="item.siteId"
            :label="item.siteName"
            :value="item.siteId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择模版" prop="templateId" v-on:click="queryCmsTemplate">
        <el-select v-model="pageForm.templateId" placeholder="请选择">
          <el-option
            v-for="item in templateList"
            :key="item.templateId"
            :label="item.templateName"
            :value="item.templateId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="页面名称" prop="pageName">
        <el-input v-model="pageForm.pageName" auto-complete="off" style="width: 302px"></el-input>
      </el-form-item>

      <el-form-item label="别名" prop="pageAliase">
        <el-input v-model="pageForm.pageAliase" auto-complete="off" style="width: 302px"></el-input>
      </el-form-item>
      <el-form-item label="访问路径" prop="pageWebPath">
        <el-input v-model="pageForm.pageWebPath" auto-complete="off" style="width: 302px"></el-input>
      </el-form-item>

      <el-form-item label="物理路径" prop="pagePhysicalPath">
        <el-input v-model="pageForm.pagePhysicalPath" auto-complete="off" style="width: 302px"></el-input>
      </el-form-item>
      <el-form-item label="数据Url" prop="dataUrl">
        <el-input v-model="pageForm.dataUrl" auto-complete="off" style="width: 302px"></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-radio-group v-model="pageForm.pageType">
          <el-radio class="radio" label="0">静态</el-radio>
          <el-radio class="radio" label="1">动态</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker type="datetime" placeholder="创建时间" v-model="pageForm.pageCreateTime"
                        style="width: 220px"></el-date-picker>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="go_back">返回</el-button>
      <el-button type="primary" @click.native="editSubmit" :loading="addLoading">提交</el-button>
    </div>
  </div>
</template>
<script>
  import * as cmsApi from '../api/cms'

  export default {
    data() {
      return {
        //模版列表
        templateList: [],
        addLoading: false,//加载效果标记
        //新增界面数据
        pageForm: {
          siteId: '',
          templateId: '',
          pageName: '',
          pageAliase: '',
          pageWebPath: '',
          dataUrl: '',
          pageParameter: '',
          pagePhysicalPath: '',
          pageType: '',
          pageCreateTime: new Date()
        },
        pageFormRules: {
          siteId: [
            {required: true, message: '请选择站点', trigger: 'blur'}
          ],
          templateId: [
            {required: true, message: '请选择模版', trigger: 'blur'}
          ],
          pageName: [
            {required: true, message: '请输入页面名称', trigger: 'blur'}
          ],
          pageWebPath: [
            {required: true, message: '请输入访问路径', trigger: 'blur'}
          ],
          pagePhysicalPath: [
            {required: true, message: '请输入物理路径', trigger: 'blur'}
          ]
        },
        siteList: []
      }
    },
    methods: {
      /*调用后台方法查询站点信息*/
      queryCmsSite: function () {
        cmsApi.cms_site_list().then((res) => {
          this.siteList = res.cmsSites;
        })
      },
      /*调用后台方法查询页面模板信息*/
      queryCmsTemplate: function () {
        cmsApi.cms_template_list().then((res) => {
            this.templateList = res.cmsTemplates;
          }
        )
      },
      /*返回*/
      go_back:function() {
        /*获得当前页面的路由进行设置*/
        this.$router.push({
          path: '/cms/page/list', query: {
            //获得当前页面路由url的参数
            page: this.$route.query.page,
            siteId: this.$route.query.siteId,
            pageType: this.$route.query.pageType,
            pageAliase: this.$route.query.pageAliase,
            pageName: this.$route.query.pageName
          }
        })
      },
      /*页面内弄修改提交*/
      editSubmit:function() {
        this.$refs.pageForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交新修改的信息吗？', '提示', {}).then(() => {
              this.addLoading = true;
              cmsApi.cms_page_edit(this.$route.params.pageId, this.pageForm).then((res) => {
                console.log(res);
                if (res.success) {
                  this.addLoading = false;
                  this.$message({
                    message: '修改成功',
                    type: 'success'
                  });
                  //返回
                  this.go_back();
                } else {
                  this.addLoading = false;
                  this.$message.error('修改失败');
                }
              });
            });
          }
        });
      }

    },
    /*create钩子方法中调用后台根据页面id查询页面信息方法，页面还未渲染完成执行
    * 注意以下两点：
    *     当页面的url请求路径是普通的请求风格（请求参数由?,&符号拼接而成）时候可以通过this.$route.query.pageId的方式获得当前路由url中的参数值
    *     当页面的url请求路径采用的是restful风格时候可以通过this.$route.params.pageId的方式获得当前路由url中的参数值
    * */
    created: function () {
      cmsApi.cms_page_findByPageId(/*this.$route.query.pageId*/this.$route.params.pageId).then((res) => {
        console.log(res);
        if (res.success) {
          this.pageForm = res.cmsPage;
        } else {
          this.$message.error('获取数据错误');
        }
      });
      //钩子方法中初始化站点信息列表
      this.siteList = this.queryCmsSite();
      //钩子方法中初始化模板信息列表
      this.templateList = this.queryCmsTemplate();
    },
    /*mounted钩子方法：页面渲染完成之后执行*/
    mounted: function () {
    }
  }
</script>
<style>

</style>
