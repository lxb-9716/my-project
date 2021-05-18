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
      <el-button type="primary" @click.native="editSubmit" :loading="addLoading">提交</el-button>
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
      /*页面内容修改提交*/
      editSubmit: function () {
        this.$refs.pageForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交新修改的信息吗？', '提示', {}).then(() => {
              this.addLoading = true;
              cmsApi.cms_site_edit(this.$route.params.siteId, this.pageForm).then((res) => {
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
    created: function () {
      /*钩子方法中调用后台根据模板id查询站点信息的方法*/
      cmsApi.cms_site_findByCmsSiteId(/*this.$route.query.siteId*/this.$route.params.siteId).then((res) => {
        console.log(res);
        if (res.success) {
          this.pageForm = res.cmsSites[0];
        } else {
          this.$message.error('获取数据错误');
        }
      });
    },
    /*钩子方法初始化相关信息*/
    mounted: function () {
    }
  }
</script>
<style>

</style>
