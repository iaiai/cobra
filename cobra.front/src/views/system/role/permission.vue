<style lang="less" scoped>

</style>

<template>
    <Drawer
            width="400"
            v-model="isShow"
            :mask-closable="false"
            :closable="true">
        <Row class-name="fbold" slot="header">设置权限</Row>
        <Row>
            <Alert show-icon>
                <Row class-name="m-b-10">说明</Row>
                <Icon type="ios-bulb-outline" slot="icon" />
                <Row class="f12" slot="desc">
                    <Row class-name="m-b-10">1. 选择子和选择父无关，如果不选择父，选择子中的任何东西时因为父看不到，所以所有的子也看不到，即使设置也无用</Row>
                    <Row>2. 看效果，请重新登录，刷新页面</Row>
                </Row>
            </Alert>

            <Tree
                    ref="permissionTree"
                    :data="menus"
                    :check-strictly="true"
                    show-checkbox
                    class="f12 menu m-t-20"
                    :check-directly="true"
                    :default-checked-keys="permissions" />

            <Row class-name="m-t-20">
                <Button type="primary" :loading="loading" @click="save">
                    <span v-if="!loading">
                        <i class="iconfont iaiai-correct f12" /> 保存</span>
                    <span v-else>处理中...</span>
                </Button>
                <Button @click="hidden" class="m-l-10">
                    <i class="iconfont iaiai-multiply f12" /> 取消
                </Button>
            </Row>
        </Row>
    </Drawer>
</template>

<script>
    export default {
        data() {
            return {
                isShow: false,
                loading: false,  //确定按钮

                treeProps: {
                    children: 'children',
                    label: 'name'
                },
                menus: [],
                roleId: null,
                permissions: [],
            }
        },
        methods: {
            getData() {
                this.getPermission()
            },
            getMenu(){
                this.$http('menu_all').then(res => {
                    this.loading = false

                    if(res.result!=null && res.result.length>0){
                        this.initTree(res.result)
                    }
                    this.menus = res.result

                    //刷新权限
                    this.refTreePermission()
                }).catch(error => {
                    this.loading = false
                })
            },
            initTree(_datas){
                for(var i=0;i<_datas.length;i++){
                    _datas[i].title = _datas[i].name

                    if(_datas[i].children!=null && _datas[i].children.length>0){
                        this.initTree(_datas[i].children)
                    }
                }
            },
            show(obj) {
                this.roleId = obj.id
                this.isShow = true
                this.getData()
            },
            getPermission() {
                this.loading = true
                this.permissions = []
                this.$http('role_permission', {
                    id: this.roleId,
                }).then(res => {
                    this.loading = false
                    this.permissions = []
                    if (res.result && res.result.length > 0) {
                        for (var i = 0; i < res.result.length; i++) {
                            this.permissions.push(res.result[i].menuId)
                        }
                    }

                    this.getMenu()
                }).catch(error => {
                    this.loading = false
                })
            },
            refTreePermission(){
                //刷新权限
                if(this.menus!=null && this.menus.length>0){
                    this.refTreePermissionChildren(this.menus)
                }
            },
            refTreePermissionChildren(_datas){
                //刷新权限
                for(var i=0;i<_datas.length;i++){
                    if(this.permissions.indexOf(_datas[i].id)>=0){
                        _datas[i].checked = true
                    }else{
                        _datas[i].checked = false
                    }

                    if(_datas[i].children!=null && _datas[i].children.length>0){
                        this.refTreePermissionChildren(_datas[i].children)
                    }
                }
            },
            hidden() {
                this.isShow = false
            },
            save() {
                var checkKeys = this.$refs.permissionTree.getCheckedNodes()
                var ids = ""
                for (var i = 0; i < checkKeys.length; i++) {
                    if (i > 0) ids += ","
                    ids += checkKeys[i].id
                }

                this.$http('role_permission_setting', {
                    id: this.roleId,
                    menus: ids,
                }).then(res => {
                    this.isShow = false
                    this.$Message.success("设置成功")
                })
            },
        }
    }
</script>