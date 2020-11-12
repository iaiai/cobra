<style lang="less" scoped>

</style>

<template>
    <Drawer width="100" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">多个大文本框同时显示测试</Row>
        <Row class="p-content">
            <neditor :value="content" />

            <Row class-name="m-t-20">
                <Button type="primary" shape="circle" :loading="loading" @click="save">
                    <span v-if="!loading">
                        <i class="iconfont iaiai-correct f12" /> 保存
                    </span>
                    <span v-else>处理中...</span>
                </Button>
                <Button type="text" shape="circle" @click="hidden" class="m-l-20">
                    <i class="iconfont iaiai-multiply f12" /> 取消
                </Button>
            </Row>
        </Row>
    </Drawer>
</template>

<script>
    import neditor from '@/commons/components/neditor.vue'

    export default {
        components: {
            neditor,
        },
        data() {
            return {
                isShow:false,
                loading:false,

                content: '',
            }
        },
        methods:{
            show(){
                this.content = ""
                this.isShow = true
            },
            hidden(){
                this.isShow = false
            },
            save(){

            },
        }
    }
</script>