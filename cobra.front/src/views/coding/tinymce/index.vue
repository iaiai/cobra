<!--
参考:https://github.com/tinymce
-->

<style lang="less" scoped>

</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>tinymce</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="p-content">
            <Row class-name="m-b-20">
                <Button icon="md-add" @click="add">多个大文本框同时显示测试</Button>
            </Row>
            <Row style="height: 300px;">
                <tinymce
                        ref="ty"
                        :value="content"
                        @onchange="onchange"
                        @imagesHandle="imagesHandle"/>
            </Row>
        </Row>

        <addComp ref="addComp"></addComp>
    </Row>
</template>

<script>
    import addComp from './add.vue'
    import tinymce from '@/commons/components/tinymce-editor.vue'
    import api from '@/http/api'

    export default {
        components: {
            tinymce,
            addComp,
        },
        data() {
            return {
                content:'默认值',
            }
        },
        mounted(){
            this.$nextTick(()=>{
                this.$refs.ty.initTinyMCE()
            })
        },
        methods: {
            onchange(value){
                this.content = value
            },
            add(){
                this.$refs.addComp.show()
            },
            imagesHandle(blobInfo, success, failure){
                // const img = 'data:image/jpeg;base64,' + blobInfo.base64()
                // success(img)

                const formData = new FormData()
                formData.append('file', blobInfo.blob())

                this.$uploadFile(api.uploadImg.url, formData).then(res => {
                    success(res.data.result.webUrl)
                }).catch(error => {
                    failure(error)
                })
            },
        }
    }
</script>
