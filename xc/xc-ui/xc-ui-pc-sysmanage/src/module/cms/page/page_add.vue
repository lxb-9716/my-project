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
      <el-form-item label="页面名称" prop="pageName" style="width: 302px">
        <el-input v-model="pageForm.pageName" auto-complete="off"></el-input>
      </el-form-item>

      <el-form-item label="别名" prop="pageAliase" style="width: 302px">
        <el-input v-model="pageForm.pageAliase" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="访问路径" prop="pageWebPath" style="width: 302px">
        <el-input v-model="pageForm.pageWebPath" auto-complete="off"></el-input>
      </el-form-item>

      <el-form-item label="物理路径" prop="pagePhysicalPath" style="width: 302px">
        <el-input v-model="pageForm.pagePhysicalPath" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="数据Url" prop="dataUrl" style="width: 302px">
        <el-input v-model="pageForm.dataUrl" auto-complete="off"></el-input>
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
      <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
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
        siteList: [],
        goback_params: {
          page: this.$route.query.page,
          siteId: this.$route.query.siteId,
        }
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
      go_back:function() {
        //获得当前路由并进行设置
        this.$router.push({
          path: '/cms/page/list',
          query: {
            //获得当前页面路由url的参数
            page: this.$route.query.page,
            siteId: this.$route.query.siteId,
            pageType: this.$route.query.pageType,
            pageAliase: this.$route.query.pageAliase,
            pageName: this.$route.query.pageName
          }
        })
      },
      addSubmit:function() {
        this.$refs.pageForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.addLoading = true;
              cmsApi.cms_page_add(this.pageForm).then((res) => {
                console.log(res);
                if (res.success) {
                  this.addLoading = false;
                  //NProgress.done();
                  this.$message({
                    message: '提交成功',
                    type: 'success'
                  });
                  this.$refs['pageForm'].resetFields();

                } else if (res.message) {
                  this.addLoading = false;
                  this.$message.error(res.message);
                } else {
                  this.addLoading = false;
                  this.$message.error('提交失败');
                }
              });
            });
          }
        });
      }
    },
    created: function () {

    },
    /*钩子方法初始化相关信息*/
    mounted: function () {
      //初始化站点列表
      this.queryCmsSite();
      //初始化模板列表
      this.templateList = this.queryCmsTemplate();
    }
  }
</script>
<style>

</style>
