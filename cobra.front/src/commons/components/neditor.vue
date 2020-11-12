<style lang="less" scoped>

</style>

<template>
    <vue-neditor-wrap v-model="content" :config="myConfig" :destroy="false" />
</template>

<script>
    import VueNeditorWrap from 'vue-neditor-wrap'
    import api from '@/http/api'

    export default {
        components: {
            VueNeditorWrap,
        },
        props: {
            value: {
                type: String,
                default: '',
            },
            autoHeightEnabled: {
                type: Boolean,
                default: false,
            },
        },
        watch: {
            content (newValue) {
                this.$emit('changeContent',newValue)
            },
        },
        data() {
            return {
                myConfig: {
                    // 如果需要上传功能,找后端小伙伴要服务器接口地址
                    // serverUrl: this.$root.$data.baseURL + api.uploadImg.url,
                    serverUrl: api.uploadImg.url,
                    // 你的UEditor资源存放的路径,相对于打包后的index.html
                    UEDITOR_HOME_URL: '/NEditor-next-master/',
                    // 编辑器不自动被内容撑高
                    autoHeightEnabled: this.autoHeightEnabled,
                    // 初始容器高度
                    initialFrameHeight: 240,
                    // 初始容器宽度
                    initialFrameWidth: '100%',
                    // 关闭自动保存
                    enableAutoSave: false,

                    zIndex:888,

                },
                content: this.value,
            }
        },
        methods: {

        },
    }
</script>
