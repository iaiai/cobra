<style lang="less" scoped>
    .input {
        width: 350px;
        font-size: 12px;
    }
</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem v-if="breadcrumbs.length>0">
                    <a href="javascript:;" @click="all"> 全部</a>
                </BreadcrumbItem>
                <BreadcrumbItem v-else>
                    <i class="f12 iconfont iaiai-people-dept"/> 全部
                </BreadcrumbItem>

                <BreadcrumbItem v-for="breadcrumb in breadcrumbs" :key="breadcrumb.id">
                    <i class="f12 iconfont iaiai-people-dept"/> {{breadcrumb.name}}
                </BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="p-content">
            <Row>
                <Input
                        placeholder="搜索登录名、姓名、手机号，按回车搜索..."
                        v-model="pageParams.search"
                        clearable
                        class="input m-r-10"
                        @keyup.enter.native="search"/>
                <Button type="primary" icon="md-search" @click="search">搜索</Button>
            </Row>
            <Row class="m-t-10">
                <span v-if="$root.isPermission('system:user:add')">
                    <Button type="primary" @click="add">
                        <i class="iconfont iaiai-add f12"></i> 添加
                    </Button>
                </span>
                <span v-if="$root.isPermission('system:user:del')" class="m-l-10">
                    <Poptip confirm placement="right" :title="`真的要删除这 ${delItem.length} 项`" @on-ok="dels()">
                        <Button type="error" :disabled="delItem.length<=0">
                            <i class="ivu-icon ivu-icon-md-trash"></i> 删除
                        </Button>
                    </Poptip>
                </span>
                <span v-if="$root.isPermission('system:user:export')" class="m-l-10">
                    <Poptip confirm placement="right" :title="`真的要导出这些用户?`" @on-ok="dataExport()">
                        <Button type="primary" :disabled="datas.length<=0">
                            <i class="iconfont iaiai-export f12"></i> 导出
                        </Button>
                    </Poptip>
                </span>
            </Row>

            <Table
                    :data="datas"
                    border
                    stripe
                    @on-selection-change="tableSelect"
                    size="small"
                    class="m-t-10 table-small"
                    :columns="columns">
                <template slot-scope="{ row }" slot="sex">
                    <span v-if="row.sex==1"><i class="ivu-icon ivu-icon-md-male f12"/> 男</span>
                    <span v-if="row.sex==2"><i class="ivu-icon ivu-icon-md-female f12"/> 女</span>
                    <span v-if="row.sex==0">未知</span>
                </template>
                <template slot-scope="{ row, index }" slot="status">
                    <i-switch
                            v-model="row.status"
                            true-color="#19be6b"
                            :true-value="1"
                            :false-value="0"
                            @on-change="editStatus(index,row.status)"
                            :disabled="!$root.isPermission('system:user:edit:status')"/>
                </template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row,index }" slot="fun">
                    <Row>
                        <a v-if="$root.isPermission('system:user:edit:pwd')" href="javascript:;" @click="editPassword(index)">
                            <Icon type="md-create"></Icon> 修改密码
                        </a>
                        <span v-if="$root.isPermission('system:user:edit')">
                            <Divider type="vertical" v-if="$root.isPermission('system:user:edit:pwd')"/>
                            <a href="javascript:;" @click="edit(index)">
                                <Icon type="md-create"></Icon> 编辑
                            </a>
                        </span>
                        <span v-if="$root.isPermission('system:user:del')">
                            <Divider type="vertical"
                                     v-if="$root.isPermissions('system:user:edit:pwd','system:user:edit')"/>
                            <Poptip
                                    confirm
                                    placement="left"
                                    :transfer="true"
                                    :title="`真的要删除此 [${row.username}] 用户`"
                                    @on-ok="del(index)">
                                <a href="javascript:;" class="color-red">
                                    <Icon type="md-trash" /> 删除
                                </a>
                            </Poptip>
                        </span>
                    </Row>
                </template>
            </Table>

            <Row class="center m-t-10">
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

        <addComp ref="addComp" @addSuccess="addSuccess"></addComp>
        <editComp ref="editComp" @editSuccess="editSuccess"></editComp>
        <editPasswordComp ref="editPasswordComp"></editPasswordComp>
    </Row>
</template>

<script>
    import addComp from './add'
    import editComp from './edit'
    import editPasswordComp from './edit_pwd'
    import methodMap from '../../../http/api'

    export default {
        props: ['node'],
        components: {
            addComp,
            editComp,
            editPasswordComp,
        },
        data() {
            return {
                breadcrumbs: [], //面包屑

                pageParams: {
                    page: 1,
                    limit: this.$root.$data.page.defaultLimit,
                    search: '',
                    total: 0,
                },
                columns: this.initColumns(),
                datas: [],

                delItem: [],
            }
        },
        watch: {
            node(to, form) {
                this.setBreadcrumbs(to)
                this.onLoad()
            },
        },
        mounted() {
            this.setBreadcrumbs(this.node)
            this.onLoad()
        },
        methods: {
            initColumns(){
                let list = [
                    {title: '登录名', key: 'username', minWidth:100},
                    {title: '姓名', key: 'nickname', minWidth:100},
                    {title: '手机号', key: 'phone', width:110},
                    {title: '性别', slot: 'sex', width:60},
                    {title: '部门', key: 'deptName', minWidth:150},
                    {title: '备注', key: 'remark', minWidth:150},
                    {title: '邮箱', key: 'email', minWidth:150},
                    {title: '状态', slot: 'status', width:80},
                    {title: '创建时间', slot: 'createTime', width:160},
                ]

                if(this.$root.isPermission('system:user:del')) {
                    list.splice(0, 0, {type: 'selection', width: 40, align: 'center'})
                }
                if(this.$root.isPermissions('system:user:edit:pwd','system:user:edit','system:user:del')) {
                    list.push({
                        title: '操作',
                        slot: 'fun',
                        width:(this.$root.isPermission('system:user:edit:pwd')?90:0)+(this.$root.isPermission('system:user:edit')?65:0)+(this.$root.isPermission('system:user:del')?65:0),
                        fixed:'right',
                        align: 'center',
                    })
                }

                return list
            },
            setBreadcrumbs(nodes) {
                this.breadcrumbs = []
                if (!nodes) {
                    return
                }

                this.breadcrumbs = Array.prototype.reverse.call(nodes)  //倒序
            },
            onLoad() {
                this.$http('user_index', {
                    deptId: this.breadcrumbs.length > 0 ? this.breadcrumbs[0].id : null,
                    page: this.pageParams.page,
                    limit: this.pageParams.limit,
                    search: this.pageParams.search,
                }).then(res => {
                    this.datas = res.result.records
                    this.pageParams.total = res.result.total
                })
            },
            search() {
                this.pageParams.page = 1
                this.onLoad()
            },
            changePageSize(limit) {
                this.pageParams.page = 1
                this.pageParams.limit = limit
                this.onLoad()
            },
            chagePage(page) {
                this.pageParams.page = page
                this.onLoad()
            },
            del(index) {
                this.$http('user_del', {
                    id: this.datas[index].id,
                }).then(res => {
                    this.onLoad()
                })
            },
            dels() {
                var ids = ""
                for (var i = 0; i < this.delItem.length; i++) {
                    if (ids.length > 0) ids += ","
                    ids += this.delItem[i].id
                }

                this.$http('user_dels', {id: ids}).then(res => {
                    this.onLoad()
                })
            },
            add() {
                this.$refs.addComp.show()
            },
            addSuccess() {
                //添加成功刷新数据
                this.datas = []
                this.onLoad()
            },
            edit(index) {
                this.$refs.editComp.show(this.datas[index])
            },
            editPassword(index) {
                this.$refs.editPasswordComp.show(this.datas[index].id)
            },
            editSuccess() {
                //编辑成功
                this.datas = []
                this.onLoad()
            },
            editStatus(index,status) {
                //编辑状态
                this.$http('user_edit_status', {
                    id: this.datas[index].id,
                    status: status,
                }, true).then(res => {
                    this.onLoad()
                })
            },
            all() {
                //查全部
                this.breadcrumbs = []
                this.onLoad()
            },
            tableSelect(n) {
                this.delItem = n
            },
            dataExport() {
                //导出
                this.$download(methodMap.user_export.url, {
                    deptId: this.breadcrumbs.length > 0 ? this.breadcrumbs[0].id : null,
                    page: this.pageParams.page,
                    limit: this.pageParams.limit,
                    search: this.pageParams.search,
                }).then(res => {
                    this.$root.downloadFile(res, decodeURIComponent(res.headers['filename']))
                })
            },
        }
    }
</script>
