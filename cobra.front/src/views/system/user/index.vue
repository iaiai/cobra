<style lang="less" scoped>
    @_dept_width: 240px;

    .dept {
        position: absolute !important;
        left: 0px;
        top: 0px;
        bottom: 0px;
        width: @_dept_width;
        z-index: 2;

        background-color: white;
    }

    .content {
        position: absolute !important;
        top: 0px;
        left: @_dept_width;
        right: 0px;
        bottom: 0px;
    }
</style>

<template>
    <Row class="max-height">
        <Row class="content">
            <keep-alive>
                <transition name="fade" mode="out-in" appear>
                    <component v-bind:is="componeContent" :node="currentDept"></component>
                </transition>
            </keep-alive>
        </Row>

        <Row class="dept right-outer-shadow p-l-10 p-r-10">
            <Tree
                    :data="depts"
                    class="f12"
                    :render="renderTree" />
            <Spin v-if="loading" fix />
        </Row>
    </Row>
</template>

<script>
    import listComp from './list.vue'

    export default {
        data() {
            return {
                componeContent: listComp,

                loading: true,
                depts: [],

                currentDept: [],
            }
        },
        mounted() {
            this.onLoad()
        },
        methods: {
            onLoad() {
                // if (this.$root.isPermission('system:dept:query')) {
                    this.loading = true

                    this.$http('dept_query').then(res => {
                        this.loading = false

                        if(res.result!=null && res.result.length>0){
                            for(var i=0;i<res.result.length;i++){
                                res.result[i].expand = true
                            }
                        }

                        this.depts = res.result
                    }).catch(error => {
                        this.loading = false
                    })
                // } else {
                //     this.loading = false
                // }
            },
            selectTree(dept) {
                this.currentDept = [dept]
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
        }
    }
</script>
