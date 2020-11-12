<style lang="less" scoped>
    .menu-left{
        position: absolute;
        left:0px;
        top:0px;
        bottom:0px;
        width:260px;
        background-color: white;
        overflow-y: auto;
        overflow-x:hidden;
        z-index: 2;

        .dept{
            .icon{
                color:#aaa;
                font-size: 12px;
            }
        }
    }

    .menu-right{
        position: absolute;
        left:260px;
        top:0px;
        right:0px;
        bottom:0px;
        overflow-y: auto;
    }
</style>

<template>
    <Row class="max-height">
        <Row class="menu-right">
            <keep-alive>
                <transition name="fade" mode="out-in" appear>
                    <component v-bind:is="componeContent" :node="currentNode" @addSuccess="addsuccess" @delSuccess="delSuccess" @delsSuccess="delsSuccess" @editSuccess="editSuccess"></component>
                </transition>
            </keep-alive>
        </Row>

        <Row class="menu-left right-outer-shadow">
            <Tree
                    :data="depts"
                    class="f12 dept m-l-10"
                    :render="renderTreeContent"
                    @on-select-change="selectTree" />
            <Spin v-if="loading" fix />
        </Row>
    </Row>
</template>

<script>
    import listComp from './list.vue'

    export default {
        data() {
            return {
                componeContent:null,
                loading:true,
                currentNode:[],

                depts:[],
            }
        },
        mounted(){
            this.onLoad()
        },
        methods:{
            renderTreeContent(h, { root, node, data }){
                return h('span', {
                    style: {
                        display: 'inline-block',
                        width: '100%'
                    }
                }, [
                    h('span', [
                        h('span', data.name)
                    ]),
                ])
            },
            onLoad(){
                this.loading = true
                this.$http('dept_index').then(res => {
                    this.loading = false
                    this.depts = res.result
                }).catch(error => {
                    this.loading = false
                })
            },
            addsuccess(dept){
                this.addTree(this.depts,dept)
            },
            addTree(depts,dept){
                for(var i=0;i<depts.length;i++){
                    if(depts[i].id==dept.parentId){
                        if(depts[i].children && depts[i].children.length>0){
                            depts[i].children.push(dept)

                            //排序
                            depts[i].children.sort((a,b)=>{
                                return a.seq - b.seq
                            })
                        }else{
                            depts[i].children = [dept]
                        }

                        break
                    }

                    if(depts[i].children && depts[i].children.length>0){
                        this.addTree(depts[i].children,dept)
                    }
                }
            },
            delSuccess(id){
                this.delTree(this.depts,null,id)
            },
            delTree(depts,parentDept,id){
                for(var i=0;i<depts.length;i++){
                    if(depts[i].id==id){
                        if(parentDept){
                            //有上层，需要判断平级数量
                            depts.splice(i,1)
                        }else{
                            //无上级，是第一层
                            this.depts.splice(i,1)
                        }
                        break
                    }

                    if(depts[i].children && depts[i].children.length>0){
                        this.delTree(depts[i].children,depts[i],id)
                    }
                }
            },
            delsSuccess(dps){
                for(var i=0;i<dps.length;i++){
                    this.delTree(this.depts,null,dps[i].id)
                }
            },
            editSuccess(dept){
                this.editTree(this.depts,dept)
            },
            editTree(depts,dept){
                for(var i=0;i<depts.length;i++){
                    if(depts[i].id==dept.id){
                        depts[i].name = dept.name
                        depts[i].seq = dept.seq

                        //排序，这里好像没用
                        depts[i].children.sort((a,b)=>{
                            return a.seq - b.seq
                        })
                        break
                    }

                    if(depts[i].children && depts[i].children.length>0){
                        this.editTree(depts[i].children,dept)
                    }
                }
            },
            selectTree(node){
                if(node==null || node.length<=0)return

                this.currentNode = []

                this.currentNode.push(node[0])
                this.getTreeParent(this.depts, node[0])

                this.componeContent = listComp
            },
            getTreeParent(ms,node){
                var bol = false
                for (var i=0;i<ms.length;i++){
                    if(ms[i].id==node.parentId){
                        bol = true
                        this.currentNode.push(ms[i])
                        break
                    }

                    if(ms[i].children && ms[i].children.length>0) {
                        var b = this.getTreeParent(ms[i].children,node)
                        if(b){
                            bol = true
                            this.currentNode.push(ms[i])
                            break
                        }
                    }
                }
                return bol
            },
        }
    }
</script>
