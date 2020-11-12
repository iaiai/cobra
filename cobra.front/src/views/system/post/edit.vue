<style lang="less" scoped>

</style>

<template>
    <Drawer width="400" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">编辑岗位</Row>
        <Row>
            <Form ref="editPostForm" :rules="rules" :model="form" :label-width="80">
                <FormItem label="名称" prop="name">
                    <Input v-model.trim="form.name" placeholder="请输入名称" />
                </FormItem>
                <FormItem label="编码">
                    <Input v-model.trim="form.code" placeholder="请输入编码" />
                </FormItem>
                <FormItem label="备注">
                    <Input v-model.trim="form.remark" placeholder="请输入备注信息" />
                </FormItem>
                <FormItem label="顺序">
                    <InputNumber :min="1" v-model="form.seq" />
                </FormItem>
                <FormItem>
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
    export default {
        data() {
            return {
                isShow:false,
                loading:false,  //确定按钮

                form:{
                    id:null,
                    name:'',
                    code:'',
                    remark:'',
                    seq:1,
                    status:1,
                },

                rules: {
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
                this.form.code = obj.code
                this.form.seq = obj.seq
                this.form.status = obj.status
                this.form.remark = obj.remark
                this.isShow = true
            },
            hidden(){
                this.isShow = false
            },
            save(){
                this.$refs.editPostForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('post_edit',this.form, true).then(res => {
                            this.loading = false

                            this.$Message.success("编辑成功")

                            this.$refs.editPostForm.resetFields()
                            this.$emit("editSuccess",res.result)
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