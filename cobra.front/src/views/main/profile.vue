<style lang="less" scoped>
    .face{
        width:100px;
        height:100px;
        object-fit: cover;
    }
</style>

<template>
    <Drawer width="600" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">个人资料</Row>
        <Row>
            <Form ref="editSelfForm" :rules="rules" :model="form" :label-width="80">
                <Row class="center m-b-20">
                    <Upload
                            class="center"
                            :action="uploadUrl"
                            :show-upload-list="false"
                            :format="['jpg','jpeg','png']"
                            :max-size="2048"
                            type="drag"
                            :on-success="handleFaceSuccess"
                            :before-upload="handleBeforeUpload"
                            :on-error="handleUploadError"
                            :on-format-error="handleFormatError"
                            :on-exceeded-size="handleExceededSizeError"
                            :headers="$root.getHeaderObj()">
                        <Row class-name="center face">
                            <img v-if="form.face" :src="form.face" class="face">
                            <Icon v-else type="ios-camera" size="40" />
                        </Row>
                    </Upload>
                </Row>
                <FormItem label="姓名" prop="nickname">
                    <Input v-model.trim="form.nickname" placeholder="请输入姓名" />
                </FormItem>
                <FormItem label="手机号" prop="phone">
                    <Input v-model.trim="form.phone" placeholder="请输入手机号" readonly />
                </FormItem>
                <FormItem label="性别" prop="sex">
                    <ButtonGroup shape="circle">
                        <Button
                                :type="form.sex==0?'primary':'default'"
                                size="small"
                                icon="md-person"
                                @click="selSex(0)">未知</Button>
                        <Button
                                :type="form.sex==1?'primary':'default'"
                                size="small"
                                icon="md-male"
                                @click="selSex(1)">男</Button>
                        <Button
                                :type="form.sex==2?'primary':'default'"
                                size="small"
                                icon="md-female"
                                @click="selSex(2)">女</Button>
                    </ButtonGroup>
                </FormItem>
                <FormItem label="邮箱" prop="email">
                    <Input v-model.trim="form.email" placeholder="请输入邮箱" />
                </FormItem>
                <FormItem>
                    <Button type="primary" :loading="loading" @click="save">
                        <span v-if="!loading">
                            <i class="iconfont iaiai-correct f12" /> 保存
                        </span>
                        <span v-else>处理中...</span>
                    </Button>
                    <Button type="text" @click="hidden" class="m-l-20">
                        <i class="iconfont iaiai-multiply f12" /> 取消
                    </Button>
                </FormItem>
            </Form>
        </Row>
    </Drawer>
</template>

<script>
    import api from '@/http/api'
    import {isEmail} from '@/libs/validate.js'

    export default {
        data() {
            return {
                uploadUrl: api.uploadImg.url,

                isShow: false,
                loading: false,  //确定按钮

                form: {
                    nickname: '',
                    phone: '',
                    email: '',
                    sex: 0,
                    face:null,
                },

                rules: {
                    nickname: [
                        {required: true, message: '请输入姓名', trigger: 'blur'},
                    ],
                    email: [
                        {validator: isEmail, trigger: 'blur'},
                    ],
                },
            }
        },
        methods: {
            show() {
                this.isShow = true

                this.$http('self_detail').then(res => {
                    this.form.nickname = res.result.nickname
                    this.form.phone = res.result.phone
                    this.form.email = res.result.email
                    this.form.sex = res.result.sex
                    this.form.face = res.result.face
                })
            },
            hidden() {
                this.isShow = false
            },
            save() {
                this.$refs.editSelfForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('self_edit', this.form).then(res => {
                            this.loading = false

                            this.$Message.success('修改成功')

                            this.hidden()
                        }).catch(error => {
                            this.loading = false
                        })
                    }
                })
            },

            handleFaceSuccess(res, file, fileList) {
                this.$Spin.hide()
                this.form.face = res.result.webUrl
            },
            handleBeforeUpload(file){
                this.$Spin.show()
                return true
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

            selSex(sex) {
                this.form.sex = sex
            },
        }
    }
</script>