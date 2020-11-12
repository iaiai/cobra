<style lang="less" scoped>

</style>

<template>
    <Row>
        <Drawer width="500" v-model="isShow" :mask-closable="false" :closable="true">
            <Row class-name="fbold" slot="header">添加菜单</Row>
            <Row>
                <Alert show-icon>
                    <Icon type="ios-bulb-outline" slot="icon" />
                    <Row class="f12">
                        <Row class-name="m-b-10">说明</Row>
                        <Row class-name="m-b-5">1. 如果为目录或菜单，则编码起名时为驼峰类型的标识，必须全局唯一，不允许重复，如:systemUser、systemMenu</Row>
                        <Row class-name="m-b-5">2. 如果为功能项时，则编码起名时为冒号分割的方式起名，如:system:user:add、system:menu:del</Row>
                        <Row class-name="m-b-5">3. 目录、菜单、功能、子页面起名方式不是必须，但必须全局唯一</Row>
                        <Row class-name="m-b-5">4. <b class="red">编码</b>是后台权限判断依据，不要随便修改，如果要修改请查看后台代码哪里用此<b class="red">编码</b>做权限验证</Row>
                        <Row>5. 前端页面菜单那里只循环了三级，所以这里只允许添加三级，再多的话前端树显示不了，以后了再做成递归就无限制了</Row>
                    </Row>
                </Alert>
                <Form ref="addMenuForm" :rules="rules" :model="form" :label-width="100">
                    <FormItem prop="type" class="f12">
                        <span slot="label" class="f12">类型</span>
                        <RadioGroup v-model="form.type">
                            <Radio :label="1">目录</Radio>
                            <Radio :label="2">菜单</Radio>
                            <Radio :label="4">子页面</Radio>
                            <Radio :label="3">功能</Radio>
                        </RadioGroup>
                    </FormItem>
                    <FormItem v-if="currentNode">
                        <span slot="label" class="f12">上级菜单</span>
                        <Input
                                :value="currentNode.name"
                                disabled
                                placeholder="上级菜单">
                            <i :class="currentNode.icon" slot="prefix" />
                        </Input>
                    </FormItem>
                    <FormItem prop="code">
                        <span slot="label" class="f12">编码</span>
                        <Input v-model.trim="form.code" placeholder="请输入编码，英文，全局唯一" />
                    </FormItem>
                    <FormItem prop="name">
                        <span slot="label" class="f12">名称</span>
                        <Input v-model.trim="form.name" placeholder="请输入名称" />
                    </FormItem>
                    <Row>
                        <Col :span="12">
                            <FormItem prop="seq">
                                <span slot="label" class="f12">顺序</span>
                                <InputNumber :min="1" v-model="form.seq" />
                            </FormItem>
                        </Col>
                        <Col :span="12">
                            <FormItem prop="show" v-show="form.type==1 || form.type==2">
                                <Checkbox v-model="form.show" :true-value="1" :false-value="0">
                                    是否显示
                                </Checkbox>
                            </FormItem>
                        </Col>
                    </Row>
                    <FormItem prop="icon" v-show="form.type==1 || form.type==2">
                        <span slot="label" class="f12">图标</span>
                        <Input
                                v-model.trim="form.icon"
                                placeholder="请选择图标"
                                class="m-r-10"
                                readonly
                                style="width:200px;">
                            <i :class="form.icon" slot="suffix" />
                        </Input>
                        <Button type="text" @click="selIcon">
                            <span class="f12">选择图标</span>
                            <i class="ivu-icon ivu-icon-md-arrow-dropdown" />
                        </Button>
                    </FormItem>
                    <FormItem prop="url" v-show="form.type==2 || form.type==4">
                        <span slot="label" class="f12">url地址</span>
                        <Input v-model.trim="form.url" placeholder="请输入url地址" />
                    </FormItem>
                    <FormItem prop="filePath" v-show="form.type==2 || form.type==4">
                        <span slot="label" class="f12">文件地址</span>
                        <Input v-model.trim="form.filePath" placeholder="请输入文件地址" />
                    </FormItem>
                    <FormItem v-show="form.type==2 || form.type==4">
                        <RadioGroup v-model="form.cache">
                            <Radio :label="1">缓存</Radio>
                            <Radio :label="0">不缓存</Radio>
                        </RadioGroup>
                    </FormItem>
                    <FormItem prop="remark">
                        <span slot="label" class="f12">备注</span>
                        <Input
                                type="textarea"
                                :rows="2"
                                placeholder="请输入备注信息"
                                v-model="form.remark" />
                    </FormItem>
                    <FormItem>
                        <Button type="primary" :loading="loading" @click="save">
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

        <iconComp ref="iconComp" @selectIcon="selectIcon"></iconComp>
    </Row>
</template>

<script>
    import iconComp from '@/commons/components/icon'

    export default {
        props:['currentNode'],
        components: {
            iconComp,
        },
        data() {
            return {
                isShow:false,
                loading:false,  //确定按钮

                form:{
                    name:'',
                    code:'',
                    url:'',
                    filePath:'',
                    type:1,
                    seq:1,
                    icon:'',
                    show:1,
                    parentId:null,
                    cache:1,
                    remark:'',
                },

                rules: {
                    type: [
                        { required: true, type:'number', message: '请选择类型', trigger: 'blur' },
                    ],
                    seq: [
                        { required: true, type:'number', message: '请输入顺序', trigger: 'blur' },
                    ],
                    name: [
                        { required: true, message: '请输入名称', trigger: 'blur' },
                    ],
                    code: [
                        { required: true, message: '请输入编码', trigger: 'blur' },
                    ],
                },
            }
        },
        methods:{
            show(){
                this.form.parentId = this.currentNode?this.currentNode.id:null
                this.isShow = true
            },
            hidden(){
                this.isShow = false
            },
            save(){
                if(this.form.type==3){
                    this.form.url = ''
                    this.form.show = 1
                    this.form.icon = ''
                    this.form.filePath = ''
                }
                if(this.form.type!=2 && this.form.type!=4){
                    this.form.cache = 1
                }

                this.$refs.addMenuForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('menu_add',this.form, true).then(res => {
                            this.loading = false

                            this.$Message.success("添加成功")

                            this.$refs.addMenuForm.resetFields()
                            this.$emit("addSuccess",res.result)
                            this.hidden()
                        }).catch(error => {
                            this.loading = false
                        })
                    }
                })
            },
            selIcon(){
                //选择图标
                this.$refs.iconComp.show()
            },
            selectIcon(icon){
                this.form.icon = icon

                //验证图标单独一项
                this.$refs.addMenuForm.validateField("icon")
            },
        }
    }
</script>
