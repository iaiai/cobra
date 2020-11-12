<style lang="less" scoped>
    .cover-uploader {
        width: 200px;
        height: 150px;
    }
</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>
                    <a href="javascript:;" @click="back">
                        <Icon type="md-list-box" /> 新闻列表
                    </a>
                </BreadcrumbItem>

                <BreadcrumbItem>
                    <Icon type="md-list-box"/> 编辑新闻
                </BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class-name="p-content">
            <Form ref="editNewsForm" :rules="rules" :model="form" :label-width="100" class="m-t-20 m-b-100">
                <FormItem label="封面">
                    <Upload
                            class="cover-uploader"
                            :action="uploadUrl"
                            :show-upload-list="false"
                            :format="['jpg','jpeg','png']"
                            :max-size="2048"
                            type="drag"
                            :before-upload="beforeUpload"
                            :on-success="handleSuccess"
                            :on-error="handleUploadError"
                            :on-format-error="handleFormatError"
                            :on-exceeded-size="handleExceededSizeError"
                            :headers="$root.getHeaderObj()">
                        <Row class-name="center cover-uploader">
                            <img v-if="form.cover" :src="form.cover" class="cover-uploader">
                            <Icon v-else type="ios-camera" size="40" />
                        </Row>
                    </Upload>
                </FormItem>
                <FormItem prop="typeId" label="分类">
                    <Poptip placement="bottom-start" width="300">
                        <Input v-model.trim="form.typeName" readonly placeholder="请选择分类" />
                        <Row slot="content">
                            <Tree
                                    :data="types"
                                    class="f12"
                                    :render="renderTree" />
                            <div v-if="treeLoading" class="loading center"><Spin></Spin></div>
                        </Row>
                    </Poptip>

                    <Checkbox v-model="form.show" :true-value="1" :false-value="0" class="m-l-100">是否显示</Checkbox>
                </FormItem>
                <FormItem prop="title" label="标题">
                    <Input v-model.trim="form.title" placeholder="请输入标题" style="width: 500px;" />
                </FormItem>
                <FormItem prop="releaseStartTime" label="发布时间">
                    <DatePicker
                            :value="form.releaseStartTime"
                            type="datetime"
                            :transfer="true"
                            @on-change="releaseStartChange"
                            format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择开始日期时间" />
                    ~
                    <DatePicker
                            :value="form.releaseEndTime"
                            @on-change="releaseEndChange"
                            type="datetime"
                            :transfer="true"
                            format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择结束日期时间" />
                </FormItem>
                <FormItem prop="content">
                    <tinymce
                            ref="ty"
                            :value="form.content"
                            @onchange="onchange"
                            height="500px"
                            minHeight="500px" />
                </FormItem>
                <FormItem class="p-t-20">
                    <Button type="primary" :loading="loading" @click="save" shape="circle" class="m-r-20">
                        <span v-if="!loading">
                            <i class="iconfont iaiai-correct f12" /> 保存
                        </span>
                        <span v-else>处理中...</span>
                    </Button>
                    <Button type="text" @click="back" shape="circle">
                        <i class="iconfont iaiai-multiply f12" /> 取消
                    </Button>
                </FormItem>
            </Form>
        </Row>
    </Row>
</template>

<script>
    import tinymce from '@/commons/components/tinymce-editor'
    import api from '@/http/api'

    export default {
        components: {
            tinymce,
        },
        data() {
            return {
                uploadUrl:api.uploadImg.url,
                editorId:null,
                editor:null,

                loading:false,  //确定按钮

                form:{
                    id:this.$route.query.id,
                    content:'',
                    title:'',
                    show:1,
                    releaseStartTime:'',
                    releaseEndTime:'',
                    typeId:'',
                    typeName:'',
                    cover:'',
                    contentType:1,
                },

                rules:{
                    typeId: [
                        { required: true, message: '分类不允许为空', trigger: 'blur' },
                    ],
                    title: [
                        { required: true, message: '请输入标题', trigger: 'blur' },
                        { type: 'string', max: 100, message: '标题不允许大于100个字', trigger: 'blur' },
                    ],
                    content: [
                        { required: true, message: '请输入内容', trigger: 'blur' },
                    ],
                },

                types:[],
                treeLoading:true,
            }
        },
        mounted() {
            this.onLoad()
        },
        methods:{
            back(){
                this.$router.go(-1)
            },
            onchange(value){
                this.form.content = value
            },
            onLoad(){
                this.treeLoading = true
                this.$http('news_type_index').then(res => {
                    this.treeLoading = false
                    this.types = res.result

                    this.getData()
                }).catch(error => {
                    this.treeLoading = false
                })
            },
            getData(){
                this.$http('news_detail',{
                    id:this.form.id
                }).then(res => {
                    this.form.id = res.result.id
                    this.form.title = res.result.title
                    this.form.content = res.result.content
                    this.form.contentType = res.result.contentType
                    this.form.show = res.result.show
                    this.form.typeId = res.result.typeId
                    this.form.typeName = res.result.typeName
                    if(res.result.releaseStartTime) {
                        this.form.releaseStartTime = this.$formatTimestamp(res.result.releaseStartTime, "yyyy-MM-dd hh:mm:ss")
                    }else{
                        this.form.releaseStartTime = ''
                    }
                    if(res.result.releaseEndTime) {
                        this.form.releaseEndTime = this.$formatTimestamp(res.result.releaseEndTime, "yyyy-MM-dd hh:mm:ss")
                    }else{
                        this.form.releaseEndTime = ''
                    }
                    this.form.cover = res.result.cover

                    this.$refs.ty.setContent(this.form.content)
                })
            },
            save(){
                this.form.content = this.$refs.ty.getContent()
                this.$refs.editNewsForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('news_edit',this.form).then(res => {
                            this.loading = false

                            this.$Message.success("编辑成功")

                            this.$bus.$emit('news-index:ref')
                        }).catch(error => {
                            this.loading = false
                        })
                    }
                })
            },
            renderTree(h, { root, node, data }){
                return h('span', {
                    style: {
                        display: 'inline-block',
                        width: '100%',
                        fontSize: '12px'
                    },
                }, [
                    h('span',{
                        on: {
                            click: () => {
                                this.selectTree(data)
                            },
                        },
                    }, [
                        h('span', data.name),
                    ]),
                ])
            },
            selectTree(node){
                this.form.typeId = node.id
                this.form.typeName = node.name

                //单个字段去掉错误信息
                this.$refs.editNewsForm.validateField('typeId')
            },
            beforeUpload(file){
                this.$Spin.show()
                return true
            },
            handleSuccess(res, file, fileList) {
                this.$Spin.hide()
                this.form.cover = res.result.webUrl
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
            releaseStartChange(value){
                this.form.releaseStartTime = value
            },
            releaseEndChange(value){
                this.form.releaseEndTime = value
            },
        }
    }
</script>