<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>岗位管理</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class-name="p-content">
            <Row class="post-toolbar" v-if="$root.isPermissions('system:post:add','system:post:del')">
                <Button v-if="$root.isPermission('system:post:add')" icon="md-add" type="primary" @click="add">
                    添加
                </Button>
                <span v-if="$root.isPermission('system:post:del')" class="m-l-10">
                    <Poptip
                            confirm
                            placement="right"
                            :transfer="true"
                            :title="`真的要删除这 ${delItem.length} 项`"
                            @on-ok="dels()">
                        <Button type="error" icon="md-trash" :disabled="delItem.length<=0">删除</Button>
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
                            :disabled="!$root.isPermission('system:post:edit:status')"/>
                </template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row,index }" slot="fun">
                    <Row>
                        <a v-if="$root.isPermission('system:post:edit')" @click="edit(index)" href="javascript:;">
                            <i class="ivu-icon ivu-icon-md-create" /> 编辑
                        </a>
                        <span v-if="$root.isPermission('system:post:del')">
                            <Divider type="vertical" v-if="$root.isPermission('system:post:edit')" />
                            <Poptip
                                    confirm
                                    placement="left"
                                    :transfer="true"
                                    :title="`真的要删除此 [${row.name}] 岗位`"
                                    @onConfirm="del(index)">
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
        components: {
            addComp,
            editComp,
        },
        data() {
            return {
                datas:[],
                delItem:[],
                columns: this.initColumns(),
            }
        },
        mounted(){
            this.onLoad()
        },
        methods:{
            initColumns(){
                let list = [
                    {title: '岗位名', key: 'name', minWidth:130},
                    {title: '编码', key: 'code', minWidth:130},
                    {title: '备注', key: 'remark', minWidth:130},
                    {title: '顺序', key: 'seq', width:80},
                    {title: '启用', slot: 'status', minWidth:80},
                    {title: '联系电话', key: 'phone', width:110},
                    {title: '邮箱', key: 'email', minWidth:150},
                    {title: '创建时间', slot: 'createTime', width:160},
                ]

                if(this.$root.isPermission('system:post:del')) {
                    list.splice(0, 0, {type: 'selection', width: 40, align: 'center'})
                }
                if(this.$root.isPermissions('system:post:edit','system:post:del')) {
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
            onLoad(){
                this.$http('post_index').then(res => {
                    this.datas = res.result
                }).catch(error => {})
            },
            add(){
                this.$refs.addComp.show()
            },
            addSuccess(){
                this.onLoad()
            },
            editSuccess(){
                this.onLoad()
            },
            edit(index){
                this.$refs.editComp.show(this.datas[index])
            },
            editStatus(index,status){
                this.$http('post_edit_status',{
                    id:this.datas[index].id,
                    status:status,
                }, true).then(res => {
                    this.onLoad()
                })
            },
            del(index){
                this.$http('post_del',{
                    id:this.datas[index].id,
                }, true).then(res => {
                    this.onLoad()
                }).catch(error => {})
            },
            dels(){
                var ids = ""
                for(var i=0;i<this.delItem.length;i++){
                    if(ids.length>0)ids+=","
                    ids+=this.delItem[i].id
                }

                this.$http('post_dels',{
                    id:ids,
                }, true).then(res => {
                    this.onLoad()
                }).catch(error => {})
            },
            tableSelect(n){
                this.delItem = n
            },
        }
    }
</script>
