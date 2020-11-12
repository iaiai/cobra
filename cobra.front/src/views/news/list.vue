<style lang="less" scoped>
    .input{
        width:350px;
        font-size: 12px;
    }

    .cover{
        width:100%;
        height:100%;
        margin-top: 6px;
    }
</style>

<template>
    <Row class="max-height pr">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12" v-if="breadcrumbs.length>0">
                <BreadcrumbItem v-for="breadcrumb in breadcrumbs" :key="breadcrumb.id">
                    <i :class="['f12',breadcrumb.icon]" /> {{breadcrumb.name}}
                </BreadcrumbItem>
            </Breadcrumb>
            <Breadcrumb class="f12" v-else>
                <BreadcrumbItem>全部</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="search-content">
            <Input
                    placeholder="搜索标题、内容按回车搜索..."
                    v-model="pageParams.search"
                    clearable
                    class="input m-r-10"
                    @keyup.enter.native="search"/>
            <Button icon="md-search" @click="search">搜索</Button>
        </Row>
        <Row class="fun-content" v-if="$root.isPermissions('newsAddMarkdown','news:del')">
            <span v-if="$root.isPermission('newsAddMarkdown')">
                <Dropdown @on-click="add" :transfer="true">
                    <Button type="primary">添加 <Icon type="ios-arrow-down" /></Button>
                    <DropdownMenu slot="list">
                        <DropdownItem name="html"><span class="f12">HTML编辑器</span></DropdownItem>
                        <DropdownItem name="markdown"><span class="f12">Markdown编辑器</span></DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </span>
            <span v-if="$root.isPermission('news:del')" class="m-l-10">
                <Poptip
                        confirm
                        placement="right"
                        :transfer="true"
                        :title="`真的要删除这 ${delItem.length} 项`"
                        @on-ok="dels()">
                    <Button type="error" icon="md-trash" :disabled="delItem.length<=0"> 删除</Button>
                </Poptip>
            </span>
        </Row>
        <Row
                class-name="page-content"
                ref="pageContent"
                :style="{top:$root.isPermissions('newsAddMarkdown','news:del')?'130px':'90px'}">
            <Table
                    :data="datas"
                    border
                    stripe
                    @on-selection-change="tableSelect"
                    size="small"
                    class="table-small"
                    :height="tableHeight"
                    :columns="columns">
                <template slot-scope="{ row }" slot="cover">
                    <img class="cover" :src="row.cover" v-if="row.cover">
                </template>
                <template slot-scope="{ row, index }" slot="show">
                    <i-switch
                            v-model="row.show"
                            true-color="#19be6b"
                            :true-value="1"
                            :false-value="0"
                            @on-change="editShow(index,row.show)"
                            :disabled="!$root.isPermission('news:edit:show')"/>
                </template>
                <template slot-scope="{ row }" slot="contentType">
                    <Tag color="geekblue" v-if="row.contentType==1">Html</Tag>
                    <Tag color="geekblue" v-if="row.contentType==2">Markdown</Tag>
                </template>
                <template slot-scope="{ row }" slot="releaseStartTime">{{row.releaseStartTime|formatTimestamp}}</template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row,index }" slot="fun">
                    <Row>
                        <a href="javascript:;" @click="preview(index)">
                            <i class="iconfont iaiai-preview-line f12" /> 预览
                        </a>
                        <span v-if="$root.isPermission('newsEditMarkdown')">
                            <Divider type="vertical" v-if="$root.isPermission('newsEditMarkdown')" />
                            <a href="javascript:;" @click="edit(index)">
                                <Icon type="md-create" /> 编辑
                            </a>
                        </span>
                        <span v-if="$root.isPermission('news:del')">
                            <Divider type="vertical" v-if="$root.isPermissions('newsEditMarkdown')" />
                            <Poptip
                                    confirm
                                    placement="left"
                                    :transfer="true"
                                    :title="`真的要删除此 [${row.title}] 新闻`"
                                    @on-ok="del(index)">
                                <a href="javascript:;" class="color-red">
                                    <Icon type="md-trash" /> 删除
                                </a>
                            </Poptip>
                        </span>
                    </Row>
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
                    :page-size-opts="$root.$data.page.paginations" />
        </Row>

        <htmlPreviewComp ref="htmlPreviewComp"></htmlPreviewComp>
        <markdownPreviewComp ref="markdownPreviewComp"></markdownPreviewComp>
    </Row>
</template>

<script>
    import htmlPreviewComp from './preview_html'
    import markdownPreviewComp from './preview_markdown'

    export default {
        props:['node'],
        components: {
            htmlPreviewComp,
            markdownPreviewComp,
        },
        data() {
            return {
                breadcrumbs:[],
                currentNode:null,

                pageParams:{
                    page:1,
                    limit:this.$root.$data.page.defaultLimit,
                    search:'',
                    total:0,
                },

                datas:[],
                columns: this.initColumns(),

                delItem:[],
                tableHeight:0,
            }
        },
        watch:{
            node(to,form){
                this.setBreadcrumbs(to)
                this.onLoad()
            }
        },
        created() {
            this.$bus.$on('news-index:ref', (arg) => {
                this.onLoad()
            })
        },
        destroyed() {
            this.$bus.$off('news-index:ref')
        },
        mounted(){
            this.$nextTick(()=>{
                this.tableHeight = this.$refs.pageContent.$el.clientHeight
            })

            this.setBreadcrumbs(this.node)
            this.onLoad()
        },
        methods:{
            initColumns(){
                let list = [
                    {title: '封面', slot: 'cover', width:120},
                    {title: '标题', key: 'title', minWidth:300},
                    {title: '显示', slot: 'show', width:80},
                    {title: '内容类型', slot: 'contentType', width:80},
                    {title: '发布时间', slot: 'releaseStartTime', minWidth:150},
                    {title: '创建时间', slot: 'createTime', width:160},
                ]

                if(this.$root.isPermission('news:del')) {
                    list.splice(0, 0, {type: 'selection', width: 40, align: 'center'})
                }
                list.push({
                    title: '操作',
                    slot: 'fun',
                    width:(this.$root.isPermission('newsEditMarkdown')?70:0)+(this.$root.isPermission('news:del')?65:0)+65,
                    fixed:'right',
                    align: 'center',
                })

                return list
            },
            setBreadcrumbs(nodes){
                this.breadcrumbs = []
                this.currentNode = null
                if(!nodes){
                    return
                }

                this.currentNode = nodes[0]
                this.breadcrumbs = Array.prototype.reverse.call(nodes)  //倒序
            },
            onLoad(){
                this.$http('news_index',{
                    typeId:this.currentNode?this.currentNode.id:null,
                    page:this.pageParams.page,
                    limit:this.pageParams.limit,
                    search:this.pageParams.search,
                }).then(res => {
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
            del(index){
                this.$http('news_del',{
                    id:this.datas[index].id,
                }).then(res => {
                    this.onLoad()
                }).catch(error => {})
            },
            dels(){
                var ids = ""
                for(var i=0;i<this.delItem.length;i++){
                    if(ids.length>0)ids+=","
                    ids+=this.delItem[i].id
                }

                this.$http('news_dels',{id:ids}).then(res => {
                    this.onLoad()
                }).catch(error => {})
            },
            add(value){
                if(value==='html') {
                    if(this.breadcrumbs.length>0) {
                        this.$router.push({
                            name: 'newsAddHtml',
                            query: {
                                id: this.breadcrumbs[this.breadcrumbs.length-1].id,
                                name: this.breadcrumbs[this.breadcrumbs.length-1].name,
                            },
                        })
                    }else{
                        this.$router.push({
                            name: 'newsAddHtml',
                        })
                    }
                    return
                }
                if(value==='markdown'){
                    if(this.breadcrumbs.length>0) {
                        this.$router.push({
                            name: 'newsAddMarkdown',
                            query: {
                                id: this.breadcrumbs[this.breadcrumbs.length-1].id,
                                name: this.breadcrumbs[this.breadcrumbs.length-1].name,
                            },
                        })
                    }else{
                        this.$router.push({
                            name: 'newsAddMarkdown',
                        })
                    }
                    return
                }
            },
            tableSelect(n){
                this.delItem = n
            },
            addSuccess(){
                this.onLoad()
            },
            editSuccess(){
                this.onLoad()
            },
            editShow(index,show){
                this.$http('news_edit_show',{
                    id:this.datas[index].id,
                    show:show,
                }, true).then(res => {
                    this.$Message.success("设置成功")
                }).catch(error => {})
            },
            edit(index){
                if(this.datas[index].contentType===1) {
                    this.$router.push({
                        name: 'newsEditHtml',
                        query:{
                            id:this.datas[index].id,
                        },
                    })
                    return
                }
                if(this.datas[index].contentType===2) {
                    this.$router.push({
                        name: 'newsEditMarkdown',
                        query:{
                            id:this.datas[index].id,
                        },
                    })
                    return
                }
            },
            preview(index){
                if(this.datas[index].contentType==1){
                    //html
                    this.$refs.htmlPreviewComp.show(this.datas[index])
                    return
                }
                if(this.datas[index].contentType==2){
                    //markdown
                    this.$refs.markdownPreviewComp.show(this.datas[index])
                    return
                }
            },
        }
    }
</script>
