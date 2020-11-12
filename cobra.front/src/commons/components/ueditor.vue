<!--
参考:
https://blog.csdn.net/haochuan9421/article/details/81975966
http://ueditor.gitrr.com/#/
-->
<template>
    <Row>
        <VueUeditorWrap
                :config="config"
                v-model="content"
                @ready="ready"
                :destroy="true"
                @beforeInit="addCustomButtom"/>

        <Drawer title="上传图片" v-model="isShowUploadImage" width="400" :transfer="false" style="z-index: 9999;">
            <Row class-name="f12 m-b-20">
                <i-switch v-model="isMultiple" class="m-r-10" /> 是否可选多张图片上传
            </Row>
            <Upload
                    :multiple="isMultiple"
                    type="drag"
                    :format="['jpg','jpeg','png']"
                    :max-size="uploadMaxSize"
                    :headers="header"
                    :show-upload-list="false"
                    :before-upload="handleBeforeUpload"
                    :on-success="handleSuccess"
                    :on-error="handleUploadError"
                    :on-format-error="handleFormatError"
                    :on-exceeded-size="handleExceededSizeError"
                    :action="action">
                <Row class-name="center" style="height:400px;">
                    <Row>
                        <Icon type="ios-cloud-upload" size="52" style="color: #3399ff" />
                        <p>Click or drag files here to upload</p>
                    </Row>
                </Row>
            </Upload>
            <Row class-name="m-t-20">
                <Input v-model="imgTemplate" type="textarea" placeholder="请输入图片显示格式..." />
            </Row>
        </Drawer>
    </Row>
</template>

<script>
    import VueUeditorWrap from 'vue-ueditor-wrap'
    import api from '@/http/api'

    export default {
        components: {
            VueUeditorWrap
        },
        props: {
            value: {
                type: String,
                default: ''
            },
            imgTemplate: {
                type: String,
                default: '<img src="#img">'
            },
            autoHeightEnabled: {
                type: Boolean,
                default: false
            },
            isMultiple: {
                type: Boolean,
                default: false
            },
            uploadMaxSize: {
                type: Number,
                default: 1024,   //单位 kb
            },
            topOffset: {
                type: Number,
                default: 150,   //浮动时工具栏距离浏览器顶部的高度，用于某些具有固定头部的页面
            },
        },
        data() {
            return {
                config:{
                    // 编辑器不自动被内容撑高
                    autoHeightEnabled: this.autoHeightEnabled,
                    //是否可以拉伸长高,默认true(当开启时，自动长高失效)
                    scaleEnabled:false,
                    //是否保持toolbar的位置不动,默认true
                    autoFloatEnabled:true,
                    //浮动时工具栏距离浏览器顶部的高度，用于某些具有固定头部的页面
                    topOffset:this.topOffset,
                    // 初始容器高度
                    //编辑器底部距离工具栏高度(如果参数大于等于编辑器高度，则设置无效)
                    //,toolbarTopOffset:400
                    initialFrameHeight: '300',
                    // 初始容器宽度
                    initialFrameWidth: '100%',
                    // 上传文件接口, 报错属于正常，若需要验证可使用(也是盗大神的)http://35.201.165.105:8000/controller.php
                    // 调试完毕打包上线则切换回/static/UEditor/php/controller.php即可，不用做其他处理
                    // serverUrl: '/static/UEditor/php/controller.php',
                    // serverUrl: 'http://35.201.165.105:8000/controller.php',
                    serverUrl: api.uploadImg.url,
                    UEDITOR_HOME_URL: '/ueditor/',

                    toolbars: [[
                        'fullscreen', 'source', '|', 'undo', 'redo', '|',
                        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                        'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                        'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                        'directionalityltr', 'directionalityrtl', 'indent', '|',
                        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                        'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                        'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'insertframe', 'insertcode', 'pagebreak', 'template', 'background', '|',
                        'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                        'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                        'searchreplace', 'drafts', 'help'
                    ]], //如果想看所有的，请到ueditor.config.js文件中找
                },

                content: this.value,
                editorInstance:null,

                isShowUploadImage:false,

                action:api.uploadImg.url,
                header:this.$root.getHeaderObj(),
            }
        },
        watch: {
            content (newValue) {
                this.$emit('changeContent',newValue)
            },
        },
        methods: {
            ready (editorInstance) {
                this.editorInstance = editorInstance
                console.log(`编辑器实例${editorInstance.key}: `, editorInstance)
            },
            editContent(value){
                this.content = value
                this.$emit('changeContent',value)
            },
            addCustomButtom(editorId){
                let that = this
                UE.registerUI(
                    "test-button",
                    function(editor, uiName) {
                        // 注册按钮执行时的 command 命令，使用命令默认就会带有回退操作
                        editor.registerCommand(uiName, {
                            execCommand: function() {
                                that.isShowUploadImage = true
                            }
                        })
                        // 创建一个 button
                        var btn = new UE.ui.Button({
                            // 按钮的名字
                            name: uiName,
                            // 提示
                            title: "单图上传",
                            // 需要添加的额外样式，可指定 icon 图标，图标路径参考常见问题 2
                            cssRules: "background-position: -380px 0px;",
                            // 点击时执行的命令
                            onclick: function() {
                                // 这里可以不用执行命令，做你自己的操作也可
                                editor.execCommand(uiName)
                            }
                        })
                        // 当点到编辑内容上时，按钮要做的状态反射
                        editor.addListener("selectionchange", function() {
                            var state = editor.queryCommandState(uiName);
                            if (state === -1) {
                                btn.setDisabled(true)
                                btn.setChecked(false)
                            } else {
                                btn.setDisabled(false)
                                btn.setChecked(state)
                            }
                        })
                        // 因为你是添加 button，所以需要返回这个 button
                        return btn
                    },
                    56 /* 指定添加到工具栏上的哪个位置，默认时追加到最后 */,
                    editorId /* 指定这个 UI 是哪个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮 */
                )
            },

            handleBeforeUpload(file){
                this.$Spin.show()
                return true
            },
            handleSuccess(res, file){
                this.$Spin.hide()

                this.editorInstance.execCommand('inserthtml', this.imgTemplate.replace("#img",res.result.webUrl));
                this.isShowUploadImage = false
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
                console.log(file)

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
        }
    }
</script>
