<!--
参考:http://www.wangeditor.com/
如果增加查看源码:http://www.wangeditor.com/doc/pages/01-%E5%BC%80%E5%A7%8B%E4%BD%BF%E7%94%A8/04-%E4%BD%BF%E7%94%A8textarea.html
-->

<style lang="less" scoped>

</style>

<template>
    <Row>
        <Row>
            <div ref="toolbar"></div>
        </Row>
        <div :id="id" ref="editor"></div>
    </Row>
</template>

<script>
    import api from '@/http/api'
    import E from 'wangeditor'

    export default {
        data() {
            return {
                editor: null,
                id:null,
            }
        },
        methods: {
            getuuid() {
                return (((1+Math.random())*0x10000)|0).toString(16).substring(1)
            },
            show() {
                this.id = this.getuuid()
                this.$nextTick(()=>{
                    this.editor = new E(this.$refs.toolbar,this.$refs.editor)
                    this.editor.config.zIndex = 10

                    this.editor.config.uploadImgShowBase64 = false // base 64 存储图片
                    this.editor.config.uploadImgServer = api.uploadImg.url// 配置服务器端地址
                    this.editor.config.uploadImgMaxSize = 1 * 1024 * 1024 // 将图片大小限制为 2M
                    this.editor.config.uploadImgTimeout = 3 * 60 * 1000 // 设置超时时间
                    this.editor.config.uploadFileName = 'file'
                    this.editor.config.debug = true
                    this.editor.config.uploadImgParams = {
                        type: 'image'
                    }

                    // 配置菜单
                    this.editor.config.menus = [
                        'head', // 标题
                        'bold', // 粗体
                        'fontSize', // 字号
                        'fontName', // 字体
                        'italic', // 斜体
                        'underline', // 下划线
                        'strikeThrough', // 删除线
                        'foreColor', // 文字颜色
                        'backColor', // 背景颜色
                        'link', // 插入链接
                        'list', // 列表
                        'justify', // 对齐方式
                        'quote', // 引用
                        'image', // 插入图片
                        'table', // 表格
                        'video', // 插入视频
                        'code', // 插入代码
                    ]

                    let that = this
                    this.editor.config.uploadImgHooks = {
                        fail: (xhr, editor, result) => {
                            // 插入图片失败回调
                        },
                        success: (xhr, editor, result) => {
                            // 图片上传成功回调
                        },
                        timeout: (xhr, editor) => {
                            // 网络超时的回调
                            that.$Message({
                                showClose: true,
                                message: "网络超时",
                                type: 'error',
                                duration:1000*10,
                            })
                        },
                        error: (xhr, editor) => {
                            // 图片上传错误的回调
                        },
                        customInsert: function (insertImg, result, editor) {
                            // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
                            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

                            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
                            if(result.code!=1000){
                                that.$Message.error({
                                    showClose: true,
                                    message: result.msg,
                                    type: 'error',
                                    duration:1000*10,
                                })
                                return
                            }

                            var url = result.result.webUrl
                            insertImg(url)

                            // result 必须是一个 JSON 格式字符串！！！否则报错
                        }
                    }
                    this.editor.config.onchange = (html)=>{
                        that.$emit("changeContent",html)
                    }

                    this.editor.create()
                })
            },
        }
    }
</script>
