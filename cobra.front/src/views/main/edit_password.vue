<style lang="less" scoped>
    .face{
        width:100px;
        height:100px;
    }
</style>

<template>
    <Drawer width="400" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">修改密码</Row>
        <Row>
            <Row class="m-b-10">
                <Alert show-icon class="f12">
                    <Icon type="ios-bulb-outline" slot="icon"></Icon>
                    密码必须8位或8位以上，并且必须包含大小写字母和数字和特殊字符 ~!@#$%^&*()_+|<>,.?/:;'[]{}
                </Alert>
            </Row>
            <Form ref="editPasswordForm" :rules="rules" :model="form" :label-width="100">
                <FormItem label="密码" prop="password">
                    <Input type="password" :password="true" v-model.trim="form.password" placeholder="请输入密码" />
                </FormItem>
                <FormItem label="新密码" prop="newPassword">
                    <Input type="password" :password="true" v-model.trim="form.newPassword" placeholder="请输入新密码" />
                </FormItem>
                <FormItem label="再输入新密码" prop="confirmPassword">
                    <Input type="password" :password="true" v-model.trim="form.confirmPassword" placeholder="请再输入一遍新密码" />
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
    import {checkPasswordRule} from '@/libs/validate.js'

    export default {
        data() {
            return {
                isShow: false,
                loading: false,  //确定按钮

                form: {
                    password: '',
                    newPassword: '',
                    confirmPassword: '',
                },

                rules: {
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {validator: checkPasswordRule, trigger: 'blur'},
                    ],
                    newPassword: [
                        {required: true, message: '请输入新密码', trigger: 'blur'},
                        {validator: checkPasswordRule, trigger: 'blur'},
                    ],
                    confirmPassword: [
                        {required: true, message: '请再输入一遍新密码', trigger: 'blur'},
                        {validator: checkPasswordRule, trigger: 'blur'},
                    ],
                },
            }
        },
        methods: {
            show() {
                this.isShow = true
            },
            hidden() {
                this.isShow = false
            },
            save() {
                this.$refs.editPasswordForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('self_edit_password', this.form).then(res => {
                            this.loading = false

                            this.$Message.success('修改成功')
                            this.$refs.editPasswordForm.resetFields()

                            this.hidden()
                        }).catch(error => {
                            this.loading = false
                        })
                    }
                })
            },
        }
    }
</script>