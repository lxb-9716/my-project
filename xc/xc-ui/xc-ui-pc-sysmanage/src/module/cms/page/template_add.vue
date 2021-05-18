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
      <el-form-item label="模板名称" prop="templateName" style="width: 302px">
        <el-input v-model="pageForm.templateName" auto-complete="off"></el-input>
      </el-form-item>

      <el-form-item label="模板参数" prop="templateParameter" style="width: 302px">
        <el-input v-model="pageForm.templateParameter" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="模板文件" prop="templateFileId" style="width: 302px">
        <el-input v-model="pageForm.templateFileId" auto-complete="off"></el-input>
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
          templateName: '',
          templateParameter: '',
          templateFileId: ''
        },
        pageFormRules: {
          siteId: [
            {required: true, message: '请选择站点', trigger: 'blur'}
          ],
          templateName: [
            {required: true, message: '请输入模板名称', trigger: 'blur'}
          ],
          templateParameter: [
            {required: true, message: '请输入模板参数', trigger: 'blur'}
          ],
          templateFileId: [
            {required: true, message: '请输入模板文件id', trigger: 'blur'}
          ]
        },
        siteList: [],
      }
    },
    methods: {
      /*调用后台方法查询站点信息*/
      queryCmsSite: function () {
        cmsApi.cms_site_list().then((res) => {
          this.siteList = res.cmsSites;
        })
      },
      go_back: function () {
        //获得当前路由并进行设置
        this.$router.push({
          path: '/cms/template/list',
          query: {
            //获得当前页面路由url的参数
            page: this.$route.query.page,
            templateName: this.$route.query.templateName
          }
        })
      },
      addSubmit: function () {
        this.$refs.pageForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.addLoading = true;
              cmsApi.cms_template_add(this.pageForm).then((res) => {
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
    }
  }
</script>
<style>

</style>
