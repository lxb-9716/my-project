<template>
  <div>
    <el-form :model="pageForm" label-width="80px" :rules="pageFormRules" ref="pageForm">
      <el-form-item label="站点名称" prop="siteName" style="width: 302px">
        <el-input v-model="pageForm.siteName" auto-complete="off"></el-input>
      </el-form-item>

      <el-form-item label="站点IP" prop="siteDomain" style="width: 302px">
        <el-input v-model="pageForm.siteDomain" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="站点端口" prop="sitePort" style="width: 302px">
        <el-input v-model="pageForm.sitePort" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="站点访问路径" prop="siteWebPath" style="width: 302px">
        <el-input v-model="pageForm.siteWebPath" auto-complete="off"></el-input>
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
        addLoading: false,//加载效果标记
        //新增界面数据
        pageForm: {
          siteId: '',
          siteName: '',//站点名称
          siteDomain: '',//站点ip
          sitePort: '',//站点端口
          siteWebPath: '',//站点访问地址
          siteCreateTime: new Date()
        },
        pageFormRules: {
          siteName: [
            {required: true, message: '站点名称不能为空', trigger: 'blur'}
          ],
          siteDomain: [
            {required: true, message: '站点IP不能为空', trigger: 'blur'}
          ],
          sitePort: [
            {required: true, message: '站点端口号不能为空', trigger: 'blur'}
          ],
          siteWebPath: [
            {required: true, message: '站点访问路径不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      go_back: function () {
        //获得当前路由并进行设置
        this.$router.push({
          path: '/cms/site/list',
          query: {
            //获得当前页面路由url的参数
            page: this.$route.query.page,
            siteName: this.$route.query.siteName,
            siteDomain:this.$route.query.siteDomain,
            sitePort:this.$route.query.sitePort
          }
        })
      },
      addSubmit: function () {
        this.$refs.pageForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.addLoading = true;
              cmsApi.cms_site_add(this.pageForm).then((res) => {
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
