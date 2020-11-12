<style lang="less" scoped>

</style>

<template>
    <Drawer width="100" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">多个大文本框同时显示测试</Row>
        <Row>
            <TinymceEditor ref="ty" :value="content" @onchange="onchange" @imagesHandle="imagesHandle"></TinymceEditor>
        </Row>
    </Drawer>
</template>

<script>
    import TinymceEditor from '@/commons/components/tinymce-editor.vue'
    import api from '@/http/api'

    export default {
        components: {
            TinymceEditor,
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
            onchange(value){
                this.content = value
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