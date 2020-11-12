<style lang="less" scoped>

</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>角色管理</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="p-content">
            <Row v-if="$root.isPermissions('system:role:add','system:role:del','system:role:export')">
                <Button
                        v-if="$root.isPermission('system:role:add')"
                        type="primary"
                        icon="md-add"
                        @click="add">添加</Button>
                <span v-if="$root.isPermission('system:role:del')" class="m-l-10">
                    <Poptip confirm placement="bottom" :title="`真的要删除这 ${delItem.length} 项`" @on-ok="dels()">
                        <Button type="error" icon="md-trash" :disabled="delItem.length<=0">删除</Button>
                    </Poptip>
                </span>
                <span v-if="$root.isPermission('system:role:export')" class="m-l-10">
                    <Poptip confirm placement="bottom" :title="`真的要导出这些角色?`" @on-ok="dataExport()">
                        <Button type="primary" icon="ios-cloud-download" :disabled="datas.length<=0">导出</Button>
                    </Poptip>
                </span>
            </Row>

            <Table
                    :data="datas"
                    border
                    size="small"
                    @on-selection-change="tableSelect"
                    class="m-t-10 table-small"
                    :columns="columns"
                    stripe>
                <template slot-scope="{ row, index }" slot="status">
                    <i-switch
                            v-model="row.status"
                            true-color="#19be6b"
                            :true-value="1"
                            :false-value="0"
                            @on-change="editStatus(index,row.status)"
                            :disabled="!$root.isPermission('system:role:edit:status')"/>
                </template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row,index }" slot="fun">
                    <Row>
                        <a href="javascript:;" @click="showPermission(index)">
                            <i class="iconfont iaiai-menu f12" /> 设置权限
                        </a>
                        <span v-if="$root.isPermission('system:role:edit')">
                            <Divider type="vertical" v-if="$root.isPermission('system:role:permission')"/>
                            <a href="javascript:;" @click="edit(index)">
                                <i class="ivu-icon ivu-icon-md-create" /> 编辑
                            </a>
                        </span>
                        <span v-if="$root.isPermission('system:role:del')">
                            <Divider type="vertical" v-if="$root.isPermissions('system:role:permission','system:role:edit')"/>
                            <Poptip
                                    confirm
                                    placement="left"
                                    :transfer="true"
                                    :title="`真的要删除此 [${row.name}] 角色`"
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

        <addComp ref="addComp" @addSuccess="addSuccess"></addComp>
        <editComp ref="editComp" @editSuccess="editSuccess"></editComp>
        <permissionComp ref="permissionComp" @editSuccess="editSuccess"></permissionComp>
    </Row>
</template>

<script>
    import addComp from './add'
    import editComp from './edit'
    import permissionComp from './permission'
    import methodMap from '../../../http/api'

    export default {
        components: {
            addComp,
            editComp,
            permissionComp,
        },
        data() {
            return {
                columns: this.initColumns(),
                datas: [],

                delItem: [],
            }
        },
        mounted() {
            this.getData()
        },
        methods: {
            initColumns(){
                let list = [
                    {title: '角色名', key: 'name', minWidth:130},
                    {title: '备注', key: 'remark', minWidth:130},
                    {title: '顺序', key: 'seq', width:80},
                    {title: '启用', slot: 'status', width:80},
                    {title: '创建时间', slot: 'createTime', width:160},
                ]

                if(this.$root.isPermission('system:role:del')) {
                    list.splice(0, 0, {type: 'selection', width: 40, align: 'center'})
                }

                if(this.$root.isPermissions('system:role:permission','system:role:edit','system:role:del')) {
                    list.push({
                        title: '操作',
                        slot: 'fun',
                        width:(this.$root.isPermission('system:role:permission')?90:0)+(this.$root.isPermission('system:role:edit')?65:0)+(this.$root.isPermission('system:role:del')?65:0),
                        fixed:'right',
                        align: 'center',
                    })
                }

                return list
            },
            getData() {
                this.$http('role_index').then(res => {
                    this.datas = res.result
                }).catch(error => {
                })
            },
            add() {
                this.$refs.addComp.show()
            },
            addSuccess() {
                this.getData()
            },
            del(index) {
                this.$http('role_del', {
                    id: this.datas[index].id,
                }, true).then(res => {
                    this.getData()
                }).catch(error => {
                })
            },
            dels() {
                var ids = ""
                for (var i = 0; i < this.delItem.length; i++) {
                    if (ids.length > 0) ids += ","
                    ids += this.delItem[i].id
                }

                this.$http('role_dels', {
                    id: ids,
                }, true).then(res => {
                    this.getData()
                }).catch(error => {
                })
            },
            tableSelect(n) {
                this.delItem = n
            },
            editStatus(index,status) {
                this.$http('role_edit_status', {
                    id: this.datas[index].id,
                    status: status,
                }, true).then(res => {
                    this.getData()
                })
            },
            edit(index) {
                this.$refs.editComp.show(this.datas[index])
            },
            editSuccess() {
                this.getData()
            },
            showPermission(index) {
                this.$refs.permissionComp.show(this.datas[index])
            },

            dataExport() {
                //导出角色数据
                this.$download(methodMap.role_export.url).then(res => {
                    this.$root.downloadFile(res, decodeURIComponent(res.headers['filename']))
                }).catch(error => {
                })
            },
        }
    }
</script>
