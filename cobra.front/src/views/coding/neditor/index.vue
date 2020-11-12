<style lang="less" scoped>

</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>neditor</BreadcrumbItem>
            </Breadcrumb>
        </Row>

        <Row class="p-content">
            <Alert type="error">此组件和ueditor风格冲突，如果先打开此界面，想再看ueditor请刷新界面先看那个</Alert>
            <Row class-name="m-b-20">
                <Button icon="md-add" @click="add">多个大文本框同时显示测试</Button>
            </Row>
            <neditor :value="content" @changeContent="changeContent" />

            <Row style="margin-top: 20px;">{{content}}</Row>
        </Row>

        <addComp ref="addComp"></addComp>
    </Row>
</template>

<script>
    //两个问题，弹出层堆叠问题，还有在当前页刷新后加载不出来的问题，不在当前页刷新没问题
    import neditor from '@/commons/components/neditor.vue'
    import addComp from './add.vue'

    export default {
        components: {
            neditor,
            addComp,
        },
        data() {
            return {
                content: '默认内容',
            }
        },
        methods:{
            add(){
                this.$refs.addComp.show()
            },
            changeContent(content){
                this.content = content
            },
        }
    }
</script>