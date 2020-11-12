<style lang="less" scoped>

</style>

<template>
    <Drawer width="500" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">添加部门</Row>
        <Row>
            <Form ref="addDeptForm" :rules="rules" :model="form" :label-width="80">
                <FormItem label="上级">
                    <Input v-model.trim="form.parentName" disabled placeholder="请选择上级" />
                </FormItem>
                <FormItem label="名称" prop="name">
                    <Input v-model.trim="form.name" placeholder="请输入名称" />
                </FormItem>
                <FormItem label="负责人" prop="leader">
                    <Input v-model.trim="form.leader" placeholder="请输入负责人" />
                </FormItem>
                <FormItem label="手机号" prop="phone">
                    <Input type="text" v-model.trim="form.phone" placeholder="请输入手机号" maxlength="11" />
                </FormItem>
                <FormItem label="邮箱" prop="email">
                    <Input v-model.trim="form.email" placeholder="请输入邮箱" />
                </FormItem>
                <FormItem label="备注" prop="remark">
                    <Input v-model.trim="form.remark" placeholder="请输入备注信息" />
                </FormItem>
                <FormItem label="顺序" prop="seq">
                    <InputNumber :min="1" v-model="form.seq" />
                </FormItem>
                <FormItem prop="show">
                    <Checkbox v-model="form.status" :true-value="1" :false-value="0">
                        启用
                    </Checkbox>
                </FormItem>
                <FormItem>
                    <Button type="primary" :loading="loading" @click="save" class="m-r-20">
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
    import {isPhone,isEmail} from '@/libs/validate.js'

    export default {
        data() {
            return {
                isShow:false,
                loading:false,  //确定按钮

                form:{
                    name:'',
                    remark:'',
                    seq:1,
                    status:1,
                    leader:'',
                    phone:'',
                    email:'',

                    parentName:'',
                    parentId:'',
                },

                rules: {
                    name: [
                        { required: true, message: '请输入名称', trigger: 'blur' },
                    ],
                    phone: [
                        { validator: isPhone, trigger: 'blur' },
                    ],
                    email: [
                        { validator: isEmail, trigger: 'blur' },
                    ],
                },
            }
        },
        methods:{
            show(parent){
                this.form.parentName = parent.name
                this.form.parentId = parent.id
                this.isShow = true
            },
            hidden(){
                this.isShow = false
            },
            save(){
                this.$refs.addDeptForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('dept_add',this.form, true).then(res => {
                            this.loading = false

                            this.$Message.success("添加成功")

                            this.$refs.addDeptForm.resetFields()
                            this.$emit("addSuccess",res.result)
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