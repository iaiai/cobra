<style lang="less" scoped>

</style>

<template>
    <Drawer width="100" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">多个大文本框同时显示测试</Row>
        <Row class="p-content">
            <ueditor ref="ueditor" :value="content" @changeContent="changeContent"/>
        </Row>
    </Drawer>
</template>

<script>
    import ueditor from '@/commons/components/ueditor.vue'

    export default {
        components: {
            ueditor,
        },
        data() {
            return {
                isShow:false,

                content:'默认值',
            }
        },
        methods:{
            show(){
                this.isShow = true
            },
            changeContent(newValue){
                this.content = newValue
            },
        }
    }
</script>