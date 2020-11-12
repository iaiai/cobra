<!--
参考: https://blog.csdn.net/haochuan9421/article/details/81975966
-->
<style lang="less" scoped>

</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>ueditor</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class-name="p-content">
            <Alert type="error">此组件和neditor风格冲突，如果先打开此界面，想再看neditor请刷新界面先看那个</Alert>
            <ueditor
                    ref="ueditor"
                    :value="content"
                    @changeContent="changeContent"
                    :isMultiple="true"
                    :autoHeightEnabled="false"/>

            <Row class-name="m-t-20">{{content}}</Row>

            <Row class-name="m-t-20">
                <Button @click="editContent">修改内容</Button>
                <Button icon="md-add" @click="add" class="m-l-20">多个大文本框同时显示测试</Button>
            </Row>
        </Row>

        <addComp ref="addComp" />
    </Row>
</template>

<script>
    import addComp from './add.vue'
    import ueditor from '@/commons/components/ueditor.vue'

    export default {
        components: {
            ueditor,
            addComp,
        },
        data() {
            return {
                content:'aaaaaaaaaaaaaaaaaaaaaaaa<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf<br/>fdskalfjdsalf111111<br/>fdskalfjdsalf111111<br/>fdskalfjdsalf111111<br/>fdskalfjdsalf111111<br/>fdskalfjdsalf111111<br/>fdskalfjdsalf111111<br/>fdskalfjdsalf111111<br/>fdskalfjdsalf111111<br/>fdskalfjdsalf111111<br/>',
            }
        },
        methods: {
            ready (editorInstance) {
                console.log(`编辑器实例${editorInstance.key}: `, editorInstance)
            },
            editContent(){
                this.$refs.ueditor.editContent("这是修改后的内容")
            },
            changeContent(newValue){
                this.content = newValue
            },
            add(){
                this.$refs.addComp.show()
            },
        }
    }
</script>
