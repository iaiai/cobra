<style lang="less" scoped>
    .input{
        height:35px;
        width:350px;
        padding-left: 0px;
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
                <BreadcrumbItem>文件管理</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="search-content">
            <Input
                    placeholder="搜索文件名、访问地址、后缀，按回车搜索..."
                    v-model="pageParams.search"
                    clearable
                    class="input m-r-10"
                    @keyup.enter.native="search"/>
            <Button type="primary" icon="ios-search" @click="search">搜索</Button>
        </Row>
        <Row class="page-content" ref="pageContent">
            <Table
                    :data="datas"
                    border
                    stripe
                    class="table-small"
                    size="small"
                    :height="tableHeight"
                    :columns="columns">
                <template slot-scope="{ row }" slot="size">{{row.size|showSize}}</template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
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
    </Row>
</template>

<script>
    export default {
        data() {
            return {
                pageParams:{
                    page:1,
                    limit:this.$root.$data.page.defaultLimit,
                    search:'',
                    total:0,
                },

                datas:[],
                columns: [
                    {title: '文件名', key: 'filename', minWidth:200},
                    {title: '后缀', key: 'suffix', width:80},
                    {title: '访问地址', key: 'webUrl', minWidth:650},
                    {title: '文件大小', slot: 'size', width:100},
                    {title: '创建时间', slot: 'createTime', width:160},
                ],
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
                this.$http('upload_file_index',this.pageParams).then(res => {
                    this.datas = res.result.records
                    this.pageParams.total = res.result.total
                }).catch(error => {})
            },
            search(){
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
            add(){

            },
        }
    }
</script>
