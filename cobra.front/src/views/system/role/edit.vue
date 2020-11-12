<style lang="less" scoped>

</style>

<template>
    <Drawer
            width="400"
            v-model="isShow"
            :mask-closable="false"
            :closable="true">
        <Row class-name="fbold" slot="header">编辑角色</Row>
        <Row>
            <Form ref="editRoleForm" :rules="rules" :model="form" :label-width="80">
                <FormItem label="名称" prop="name">
                    <Input v-model.trim="form.name" placeholder="请输入名称" />
                </FormItem>
                <FormItem label="备注" prop="remark">
                    <Input v-model.trim="form.remark" placeholder="请输入备注信息" />
                </FormItem>
                <FormItem label="顺序" prop="seq">
                    <InputNumber :min="1" v-model="form.seq" />
                </FormItem>
                <FormItem prop="show">
                    <Checkbox v-model="form.status" :true-value="1" :false-value="0">
                        <span class="f12">启用</span>
                    </Checkbox>
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
    export default {
        data() {
            return {
                isShow: false,
                loading: false,  //确定按钮

                form: {
                    id: null,
                    name: '',
                    remark: '',
                    seq: 1,
                    status: 1,
                },

                rules: {
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'},
                    ],
                },
            }
        },
        methods: {
            show(obj) {


                this.form.id = obj.id
                this.form.name = obj.name
                this.form.seq = obj.seq
                this.form.status = obj.status
                this.form.remark = obj.remark
                this.isShow = true
            },
            hidden() {
                this.isShow = false
            },
            save() {
                this.$refs.editRoleForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('role_edit', this.form, true).then(res => {
                            this.loading = false

                            this.$Message.success("编辑成功")

                            this.$refs.editRoleForm.resetFields()
                            this.$emit("editSuccess", res.result)
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