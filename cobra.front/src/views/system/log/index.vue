<style lang="less" scoped>
    .input{
        height:35px;
        width:350px;
        font-size: 12px;
    }
    .page-content{
        top:90px;
    }
</style>

<template>
    <Row class="max-height pr">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>日志</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="search-content">
            <DatePicker
                    style="width:300px"
                    v-model="searchDate"
                    type="datetimerange"
                    format="yyyy-MM-dd HH:mm:ss"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
            </DatePicker>
            <Input
                    placeholder="搜索登录名、姓名、手机号，按回车搜索..."
                    v-model="pageParams.search"
                    clearable
                    class="input m-l-10 m-r-10"
                    @keyup.enter.native="search"/>
            <Button type="primary" icon="ios-search" @click="search">搜索</Button>
        </Row>
        <Row class="page-content" ref="pageContent">
            <Table
                    :data="datas"
                    border
                    stripe
                    size="small"
                    class="table-small"
                    :height="tableHeight"
                    :columns="columns">
                <template slot-scope="{ row }" slot="startTime">
                    {{row.startTime|formatTimestamp('yyyy-MM-dd hh:mm:ss')}}
                </template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row,index }" slot="fun">
                    <a href="javascript:;" @click="info(row)">
                        <i class="iconfont iaiai-md-log f12" /> 详情
                    </a>
                </template>
            </Table>
        </Row>
        <Row class="page-bottom">
            <Page
                    class-name="f12"
                    @on-page-size-change="changePageSize"
                    @on-change="chagePage"
                    show-total
                    show-elevator
                    show-sizer
                    :total="pageParams.total"
                    :current="pageParams.page"
                    :page-size="pageParams.limit"
                    :page-size-opts="$root.$data.page.paginations"/>
        </Row>

        <infoComp ref="infoComp"></infoComp>
    </Row>
</template>

<script>
    import infoComp from './info'

    export default {
        components: {
            infoComp,
        },
        data() {
            return {
                datas:[],
                columns: [
                    {title: '请求时间', slot: 'startTime', width:160},
                    {title: '请求时长(毫秒)', key: 'duration', width:120},
                    {title: 'ip', key: 'ip', width:110,resizable:true},
                    {title: '地区', key: 'ipAddress', minWidth:230},
                    {title: '地址', key: 'url', minWidth:430},
                    {title: '提交方式', key: 'method', width:80},
                    {title: 'ip', key: 'ipForward', minWidth:210},
                    {title: 'UA', key: 'userAgent', minWidth:780},
                    {title: '创建时间', slot: 'createTime', width:160},
                    {title: '操作', slot: 'fun', width:70, fixed:'right', align: 'center'},
                ],

                pageParams:{
                    page:1,
                    limit:this.$root.$data.page.defaultLimit,
                    search:'',
                    total:0,
                    startTime:'',
                    endTime:'',
                },

                searchDate:[],

                tableHeight:0,
            }
        },
        mounted(){
            this.$nextTick(()=>{
                this.tableHeight = this.$refs.pageContent.$el.clientHeight
            })

            this.onLoad()
        },
        methods:{
            onLoad(){
                this.$http('log_index',this.pageParams).then(res => {
                    this.datas = res.result.records
                    this.pageParams.total = res.result.total
                }).catch(error => {})
            },
            search(){
                this.pageParams.startTime = ""
                this.pageParams.endTime = ""
                if(this.searchDate && this.searchDate.length>0){
                    this.pageParams.startTime = this.$formatDateTime(this.searchDate[0])
                    this.pageParams.endTime = this.$formatDateTime(this.searchDate[1])
                }
                this.pageParams.page = 1
                this.onLoad()
            },
            changePageSize(limit){
                this.pageParams.page = 1
                this.pageParams.limit = limit
                this.onLoad()
            },
            chagePage(page){
                this.pageParams.page = page
                this.onLoad()
            },
            info(obj){
                this.$refs.infoComp.show(obj)
            },
        }
    }
</script>
