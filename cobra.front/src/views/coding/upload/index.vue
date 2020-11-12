<style lang="less" scoped>
    .upload{
        width:200px;
        height: 200px;
    }
    .upload img{
        width:200px;
        height: 200px;

        margin-left: 5px;
        margin-right: 5px;
        margin-bottom: 5px;

        object-fit: cover;
    }
    .img{
        width:200px;
        height: 200px;
        margin-right: 5px;
        margin-bottom: 5px;

        object-fit: cover;
    }

    .close{
        position: absolute;
        top:0;
        right: 0;
    }
</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>上传文件</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class-name="p-content">
            <Upload
                    class="upload"
                    multiple
                    type="drag"
                    :format="['jpg','jpeg','png']"
                    :max-size="1"
                    :headers="header"
                    :show-upload-list="false"
                    :before-upload="handleBeforeUpload"
                    :on-success="handleSuccess"
                    :on-error="handleUploadError"
                    :on-format-error="handleFormatError"
                    :on-exceeded-size="handleExceededSizeError"
                    :action="action">
                <Row class="upload center" v-if="img==null">
                    <Row>
                        <Icon type="ios-cloud-upload" size="52" style="color: #3399ff" />
                        <p>显示单张图片</p>
                    </Row>
                </Row>
                <img v-else :src="img.url">
            </Upload>

            <Divider />

            <Row>
                <div v-for="(image,index) in imgs" class="float-l pr">
                    <img :src="image.url" class="img">

                    <div class="close" @click="removeImg(index)">关闭</div>
                </div>

                <Upload
                        class="upload float-l"
                        multiple
                        type="drag"
                        :format="['jpg','jpeg','png']"
                        :max-size="100"
                        :headers="header"
                        :show-upload-list="false"
                        :on-success="handleMultipleSuccess"
                        :action="action">
                    <Row class-name="center upload">
                        <Row>
                            <Icon type="ios-cloud-upload" size="52" style="color: #3399ff" />
                            <p>多张图片</p>
                        </Row>
                    </Row>
                </Upload>
            </Row>
        </Row>
    </Row>
</template>

<script>
    import api from '@/http/api'

    export default {
        data() {
            return {
                action:api.uploadImg.url,
                header:this.$root.getHeaderObj(),

                img:null,
                imgs:[{
                    url:"https://qjyp-oss.oss-cn-beijing.aliyuncs.com/test/images/157fc431160549a89d8555c71c716339.png",
                },{
                    url:"https://qjyp-oss.oss-cn-beijing.aliyuncs.com/test/images/157fc431160549a89d8555c71c716339.png",
                },],
            }
        },
        mounted() {
            this.onLoad()
        },
        methods: {
            onLoad() {

            },
            handleBeforeUpload(file){
                this.$Spin.show()
                return true
            },
            handleSuccess(res, file){
                this.$Spin.hide()

                this.img = {
                    url:res.result.webUrl,
                }
            },
            handleUploadError(error, file, fileList){
                //文件上传失败
                this.$Spin.hide()
                this.$Message.error({
                    background: true,
                    content: '上传文件异常:'+error,
                    duration: 30,
                    closable:true,
                })
            },
            handleFormatError(file, fileList){
                this.$Spin.hide()
                this.$Message.error({
                    background: true,
                    content: '文件后缀错误'
                })
            },
            handleExceededSizeError(file, fileList){
                this.$Spin.hide()
                this.$Message.error({
                    background: true,
                    content: '文件超出大小限制'
                })
            },

            handleMultipleSuccess(res, file){
                this.$Spin.hide()

                this.imgs.push({
                    url:res.result.webUrl,
                })
            },

            removeImg(index){
                this.imgs.splice(index,1)
            },
        },
    }
</script>
