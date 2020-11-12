<style lang="less" scoped>

</style>

<template>
    <Row class="max-height pr">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb>
                <BreadcrumbItem>在线用户</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="page-content-nobuttom p-lr-16 p-t-15 p-b-10">
            <Row class-name="center-vertical">
                <Col span="12" class="f12">在线人数：{{this.datas.length}}</Col>
                <Col span="12" class="text-right">
                    <Button type="text" icon="md-refresh" @click="onLoad" />
                </Col>
            </Row>
            <Table :data="datas" border stripe class="table-small" size="small" :columns="columns">
                <template slot-scope="{ row }" slot="fun">
                    <Poptip
                            confirm
                            placement="left"
                            :transfer="true"
                            :title="`真的要把此 [${row.user.nickname}] 用户踢下线？`"
                            @on-ok="exit(row)">
                        <a href="javascript:;">
                            <Icon type="md-log-out" /> 踢下线
                        </a>
                    </Poptip>
                </template>
            </Table>
        </Row>
    </Row>
</template>

<script>
    export default {
        data() {
            return {
                datas:[],
                columns: this.initColumns(),
            }
        },
        mounted(){
            this.onLoad()
        },
        methods:{
            initColumns(){
                let list = [
                    {title: 'ip', key: 'ip', width:100},
                    {title: 'ip地址', key: 'ipAddress', width:200},
                    {title: '登录名', key: 'username', minWidth:100},
                    {title: '姓名', key: 'nickname', minWidth:100},
                    {title: '请求地址', key: 'url', minWidth:300},
                    {title: '最后访问时间', key: 'lastTime', width:170},
                    {title: '登录时间', key: 'loginTime', width:170},
                    {title: 'UA头', key: 'userAgent', minWidth:800},
                ]
                if(this.$root.isPermission('system:online:exit')) {
                    list.push({title: '#', slot: 'fun',fixed:'right', align: 'center', width:90})
                }

                return list
            },
            onLoad(){
                this.$http('online_index').then(res => {
                    this.datas = res.result
                    console.log(res.result)
                })
            },
            exit(row){
                this.$http('online_exit',{
                    token:row.token,
                    userId:row.user.id,
                }).then(res => {
                    this.onLoad()
                })
            },
        }
    }
</script>
