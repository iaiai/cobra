<style lang="less" scoped>
    .icon{
        width:100%;
        height:100%;
        margin-top: 6px;
    }
</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem v-if="breadcrumbs.length<=0">
                    <Icon type="md-list-box"/> 全部
                </BreadcrumbItem>

                <BreadcrumbItem v-else v-for="breadcrumb in breadcrumbs" :key="breadcrumb.id">
                    <i :class="['f12',breadcrumb.icon]" /> {{breadcrumb.name}}
                </BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="p-content">
            <Row>
                <Button v-if="$root.isPermission('news:type:add')" type="primary" icon="md-add" @click="add">添加</Button>
                <span v-if="$root.isPermission('news:type:del')" class="m-l-10">
                    <Poptip confirm placement="right" :title="`真的要删除这 ${delItem.length} 项`" @on-ok="dels()">
                        <Button type="error" icon="md-trash" :disabled="delItem.length<=0"> 删除</Button>
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
                    <img class="icon" :src="row.icon" v-if="row.icon">
                </template>
                <template slot-scope="{ row, index }" slot="show">
                    <i-switch
                            v-if="row.type!=3"
                            v-model="row.show"
                            true-color="#19be6b"
                            :true-value="1"
                            :false-value="0"
                            @on-change="editShow(index,row.show)"
                            :disabled="!$root.isPermission('news:type:edit:show')"/>
                </template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row,index }" slot="fun">
                    <Row>
                        <a v-if="$root.isPermission('news:type:edit')" href="javascript:;" @click="edit(index)">
                            <Icon type="md-create" /> 编辑
                        </a>
                        <span v-if="$root.isPermission('news:type:del')">
                            <Divider type="vertical" v-if="$root.isPermission('news:type:edit')" />
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
        props:['node'],
        components: {
            addComp,
            editComp,
        },
        data() {
            return {
                datas:[],

                breadcrumbs:[],
                currentNode:null,

                columns: this.initColumns(),

                delItem:[],
            }
        },
        mounted(){
            this.setBreadcrumbs(this.node)
            this.getData()
        },
        watch:{
            node(to,form){
                this.setBreadcrumbs(to)
                this.getData()
            }
        },
        methods:{
            initColumns(){
                let list = [
                    {title: '图片', slot: 'icon', width:130},
                    {title: '菜单名', key: 'name', minWidth:130},
                    {title: '顺序', key: 'seq', width:70},
                    {title: '备注', key: 'remark', minWidth:220},
                    {title: '显示', slot: 'show', width:60},
                    {title: '创建时间', slot: 'createTime', width:160},
                ]

                if(this.$root.isPermission('news:type:del')) {
                    list.splice(0, 0, {type: 'selection', width: 40, align: 'center'})
                }
                if(this.$root.isPermissions('news:type:edit','news:type:del')) {
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
            all() {
                //查全部
                this.breadcrumbs = []
                this.getData()
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
            getData(){
                this.$http('news_type_list',{
                    parentId:this.currentNode?this.currentNode.id:null,
                }).then(res => {
                    this.datas = res.result
                }).catch(error => {})
            },
            edit(index){
                this.$refs.editComp.show(this.datas[index])
            },
            dels(){
                var ids = ""
                for(var i=0;i<this.delItem.length;i++){
                    if(ids.length>0)ids+=","
                    ids+=this.delItem[i].id
                }

                this.$http('news_type_dels',{
                    id:ids,
                }, true).then(res => {
                    this.getData()
                    this.$emit("delsSuccess",this.delItem)
                }).catch(error => {})
            },
            del(index){
                this.$http('news_type_del',{
                    id:this.datas[index].id,
                }, true).then(res => {
                    this.getData()
                    this.$emit("delSuccess",this.datas[index].id)
                }).catch(error => {})
            },
            add(){
                this.$refs.addComp.show()
            },
            addSuccess(type){
                //添加成功
                this.getData()
                this.$emit("addSuccess",type)
            },
            editSuccess(menu){
                //编辑成功
                this.getData()
                this.$emit("editSuccess",menu)
            },
            editShow(index,show){
                this.$http('news_type_edit_show',{
                    id:this.datas[index].id,
                    show:show,
                }, true).then(res => {
                    this.$Message.success("设置成功")
                }).catch(error => {})
            },

            tableSelect(n){
                console.log(n)
                this.delItem = n
            },
        }
    }
</script>
