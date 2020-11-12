<style lang="less" scoped>

</style>

<template>
    <Drawer width="100" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">多个大文本框同时显示测试</Row>
        <Row class="p-content">
            <wangeditor ref="wangeditor" />
        </Row>
    </Drawer>
</template>

<script>
    import wangeditor from '@/commons/components/wangeditor.vue'

    export default {
        components: {
            wangeditor,
        },
        data() {
            return {
                isShow:false,
            }
        },
        methods:{
            show(){
                this.isShow = true
                this.$refs.wangeditor.show()
            },
            save(){

            },
        }
    }
</script>