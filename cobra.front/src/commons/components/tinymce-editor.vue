<style lang="less">

</style>

<template>
    <Row style="height:100%;">
        <!--        不能再用tinymce-vue，重复显示时会有问题-->
        <!--        <editor v-model="myValue"-->
        <!--                :init="init"-->
        <!--                :disabled="disabled"-->
        <!--                @onClick="onClick">-->
        <!--        </editor>-->
        <textarea :id="id">{{value}}</textarea>

        <uploadMultipleImagesComp ref="uploadMultipleImagesComp" @handle="insertImages" />
    </Row>
</template>

<script>
    /**
     toolbar---------
     newdocument（新文档）
     bold（加粗）
     italic（斜体）
     underline（下划线）
     strikethrough（删除线）
     alignleft（左对齐）
     aligncenter（居中对齐）
     alignright（右对齐）
     alignjustify（两端对齐）
     styleselect（格式设置）
     formatselect（段落格式）
     fontselect（字体选择）
     fontsizeselect（字号选择）
     cut（剪切）
     copy（复制）
     paste（粘贴）
     bullist（项目列表UL）
     numlist（编号列表OL）
     outdent（减少缩进）
     indent（增加缩进）
     blockquote（引用）
     undo（撤销）
     redo（重做/重复）
     removeformat（清除格式）
     subscript（下角标）
     superscript（上角标）
     */

    import tinymce from 'tinymce/tinymce' //tinymce默认hidden，不引入不显示
    import 'tinymce/themes/silver'
    import 'tinymce/icons/default'
    // 编辑器插件plugins
    // 更多插件参考：https://www.tiny.cloud/docs/plugins/
    import 'tinymce/plugins/image'// 插入上传图片插件
    import 'tinymce/plugins/media'// 插入视频插件
    import 'tinymce/plugins/table'// 插入表格插件
    import 'tinymce/plugins/lists'// 列表插件
    import 'tinymce/plugins/wordcount'// 字数统计插件
    import "tinymce/plugins/link"
    import "tinymce/plugins/code"
    import "tinymce/plugins/paste"
    import "tinymce/plugins/charmap"
    import "tinymce/plugins/anchor"
    import "tinymce/plugins/autosave"
    import "tinymce/plugins/advlist"
    // import "tinymce/plugins/fullpage"   //html全有
    import "tinymce/plugins/fullscreen"
    import "tinymce/plugins/hr"
    import "tinymce/plugins/imagetools"
    import "tinymce/plugins/insertdatetime"
    import "tinymce/plugins/nonbreaking"    //插入&nbsp;空格
    import "tinymce/plugins/pagebreak"  //分页符
    import "tinymce/plugins/preview"    //预览
    import "tinymce/plugins/print"
    import "tinymce/plugins/quickbars"  //快捷菜单，选中字之后弹出菜单
    import "tinymce/plugins/searchreplace"  //搜索替换
    // import "tinymce/plugins/spellchecker"   //拼写检查，这个用不了
    import "tinymce/plugins/toc"    //内容列表
    import "tinymce/plugins/visualblocks"   //显示区块边框
    import "tinymce/plugins/visualchars"    //显示不可见字符
    import "tinymce/plugins/autoresize"    //编辑器大小自适应
    import "tinymce/plugins/directionality"    //文字方向

    import uploadMultipleImagesComp from './tinymce_upload_multiple_images'

    export default {
        props: {
            value: {
                type: String,
                default: ''
            },
            height: {
                type: String,
                default: '100%'
            },
            minHeight: {
                type: String,
                default: '100%'
            },
            disabled: {
                type: Boolean,
                default: false
            },
            zIndex:{
                type:Number,
                default:1,
            },
        },
        components: {
            uploadMultipleImagesComp,
        },
        name:'tinymce',
        data() {
            return {
                id:this.getuuid(),

                init: {
                    selector:'',
                    language_url: '/tinymce/langs/zh_CN.js',    //如果语言包不存在，指定一个语言包路径
                    language: 'zh_CN',

                    autoresize_bottom_margin: 0,    //距离底部0距离

                    skin_url: '/tinymce/skins/ui/oxide',    //如果主题不存在，指定一个主题路径
                    // skin_url: 'tinymce/skins/ui/oxide-dark',//暗色系
                    theme: "silver", //主题

                    height: this.height,
                    min_height:this.minHeight,
                    resize: true,   //调整编辑器大小工具
                    quickbars_insert_toolbar: false,    //空白行是否显示插入图片的快捷工具栏
                    quickbars_selection_toolbar: false,  //选中字之后是否显示快捷工具栏
                    // contextmenu: "copy cut paste",  //右键菜单
                    //autoresize 编辑器的高度随内容自变化，不需要的话去掉这个即可
                    plugins: 'autoresize directionality nonbreaking visualchars visualblocks toc searchreplace quickbars print pagebreak insertdatetime imagetools hr fullscreen advlist lists image media table wordcount preview autolink link code charmap anchor autosave blockquote upload-multiple-images',
                    autoresize_min_height: '500px', //编辑区域的最小高度
                    autoresize_on_init: true,
                    toolbar: [
                        'preview code fullscreen | undo redo cut copy paste searchreplace | formatselect fontselect fontsizeselect | bold italic underline strikethrough subscript superscript forecolor backcolor | ltr rtl | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent blockquote | lists image media table insertdatetime | removeformat | upload-multiple-images',
                    ],
                    fontsize_formats: '8px 10px 12px 14px 16px 18px 24px 36px 48px 56px 72px',
                    font_formats: '微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;',
                    branding: false, //技术支持(Powered by Tiny || 由Tiny驱动)
                    menubar: false,  //菜单栏
                    // 此处为图片上传处理函数，这个直接用了base64的图片形式上传图片，
                    // 如需ajax上传可参考https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_handler
                    images_upload_handler: (blobInfo, success, failure) => {
                        // const img = 'data:image/jpeg;base64,' + blobInfo.base64()
                        // success(img)
                        this.$emit('imagesHandle', blobInfo, success, failure)
                    },

                    // object_resizing: false,

                    zIndex:this.zIndex,
                },
                myValue: this.value
            }
        },
        watch: {
            value (newValue) {
                this.myValue = newValue
            },
        },
        mounted() {
            // let that = this
            // tinymce.PluginManager.add('upload-multiple-images', function(editor, url) {
            //     editor.ui.registry.addButton('upload-multiple-images', {
            //         text: '插入多张图片',
            //         onAction() {
            //             that.$refs.uploadMultipleImagesComp.show()
            //         }
            //     })
            // })

            this.initTinyMCE()
        },
        methods:{
            getuuid() {
                return (((1+Math.random())*0x10000)|0).toString(16).substring(1)
            },
            destroyTinyMCE(){
                tinymce.editors[this.id].destroy()
            },
            initTinyMCE(){
                this.id = this.getuuid()
                this.$nextTick(()=>{
                    this.init.selector = "#"+this.id
                    tinymce.init(this.init)

                    if(tinymce && tinymce.editors[this.id] && this.value) {
                        tinymce.editors[this.id].setContent(this.value)
                    }
                })
            },
            getContent(){
                return tinymce.editors[this.id].getContent()
            },
            setContent(content){
                this.myValue=content

                if(tinymce && tinymce.editors[this.id]) {
                    tinymce.editors[this.id].setContent(content)
                }
            },
            insertImages(imgs){
                //插入多张图片
                if(imgs.length>0) {
                    for (let i = 0; i < imgs.length; i++) {
                        let img = "<img src='" + imgs[i] + "' style='width:100%;display: block; margin-left: auto; margin-right: auto;'>"
                        // tinymce.editors[this.id].insertContent(img)
                        tinymce.editors[this.id].execCommand('mceInsertContent', false, img)
                    }
                }
            },
        },
    }
</script>
