<style lang="less" scoped>
    .right-line{
        border-right: 1px solid #eee !important;
    }

    .menu-left{
        z-index: 2;
        position: absolute !important;
        left:0px;
        top:0px;
        bottom:0px;
        width:260px;
        background-color: white;
        overflow-y: auto;
        overflow-x:hidden;

        .btn{
            position: absolute;
            top:0px;
            left:0px;
            right:0px;
            height:40px;
            font-size: 12px;
        }
        .btn:hover{
            background-color: #fafafa;
        }

        .menu{
            position: absolute;
            top:40px;
            left:0px;
            right:0px;
            z-index:0;

            padding:10px 20px;

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

        <Row class="menu-left pr right-outer-shadow">
            <div class="btn center" @click="selectMenu">顶层类型管理</div>
            <Tree
                    :data="types"
                    class="f12 menu top-outer-shadow"
                    :render="renderTree"/>
            <div v-if="loading" class="loading center"><Spin></Spin></div>
        </Row>
    </Row>
</template>

<script>
    import listComp from './list.vue'

    export default {
        data() {
            return {
                types:[],
                componeContent:listComp,
                currentNode:[],

                loading:true,
            }
        },
        mounted(){
            this.onLoad()
        },
        methods:{
            onLoad(){
                this.loading = true
                this.$http('news_type_index').then(res => {
                    this.loading = false
                    this.types = res.result
                }).catch(error => {
                    this.loading = false
                })
            },
            selectMenu(){
                this.currentNode = []
            },
            renderTree(h, { root, node, data }){
                return h('span', {
                    style: {
                        display: 'inline-block',
                        width: '100%',
                        fontSize: '12px'
                    },
                }, [
                    h('span',{
                        on: {
                            click: () => {
                                this.selectTree(data)
                            },
                        },
                    }, [
                        h('span', data.name),
                    ]),
                ])
            },
            selectTree(node){
                this.currentNode = []

                this.currentNode.push(node)
                this.getTreeParent(this.types, node)
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

            //添加成功
            addsuccess(type){
                this.loading = false
                type.children = []

                //如果树没数据直接添加一个
                if(this.types.length<=0){
                    this.$set(this.types,this.types.length,type)
                    return
                }

                //如果没有父级，则只循环顶层即可
                if(!type.parentId){
                    //这里计算有问题
                    this.types.push(type)
                    //排序
                    this.types.sort((a,b)=>{
                        return a.seq - b.seq
                    })
                    return
                }else{
                    this.addTree(this.types,type)
                }
            },
            addTree(types,type){
                for(var i=0;i<types.length;i++){
                    if(type.parentId==types[i].id) {
                        //插入到这里
                        if(types[i].children) {
                            types[i].children.push(type)

                            //排序
                            types[i].children.sort((a,b)=>{
                                return a.seq - b.seq
                            })
                        }else{
                            types[i].children = [type]
                        }

                        break
                    }

                    if(types[i].children){
                        this.addTree(types[i].children,type)
                    }
                }
            },

            delSuccess(id){
                //菜单删除成功
                this.delTreeMenu(this.types,null,id)
            },
            delsSuccess(ts){
                for(var i=0;i<ts.length;i++){
                    this.delTreeMenu(this.types,null,ts[i].id)
                }
            },
            delTreeMenu(types,parentType,id){
                for(var i=0;i<types.length;i++){
                    if(types[i].id==id){
                        if(parentType){
                            //有上层，需要判断平级数量
                            types.splice(i,1)
                        }else{
                            //无上级，是第一层
                            this.types.splice(i,1)
                        }
                        break
                    }

                    if(types[i].children && types[i].children.length>0){
                        this.delTreeMenu(types[i].children,types[i],id)
                    }
                }
            },

            editSuccess(type){
                //编辑成功
                this.editTreeMenu(this.types,type)
            },
            editTreeMenu(types,type){
                for(var i=0;i<types.length;i++){
                    if(types[i].id==type.id){
                        types[i].name = type.name
                        types[i].icon = type.icon
                        types[i].seq = type.seq

                        //排序，这里好像没用
                        types.sort((a,b)=>{
                            return a.seq - b.seq
                        })
                        break
                    }

                    if(types[i].children && types[i].children.length>0){
                        this.editTreeMenu(types[i].children,type)
                    }
                }
            },
        }
    }
</script>