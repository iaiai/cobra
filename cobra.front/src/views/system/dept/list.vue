<style lang="less" scoped>
    .dept-toolbar{
        position: absolute;
        top:0;
        left:0;
        right:0;
        height: 40px;
        line-height: 40px;
        background-color: white;
        padding-left: 16px;

        .toolbar-right{
            position: absolute;
            right:16px;
            top:0;
            bottom:0;
        }
    }

    .dept-list-content{
        position: absolute;
        left:-1px;
        right:0;
        top:40px;
        bottom:0;
        overflow-y: auto;
    }
</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem v-for="breadcrumb in breadcrumbs" :key="breadcrumb.id">
                    <i class="f12 iconfont iaiai-people-dept" /> {{breadcrumb.name}}
                </BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="p-content">
            <Row>
                <Button
                        v-if="$root.isPermission('system:dept:add')"
                        type="primary"
                        icon="md-add"
                        @click="add">添加</Button>
                <span v-if="$root.isPermission('system:dept:del')" class="m-l-10">
                    <Poptip
                            confirm
                            placement="right"
                            :transfer="true"
                            :title="`真的要删除这 ${delItem.length} 项`"
                            @on-ok="dels()">
                        <Button type="error" :disabled="delItem.length<=0" icon="md-trash">删除</Button>
                    </Poptip>
                </span>
            </Row>

            <Table
                    :data="datas"
                    border
                    stripe
                    size="small"
                    @on-selection-change="tableSelect"
                    class="m-t-10 table-small"
                    :columns="columns">
                <template slot-scope="{ row,index }" slot="status">
                    <i-switch
                            v-model="row.status"
                            true-color="#19be6b"
                            :true-value="1"
                            :false-value="0"
                            @on-change="editStatus(index,row.status)"
                            :disabled="!$root.isPermission('system:dept:edit:status')"/>
                </template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row,index }" slot="fun">
                    <Row>
                        <a v-if="$root.isPermission('system:dept:edit')" @click="edit(index)" href="javascript:;">
                            <i class="ivu-icon ivu-icon-md-create" /> 编辑
                        </a>
                        <span v-if="$root.isPermission('system:dept:del')">
                            <Divider type="vertical" v-if="$root.isPermission('system:dept:edit')" />
                            <Poptip
                                    confirm
                                    placement="left"
                                    :transfer="true"
                                    :title="`真的要删除此 [${row.name}] 部门`"
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
    </Row>
</template>

<script>
    import addComp from './add'
    import editComp from './edit'

    export default {
        props:['node'],
        components: {
            addComp,
            editComp,
        },
        data() {
            return {
                datas:[],
                columns: this.initColumns(),

                breadcrumbs:[],
                currentNode:null,

                delItem:[],
            }
        },
        watch:{
            node(to,form){
                this.setBreadcrumbs(to)
                this.onLoad()
            }
        },
        mounted(){
            this.setBreadcrumbs(this.node)
            this.onLoad()
        },
        methods:{
            initColumns(){
                let list = [
                    {title: '部门名', key: 'name', minWidth:130},
                    {title: '顺序', key: 'seq', width:70},
                    {title: '状态', slot: 'status', width:80},
                    {title: '负责人', key: 'leader', minWidth:120},
                    {title: '联系电话', key: 'phone', width:110},
                    {title: '邮箱', key: 'email', minWidth:150},
                    {title: '备注', key: 'remark', minWidth:130},
                    {title: '创建时间', slot: 'createTime', width:160},
                ]

                if(this.$root.isPermission('system:dept:del')) {
                    list.splice(0, 0, {type: 'selection', width: 40, align: 'center'})
                }
                if(this.$root.isPermissions('system:dept:edit','system:dept:del')) {
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
                this.$http('dept_list',{
                    id:this.currentNode.id,
                }).then(res => {
                    this.datas = res.result
                })
            },
            editStatus(index,status){
                this.$http('dept_edit_status',{
                    id:this.datas[index].id,
                    status:status,
                }, true).then(res => {
                    this.onLoad()
                })
            },
            add(){
                this.$refs.addComp.show(this.breadcrumbs[this.breadcrumbs.length-1])
            },
            tableSelect(n){
                this.delItem = n
            },
            addSuccess(dept){
                this.onLoad()
                this.$emit("addSuccess",dept)
            },
            editSuccess(dept){
                this.onLoad()
                this.$emit("editSuccess",dept)
            },
            edit(index){
                this.$refs.editComp.show(this.breadcrumbs[this.breadcrumbs.length-1],this.datas[index])
            },
            del(index){
                this.$http('dept_del',{
                    id:this.datas[index].id,
                }, true).then(res => {
                    this.onLoad()
                    this.$emit("delSuccess",this.datas[index].id)
                }).catch(error => {})
            },
            dels(){
                var ids = ""
                for(var i=0;i<this.delItem.length;i++){
                    if(ids.length>0)ids+=","
                    ids+=this.delItem[i].id
                }

                this.$http('dept_dels',{
                    id:ids,
                }, true).then(res => {
                    this.onLoad()
                    this.$emit("delsSuccess",this.delItem)
                }).catch(error => {})
            },
        }
    }
</script>
