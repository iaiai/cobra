<style lang="less" scoped>
    .right-line{
        border-right: 1px solid #eee !important;
    }

    .menu-left{
        position: absolute !important;
        left:0px;
        top:0px;
        bottom:0px;
        width:260px;
        background-color: white;
        overflow-y: auto;
        overflow-x:hidden;
        z-index: 2;

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
            <div class="btn center cursor-pointer" @click="selectMenu">顶层菜单管理</div>
            <Tree
                    :data="menus"
                    class="f12 menu top-outer-shadow"
                    :render="renderTreeContent"
                    @on-select-change="selTree" />
            <Spin v-if="loading" fix />
        </Row>
    </Row>
</template>

<script>
    import listComp from './list.vue'

    export default {
        data() {
            return {
                menus:[],
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
                this.$http('menu_all').then(res => {
                    this.loading = false
                    this.menus = res.result
                }).catch(error => {
                    this.loading = false
                })
            },
            renderTreeContent(h, { root, node, data }){
                return h('span', {
                    style: {
                        display: 'inline-block',
                        width: '100%'
                    }
                }, [
                    h('span', [
                        h('i', {
                            class:data.icon,
                            style: {
                                marginRight: '8px'
                            }
                        }),
                        h('span', data.name)
                    ]),
                ])
            },
            selectMenu(){
                this.currentNode = []
            },
            selTree(node){
                if(node==null || node.length<=0)return

                this.currentNode = []

                this.currentNode.push(node[0])
                this.getTreeParent(this.menus, node[0])
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
            addsuccess(menu){
                menu.loading = false
                menu.children = []

                //如果树没数据直接添加一个
                if(this.menus.length<=0){
                    this.$set(this.menus,this.menus.length,menu)
                    return
                }

                //如果没有父级，则只循环顶层即可
                if(!menu.parentId){
                    for(var i=0;i<this.menus.length;i++){
                        if(menu.seq<this.menus[i].seq){
                            //插入到这里
                            this.menus.splice(i,0,menu)
                            //排序
                            this.menus.sort((a,b)=>{
                                return a.seq - b.seq
                            })
                            break
                        }
                    }
                    return
                }else{
                    this.addTree(this.menus,menu)
                }
            },
            addTree(menus,menu){
                for(var i=0;i<menus.length;i++){
                    if(menu.parentId==menus[i].id) {
                        //插入到这里
                        if(menus[i].children) {
                            menus[i].children.push(menu)

                            //排序
                            menus[i].children.sort((a,b)=>{
                                return a.seq - b.seq
                            })
                        }else{
                            menus[i].children = [menu]
                        }

                        break
                    }

                    if(menus[i].children){
                        this.addTree(menus[i].children,menu)
                    }
                }
            },

            delSuccess(id){
                //菜单删除成功
                this.delTreeMenu(this.menus,null,id)
            },
            delsSuccess(ms){
                for(var i=0;i<ms.length;i++){
                    this.delTreeMenu(this.menus,null,ms[i].id)
                }
            },
            delTreeMenu(menus,parentMenu,id){
                for(var i=0;i<menus.length;i++){
                    if(menus[i].id==id){
                        if(parentMenu){
                            //有上层，需要判断平级数量
                            menus.splice(i,1)
                        }else{
                            //无上级，是第一层
                            this.menus.splice(i,1)
                        }
                        break
                    }

                    if(menus[i].children && menus[i].children.length>0){
                        this.delTreeMenu(menus[i].children,menus[i],id)
                    }
                }
            },

            editSuccess(menu){
                //编辑成功
                this.editTreeMenu(this.menus,menu)
            },
            editTreeMenu(menus,menu){
                for(var i=0;i<menus.length;i++){
                    if(menus[i].id==menu.id){
                        menus[i].name = menu.name
                        menus[i].icon = menu.icon
                        menus[i].seq = menu.seq

                        //排序，这里好像没用
                        menus.sort((a,b)=>{
                            return a.seq - b.seq
                        })
                        break
                    }

                    if(menus[i].children && menus[i].children.length>0){
                        this.editTreeMenu(menus[i].children,menu)
                    }
                }
            },
        }
    }
</script>