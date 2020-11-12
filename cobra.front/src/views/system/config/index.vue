<style lang="less" scoped>
    .col1{
        width:500px;
        height: 100%;
    }

    .row{
        font-size: 12px;
        width:100%;
        height:30px;

        border-top: 1px solid #eee;
        border-left: 1px solid #eee;
        border-right: 1px solid #eee;

        .content{
            width: 100px;
            border-right: 1px solid #eee;
            cursor: pointer;

            input{
                width: 100%;
                height: 100%;
                border: 0px;
                outline:0px;
            }

            .select{
                width:100%;
                height:30px;

                p{
                    padding-left: 10px;
                    line-height: 30px;
                }
                .icon{
                    position: absolute;
                    right:10px;
                    top:10px;
                }
            }
        }
        .remark{
            padding: 0 10px;
            border-right: 1px solid #eee;
            flex: 1;
        }
        .sub{
            width: 100px;
            cursor: pointer;
            &:hover{
                background-color: #fafafa;
            }

            .button{
                width:100%;
                height:100%;
                border-radius: 0px;

                &:hover{
                    background: #f8f8f9 !important;
                }
            }
        }

        &:first-child{
            border-top: 0px;
        }

        &:last-child{
            border-bottom: 1px solid #eee;
        }
    }
    .td{
        float: left;
    }
</style>

<template>
    <Row class="max-height pr">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>
                    <Icon type="md-cog" /> 配置参数
                </BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="pa" style="left: 0;right: 0;bottom: 0;top: 40px;">
            <Row class="col1 right-outer-shadow">
                <div class="row flex">
                    <div class="float-l content center-vertical">
                        <Dropdown :transfer="true" class="select" @on-click="selSystemLoginSingle">
                            <p v-if="systemLoginSingle.value=='1'">是</p>
                            <p v-if="systemLoginSingle.value=='0'">否</p>
                            <Icon type="ios-arrow-down" size="10" class="icon" />
                            <DropdownMenu slot="list">
                                <DropdownItem name="1">是</DropdownItem>
                                <DropdownItem name="0">否</DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </div>
                    <div class="float-l remark center-vertical">
                        <Tooltip content="同一个系统用户登录是否只允许一个登录，不影响之前登录用户" max-width="300">
                            系统登录用户单例 <Icon type="md-alert" />
                        </Tooltip>
                    </div>
                    <div class="float-l sub center">
                        <Button type="text" class="button" :loading="loading.systemLoginSingle" @click="edit('systemLoginSingle')">
                            <span v-if="!loading.systemLoginSingle">修改</span>
                            <span v-else></span>
                        </Button>
                    </div>
                </div>

                <div class="row flex">
                    <div class="float-l content center-vertical">
                        <Dropdown :transfer="true" class="select" @on-click="selWebsocketSingle">
                            <p v-if="websocketSingle.value=='1'">是</p>
                            <p v-if="websocketSingle.value=='0'">否</p>
                            <Icon type="ios-arrow-down" size="10" class="icon" />
                            <DropdownMenu slot="list">
                                <DropdownItem name="1">是</DropdownItem>
                                <DropdownItem name="0">否</DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </div>
                    <div class="float-l remark center-vertical">
                        <Tooltip content="同一个用户登录后台只允许最后一个接收websocket信息，不影响之前登录用户，此值最好跟登录用户配置成一致" max-width="300">
                            websocket单例 <Icon type="md-alert" />
                        </Tooltip>
                    </div>
                    <div class="float-l sub center">
                        <Button type="text" class="button" :loading="loading.websocketSingle" @click="edit('websocketSingle')">
                            <span v-if="!loading.websocketSingle">修改</span>
                            <span v-else></span>
                        </Button>
                    </div>
                </div>
            </Row>
        </Row>
    </Row>
</template>

<script>
    export default {
        data() {
            return {
                datas:[],

                websocketSingle:{
                    id:'',
                    key:'',
                    value:'0',
                    remark:'',
                },

                systemLoginSingle:{
                    id:'',
                    key:'',
                    value:'0',
                    remark:'',
                },

                loading:{
                    websocketSingle:false,
                    systemLoginSingle:false,
                },
            }
        },
        mounted() {
            this.onLoad()
        },
        methods: {
            onLoad() {
                this.$http('config_index').then(res => {
                    this.datas = res.result

                    if(this.datas && this.datas.length>0){
                        for (let i = 0; i < this.datas.length; i++) {
                            if(this.datas[i].key=='websocket-single'){
                                this.websocketSingle.id = this.datas[i].id
                                this.websocketSingle.key = this.datas[i].key
                                this.websocketSingle.value = this.datas[i].value
                                this.websocketSingle.remark = this.datas[i].remark
                            }
                            if(this.datas[i].key=='system-login-single'){
                                this.systemLoginSingle.id = this.datas[i].id
                                this.systemLoginSingle.key = this.datas[i].key
                                this.systemLoginSingle.value = this.datas[i].value
                                this.systemLoginSingle.remark = this.datas[i].remark
                            }
                        }
                    }
                })
            },
            edit(command){
                if(command=='websocketSingle'){
                    this.loading.websocketSingle = true
                    this.$http('config_setting',this.websocketSingle).then(res => {
                        this.loading.websocketSingle = false
                        this.$Message.success("编辑成功")
                    }).catch(error => {
                        this.loading.websocketSingle = false
                    })
                    return
                }

                if(command=='systemLoginSingle'){
                    this.loading.systemLoginSingle = true
                    this.$http('config_setting',this.systemLoginSingle).then(res => {
                        this.loading.systemLoginSingle = false
                        this.$Message.success("编辑成功")
                    }).catch(error => {
                        this.loading.systemLoginSingle = false
                    })
                    return
                }
            },

            selWebsocketSingle(val){
                this.websocketSingle.value = val
            },

            selSystemLoginSingle(val){
                this.systemLoginSingle.value = val
            },
        }
    }
</script>
