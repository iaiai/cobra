<style lang="less">
    @import './403.less';
</style>

<template>
    <div class="error403">
        <div class="error403-body-con">
            <Card>
<!--                <div class="error403-body-con-title">无权限</div>-->
                <div class="error403-body-con-title">4<span class="error403-0-span"><i class="el-icon-lock" /></span><span class="error403-key-span"><i style="font-size: 220px;" class="el-icon-key" /></span></div>
                <p class="error403-body-con-message">You don't have permission</p>
                <div class="error403-btn-con">
                    <Button @click="goHome" size="small" style="width: 200px;" type="text">返回首页</Button>
                    <Button @click="backPage" size="small" style="width: 200px;margin-left: 40px;" type="primary">返回上一页</Button>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
export default {
  name: 'Error403',
  methods: {
    backPage () {
      this.$router.go(-1);
    },
    goHome () {
      this.$router.push({
        name: 'home'
      });
    }
  }
};
</script>
