<style lang="less" scoped>
    .menu-list-toolbar {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 40px;
        line-height: 40px;
        background-color: white;
        padding-left: 16px;
    }

    .menu-list-content {
        position: absolute;
        left: 0;
        right: 0;
        top: 40px;
        bottom: 0;
        overflow-y: auto;
    }
</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12" v-if="breadcrumbs.length>0">
                <BreadcrumbItem v-for="breadcrumb in breadcrumbs" :key="breadcrumb.id">
                    <i :class="['f12',breadcrumb.icon]"/>
                    {{breadcrumb.name}}
                </BreadcrumbItem>
            </Breadcrumb>

            <Breadcrumb class="f12" v-else>
                <BreadcrumbItem>顶层</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="p-content">
            <Row>
                <Button
                        v-if="$root.isPermission('system:menu:add')"
                        type="primary"
                        icon="md-add"
                        @click="add"> 添加</Button>
                <span v-if="$root.isPermission('system:menu:del')" class="m-l-10">
                    <Poptip
                            confirm
                            placement="right"
                            :transfer="true"
                            :title="`真的要删除这 ${delItem.length} 项`"
                            @on-ok="dels()">
                        <Button type="error" :disabled="delItem.length<=0">
                            <i class="ivu-icon ivu-icon-md-trash" /> 删除
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
                <template slot-scope="{ row }" slot="icon">
                    <i :class="row.icon" />
                </template>
                <template slot-scope="{ row }" slot="type">
                    <Tag color="default" v-if="row.type==1">目录</Tag>
                    <Tag color="success" v-if="row.type==2">菜单</Tag>
                    <Tag color="warning" v-if="row.type==3">功能</Tag>
                    <Tag color="error" v-if="row.type==4">子页面</Tag>
                </template>
                <template slot-scope="{ row,index }" slot="show">
                    <span v-if="row.type!=3 && row.type!=4">
                        <i-switch v-model="row.show"
                                  true-color="#19be6b"
                                  :true-value="1"
                                  :false-value="0"
                                  @on-change="editShow(index,row.show)"
                                  v-if="row.type!=3"
                                  :disabled="!$root.isPermission('system:menu:edit:status')"/>
                    </span>
                </template>
                <template slot-scope="{ row }" slot="cache">
                    <span v-if="row.type==2 || row.type==4">
                        <span v-if="row.cache==0">不缓存</span>
                        <span v-if="row.cache==1">缓存</span>
                    </span>
                </template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row,index }" slot="fun">
                    <Row>
                        <a v-if="$root.isPermission('system:menu:edit')" @click="edit(index)" href="javascript:;">
                            <i class="ivu-icon ivu-icon-md-create" /> 编辑
                        </a>
                        <span v-if="$root.isPermission('system:menu:del')">
                            <Divider type="vertical" v-if="$root.isPermission('system:menu:edit')"/>
                            <Poptip
                                    confirm
                                    placement="left"
                                    :transfer="true"
                                    :title="`真的要删除此 [${row.name}] ${row.type==1?'目录？':''}${row.type==2?'菜单?':''}${row.type==3?'功能':''}`"
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

        <addComp ref="addComp" @addSuccess="addSuccess" :currentNode="currentNode"></addComp>
        <editComp ref="editComp" @editSuccess="editSuccess" :currentNode="currentNode"></editComp>
    </Row>
</template>

<script>
    import addComp from './add'
    import editComp from './edit'

    export default {
        props: ['node'],
        components: {
            addComp,
            editComp,
        },
        data() {
            return {
                datas: [],

                breadcrumbs: [],
                currentNode: null,

                delItem: [],
                columns: this.initColumns(),
            }
        },
        mounted() {
            this.setBreadcrumbs(this.node)
            this.getData()
        },
        watch: {
            node(to, form) {
                this.setBreadcrumbs(to)
                this.getData()
            }
        },
        methods: {
            initColumns(){
                let list = [
                    {title: '#', slot: 'icon', width:40},
                    {title: '菜单名', key: 'name', minWidth:130},
                    {title: '菜单编号', key: 'code', minWidth:240},
                    {title: '类型', slot: 'type', width:80},
                    {title: '顺序', key: 'seq', width:70},
                    {title: 'URL', key: 'url', minWidth:260},
                    {title: '显示', slot: 'show', width:60},
                    {title: '缓存', slot: 'cache', width:60},
                    {title: '备注', key: 'remark', minWidth:400},
                    {title: '创建时间', slot: 'createTime', width:160},
                ]

                if(this.$root.isPermission('system:menu:del')) {
                    list.splice(0, 0, {type: 'selection', width: 40, align: 'center'})
                }
                if(this.$root.isPermissions('system:menu:edit','system:menu:del')) {
                    list.push({
                        title: '操作',
                        slot: 'fun',
                        width: 130,
                        fixed:'right',
                        align: 'center',
                    })
                }

                return list
            },
            setBreadcrumbs(nodes) {
                this.breadcrumbs = []
                this.currentNode = null
                if (!nodes) {
                    return
                }

                this.currentNode = nodes[0]
                this.breadcrumbs = Array.prototype.reverse.call(nodes)  //倒序
            },
            getData() {
                this.$http('menu_index', {
                    parentId: this.currentNode ? this.currentNode.id : null,
                }).then(res => {
                    this.datas = res.result
                })
            },
            edit(index) {
                this.$refs.editComp.show(this.datas[index])
            },
            dels() {
                var ids = ""
                for (var i = 0; i < this.delItem.length; i++) {
                    if (ids.length > 0) ids += ","
                    ids += this.delItem[i].id
                }

                this.$http('menu_dels', {
                    id: ids,
                }, true).then(res => {
                    this.getData()
                    this.$emit("delsSuccess", this.delItem)
                }).catch(error => {
                })
            },
            del(index) {
                this.$http('menu_del', {
                    id: this.datas[index].id,
                }, true).then(res => {
                    this.getData()
                    this.$emit("delSuccess", this.datas[index].id)
                })
            },
            add() {
                this.$refs.addComp.show()
            },
            addSuccess(menu) {
                //添加成功
                this.getData()
                this.$emit("addSuccess", menu)
            },
            editSuccess(menu) {
                //编辑成功
                this.getData()
                this.$emit("editSuccess", menu)
            },
            editShow(index,status) {
                this.$http('menu_edit_show', {
                    id: this.datas[index].id,
                    show: status,
                }, true).then(res => {
                    this.$Message.success("设置成功")
                    this.getData()
                })
            },

            tableSelect(n) {
                this.delItem = n
            },
        }
    }
</script>
