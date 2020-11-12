<style lang="less" scoped>
    .qr-right{
        position: absolute !important;
        top: 0;
        right: 0;
        bottom: 0;
        width: 50%;
    }

    .qr-left{
        position: absolute !important;
        top: 0;
        left: 0;
        bottom: 0;
        width: 50%;
    }
</style>

<template>
    <Row class="max-size">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>二维码</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="p-content">
            <Row class="qr-right center">
                <vue-qr :size="200" :text="content" :logoSrc="logo" :margin="0" />
            </Row>
            <Row class="qr-left right-outer-shadow p-20">
                <Upload
                        type="drag"
                        :action="uploadUrl"
                        :format="['jpg','jpeg','png']"
                        :max-size="524"
                        disabled
                        :on-success="handleSuccess"
                        :on-error="handleUploadError"
                        :on-format-error="handleFormatError"
                        :on-exceeded-size="handleExceededSizeError"
                        :headers="$root.getHeaderObj()"
                        class="m-b-10">
                    <div style="padding: 20px 0">
<!--                        <Icon type="ios-cloud-upload" size="52" style="color: #3399ff" />-->
                        <Icon type="ios-cloud-upload" size="52" class="color-ccc" />
                        <p class="color-ccc">点击 或 拖拽文件到这里上传logo(因有跨域问题，暂不支持上传)</p>
                    </div>
                </Upload>
                <Input v-model="content" type="textarea" :rows="14" show-word-limit />
            </Row>
        </Row>
    </Row>
</template>

<script>
    import api from '@/http/api'
    import VueQr from 'vue-qr'

    export default {
        components: { VueQr },
        data() {
            return {
                uploadUrl:api.uploadImg.url,

                content:'请输入二维码内容',
                logo: require("../../../assets/logo.png"),

                files:[],
            }
        },
        methods: {
            handleSuccess(res, file, fileList) {
                if(fileList.length>1) {
                    fileList.splice(0, 1)
                }

                this.logo = res.result.webUrl
            },

            handleUploadError(error, file, fileList){
                //文件上传失败
                this.$Message.error({
                    background: true,
                    content: '上传文件异常:'+error,
                    duration: 30,
                    closable:true,
                })
            },
            handleFormatError(file, fileList){
                this.$Message.error({
                    background: true,
                    content: '文件后缀错误'
                })
            },
            handleExceededSizeError(file, fileList){
                this.$Message.error({
                    background: true,
                    content: '文件超出大小限制'
                })
            },
        }
    }
</script>
