<style lang="less" scoped>

</style>

<template>
    <Drawer
            width="400"
            v-model="isShow"
            :mask-closable="false"
            :closable="true">
        <Row class-name="fbold" slot="header">修改密码</Row>
        <Row>
            <Form ref="editUserPwdForm" :rules="rules" :model="form" :label-width="110">
                <Row class="m-b-20">
                    <Alert show-icon class="f12">
                        <Icon type="ios-bulb-outline" slot="icon"></Icon>
                        密码必须8位或8位以上，并且必须包含大小写字母和数字和特殊字符 ~!@#$%^&*()_+|<>,.?/:;'[]{}
                    </Alert>
                </Row>
                <FormItem label="新密码" prop="password">
                    <Input
                            type="password"
                            v-model.trim="form.password"
                            maxlength="30"
                            password
                            placeholder="请输入新密码"
                            class="f12" />
                </FormItem>
                <FormItem label="确认新密码" prop="confirmPassword">
                    <Input
                            type="password"
                            v-model.trim="form.confirmPassword"
                            maxlength="30"
                            password
                            placeholder="请再输入一遍新密码"
                            class="f12" />
                </FormItem>
                <FormItem>
                    <Button type="primary" :loading="loading" @click="save" class="m-r-10">
                        <span v-if="!loading">
                            <i class="iconfont iaiai-correct f12" /> 保存
                        </span>
                        <span v-else>处理中...</span>
                    </Button>

                    <Button type="text" @click="hidden">
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
                    id: null,
                    password: '',
                    confirmPassword: '',
                },

                rules: {
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {validator: checkPasswordRule, trigger: 'blur'},
                    ],
                    confirmPassword: [
                        {required: true, message: '请再输入一遍密码', trigger: 'blur'},
                        {validator: checkPasswordRule, trigger: 'blur'},
                        {validator: this.checkConfirmPassword, trigger: 'blur'},
                    ],
                },
            }
        },
        methods: {
            checkConfirmPassword(rule, value, callback) {
                //检查确认密码
                if (!value) {
                    return callback()
                }

                if (value.length < 6 || value !== this.form.password)
                    return callback(new Error('密码错误'))

                return callback()
            },
            show(id) {
                this.form.id = id
                this.isShow = true
            },
            hidden() {
                this.isShow = false
            },
            save() {
                this.$refs.editUserPwdForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('user_edit_password', this.form).then(res => {
                            this.loading = false

                            this.$Message.success('修改成功')

                            this.$refs.editUserPwdForm.resetFields()
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
