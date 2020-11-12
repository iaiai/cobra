<style lang="less" scoped>
    .icon-uploader {
        width: 178px;
        height: 178px;
    }
</style>

<template>
    <Row>
        <Drawer width="500" v-model="isShow" :mask-closable="false" :closable="true">
            <Row class-name="fbold" slot="header">编辑分类</Row>
            <Row>
                <Form ref="editNewsTypeForm" :rules="rules" :model="form" :label-width="100">
                    <FormItem v-if="currentNode" label="上级分类">
                        <Input
                                :value="currentNode.name"
                                :prefix="currentNode.icon"
                                disabled
                                placeholder="上级分类" />
                    </FormItem>
                    <FormItem prop="name" label="分类名">
                        <Input v-model.trim="form.name" placeholder="请输入分类名" />
                    </FormItem>
                    <Row>
                        <Col :span="12">
                            <FormItem prop="seq" label="顺序">
                                <InputNumber :min="1" v-model="form.seq" />
                            </FormItem>
                        </Col>
                        <Col :span="12">
                            <Checkbox v-model="form.show" :true-value="1" :false-value="0">是否显示</Checkbox>
                        </Col>
                    </Row>
                    <FormItem prop="icon" v-show="form.type!=3" label="图标">
                        <Upload
                                class="icon-uploader"
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
                            <Row class-name="center icon-uploader">
                                <img v-if="form.icon" :src="form.icon" class="icon-uploader">
                                <Icon v-else type="ios-camera" size="40" />
                            </Row>
                        </Upload>
                    </FormItem>
                    <FormItem label="备注">
                        <Input
                                type="textarea"
                                :autosize="{ minRows: 2, maxRows: 4}"
                                v-model="form.remark"
                                placeholder="请输入备注" />
                    </FormItem>
                    <FormItem>
                        <Button type="primary" :loading="loading" shape="circle" @click="save" class="m-r-20">
                            <span v-if="!loading">
                                <i class="iconfont iaiai-correct f12" /> 保存
                            </span>
                            <span v-else>处理中...</span>
                        </Button>
                        <Button type="text" @click="hidden" shape="circle">
                            <i class="iconfont iaiai-multiply f12" /> 取消
                        </Button>
                    </FormItem>
                </Form>
            </Row>
        </Drawer>
    </Row>
</template>

<script>
    import api from '@/http/api'

    export default {
        props:['currentNode'],
        data() {
            return {
                uploadUrl:api.uploadImg.url,

                isShow:false,
                loading:false,  //确定按钮

                form:{
                    id:null,
                    name:'',
                    remark:'',
                    seq:1,
                    icon:'',
                    show:1,
                },

                rules: {
                    seq: [
                        { required: true, type:'number', message: '请输入顺序', trigger: 'blur' },
                    ],
                    name: [
                        { required: true, message: '请输入名称', trigger: 'blur' },
                    ],
                },
            }
        },
        methods:{
            show(obj){
                this.form.id = obj.id
                this.form.name = obj.name
                this.form.seq = obj.seq
                this.form.icon = obj.icon
                this.form.show = obj.show
                this.form.remark = obj.remark

                this.isShow = true
            },
            hidden(){
                this.isShow = false
            },
            save(){
                this.$refs.editNewsTypeForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('news_type_edit',this.form, true).then(res => {
                            this.loading = false
                            this.$Message.success("编辑成功")

                            this.$emit("editSuccess",this.form)
                            this.hidden()
                        }).catch(error => {
                            this.loading = false
                        })
                    }
                })
            },
            beforeUpload(file){
                this.$Spin.show()
                return true
            },
            handleSuccess(res, file, fileList) {
                this.$Spin.hide()
                this.form.icon = res.result.webUrl
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
        }
    }
</script>