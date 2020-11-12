<style lang="less" scoped>

</style>


<template>
    <Drawer width="400" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">上传图片</Row>
        <Row class-name="p-content">
            <Row>
                <div v-for="item in imgs" :key="item.id" class="float-l">
                    <template>
                        <img :src="item.url">
                    </template>
                </div>

                <Upload
                        ref="upload"
                        class="center float-l"
                        :action="uploadUrl"
                        :show-upload-list="false"
                        :format="['jpg','jpeg','png']"
                        :max-size="2048"
                        type="drag"
                        :on-success="handleFaceSuccess"
                        :before-upload="beforeFaceUpload"
                        :headers="$root.getHeaderObj()">
                    <Row style="width:100px;height:100px;" class-name="center">
                        <Icon type="ios-camera" size="40" />
                    </Row>
                </Upload>
            </Row>
            <Row class-name="m-t-20">
                <Button type="primary" shape="circle" @click="save">
                    <i class="iconfont iaiai-correct f12" /> 保存
                </Button>
                <Button type="text" shape="circle" @click="hidden" class="m-l-20">
                    <i class="iconfont iaiai-multiply f12" /> 取消
                </Button>
            </Row>
        </Row>
    </Drawer>
</template>

<script>
    import api from '@/http/api'

    export default {
        data() {
            return {
                uploadUrl: api.uploadImg.url,
                isShow: false,

                imgs:[],

                visible: false,
                currentImg:null,
            }
        },
        methods: {
            show() {
                // this.imgs = []
                this.isShow = true
            },
            hidden() {
                this.isShow = false
            },
            save() {
                this.$emit("handle",this.imgs)
                this.hidden()
            },

            beforeFaceUpload(file) {
                const isJPG = file.type === 'image/jpeg'
                const isPNG = file.type === 'image/png'
                const isLt2M = file.size / 1024 / 1024 < 1

                if (!(isJPG || isPNG)) {
                    this.$Message.error('上传头像图片只能是 jpg或png 格式!')
                    return false
                }
                if (!isLt2M) {
                    this.$Message.error('上传图片大小不能超过 1MB!')
                    return false
                }

                this.$Spin.show()
                return true
            },
            handleFaceSuccess(res, file, fileList) {
                this.$Spin.hide()
                this.imgs.push({
                    url:res.result.webUrl,
                })
            },
        }
    }
</script>
