<style lang="less" scoped>

</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>CKEditor</BreadcrumbItem>
            </Breadcrumb>
        </Row>

        <Row>
            <Alert type="error">图片大小都不能调整，舍弃</Alert>
            <!-- 工具栏容器 -->
            <div id="toolbar-container"></div>

            <!-- 编辑器容器 -->
            <div id="editor">
                <p>This is the initial editor content.</p>
            </div>
        </Row>
    </Row>
</template>

<script>
    import CKEditor from '@ckeditor/ckeditor5-build-decoupled-document'
    import '@ckeditor/ckeditor5-build-decoupled-document/build/translations/zh-cn'
    import api from '@/http/api'
    import MyUploadAdapter from "./MyUploadAdapter.js"

    export default {
        components: {
            ckeditor: CKEditor.component
        },
        data() {
            return {
                editorDocument:null,
            }
        },
        mounted() {
            this.onLoad()
        },
        methods: {
            onLoad() {
                CKEditor.create(document.querySelector('#editor'), {
                    language: 'zh-cn',
                    ckfinder: {
                        uploadUrl: api.uploadImg.url
                        //后端处理上传逻辑返回json数据,包括uploaded(选项true/false)和url两个字段
                    },
                }).then(editor => {
                    const toolbarContainer = document.querySelector('#toolbar-container')
                    toolbarContainer.appendChild(editor.ui.view.toolbar.element)
                    this.editorDocument = editor //将编辑器保存起来，用来随时获取编辑器中的内容等，执行一些操作

                    editor.plugins.get("FileRepository").createUploadAdapter = loader => {
                        return new MyUploadAdapter(loader,this.$root)
                    }
                }).catch(error => {
                    console.error(error)
                })
            }
        }
    }
</script>
