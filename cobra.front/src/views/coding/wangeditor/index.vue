<style lang="less" scoped>

</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>wangeditor</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class-name="p-content">
            <Row class-name="m-b-20">
                <Button icon="md-add" @click="add">多个大文本框同时显示测试</Button>
            </Row>
            <wangeditor ref="wangeditor" />
        </Row>

        <addComp ref="addComp"></addComp>
    </Row>
</template>

<script>
    import addComp from './add.vue'
    import wangeditor from '@/commons/components/wangeditor.vue'

    export default {
        components: {
            addComp,
            wangeditor,
        },
        data() {
            return {}
        },
        mounted() {
            this.onLoad()
        },
        methods: {
            onLoad() {
                this.$refs.wangeditor.show()
            },
            add(){
                this.$refs.addComp.show()
            },
        }
    }
</script>
