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
        position: absolute !important;
        left:260px;
        top:0;
        right:0;
        bottom:0;
    }
</style>


<template>
    <Row class="max-height">
        <Row class="menu-right">
            <keep-alive>
                <transition name="fade" mode="out-in" appear>
                    <component v-bind:is="componeContent" :node="currentNode"></component>
                </transition>
            </keep-alive>
        </Row>

        <Row class="menu-left pr right-outer-shadow">
            <div class="btn center" @click="selectAll">全部</div>
            <Tree
                    :data="types"
                    class="f12 menu top-outer-shadow"
                    :render="renderTree" />
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
            selectAll(){
                this.currentNode = []
            },
        }
    }
</script>