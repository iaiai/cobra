<style lang="less" scoped>

</style>

<template>
    <Drawer width="700" v-model="isShow" :closable="true">
        <Row class-name="fbold" slot="header">预览</Row>
        <Row class="p-content">
            <Row class="center">{{title}}</Row>
            <Row v-html="content"></Row>
        </Row>
    </Drawer>
</template>

<script>
    export default {
        data() {
            return {
                isShow:false,

                title:'',
                content:'',
                date:'',
            }
        },
        methods:{
            show(obj){
                this.title = obj.title
                this.content = obj.content
                this.isShow = true
            },
            hidden(){
                this.isShow = false
            },
        }
    }
</script>