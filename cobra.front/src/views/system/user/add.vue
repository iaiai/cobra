<style lang="less" scoped>
    .face{
        width:100px;
        height:100px;
        object-fit: cover;
    }
</style>

<template>
    <Drawer width="800" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">添加系统用户</Row>
        <Row>
            <Row class="m-b-10">
                <Alert show-icon class="f12">
                    <Icon type="ios-bulb-outline" slot="icon"></Icon>
                    密码必须8位或8位以上，并且必须包含大小写字母和数字和特殊字符 ~!@#$%^&*()_+|<>,.?/:;'[]{}
                </Alert>
            </Row>
            <Form ref="addUserForm" :rules="rules" :model="form" :label-width="80">
                <Row class="center">
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
                <Row class-name="m-t-20">
                    <Col :span="12">
                        <FormItem label="登录名" prop="username">
                            <Input
                                    v-model.trim="form.username"
                                    maxlength="30"
                                    clearable
                                    placeholder="请输入登录名"
                                    class="f12" />
                        </FormItem>
                    </Col>
                    <Col :span="12">
                        <FormItem label="姓名" prop="nickname">
                            <Input
                                    v-model.trim="form.nickname"
                                    maxlength="30"
                                    clearable
                                    placeholder="请输入姓名"
                                    class="f12" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col :span="12">
                        <FormItem label="密码" prop="password">
                            <Input
                                    type="password"
                                    password
                                    v-model.trim="form.password"
                                    maxlength="30"
                                    placeholder="请输入密码，必须大于或等于6位"
                                    class="f12" />
                        </FormItem>
                    </Col>
                    <Col :span="12">
                        <FormItem label="确认密码" prop="confirmPassword">
                            <Input
                                    type="password"
                                    password
                                    v-model.trim="form.confirmPassword"
                                    maxlength="30"
                                    placeholder="请再输入一遍密码，必须大于或等于6位"
                                    class="f12" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col :span="12">
                        <FormItem label="手机号" prop="phone">
                            <Input
                                    v-model.trim="form.phone"
                                    maxlength="11"
                                    clearable
                                    placeholder="请输入手机号"
                                    class="f12" />
                        </FormItem>
                    </Col>
                    <Col :span="12">
                        <FormItem label="邮箱" prop="email">
                            <Input
                                    v-model.trim="form.email"
                                    maxlength="100"
                                    clearable
                                    placeholder="请输入邮箱"
                                    class="f12" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col :span="12">
                        <FormItem label="备注">
                            <Input
                                    v-model.trim="form.remark"
                                    maxlength="200"
                                    clearable
                                    placeholder="请输入备注信息"
                                    class="f12" />
                        </FormItem>
                    </Col>
                    <Col :span="12">
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
                    </Col>
                </Row>
                <Row>
                    <Col :span="12">
                        <FormItem label="部门" prop="deptId">
                            <Input
                                    v-model="formTemp.deptName"
                                    readonly
                                    clearable
                                    style="width:140px"
                                    class="m-r-10"
                                    placeholder="请选择部门"/>
                            <Poptip
                                    width="260"
                                    placement="left"
                                    trigger="click"
                                    v-model="showDept"
                                    :transfer="true"
                                    @on-popper-show="showDeptSelect">
                                <Row slot="content">
                                    <Tree :data="depts" class="f12" :render="renderTree" />
                                </Row>

                                <a href="javascript:;" class="f12">
                                    <Icon type="md-arrow-dropdown" /> 请选择部门
                                </a>
                            </Poptip>
                        </FormItem>
                    </Col>
                    <Col :span="12">
                        <FormItem label="岗位" prop="postIds">
                            <Select
                                    v-model="selPosts"
                                    placeholder="请选择岗位"
                                    multiple
                                    @on-change="selectPost">
                                <Option
                                        v-for="item in posts"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col :span="12">
                        <FormItem label="角色" prop="roleIds">
                            <Select
                                    v-model="selRoles"
                                    placeholder="请选择角色"
                                    multiple
                                    @on-change="selectRole">
                                <Option
                                        v-for="item in roles"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col :span="12">
                        <FormItem label="启用" prop="status">
                            <i-switch
                                    v-model="form.status"
                                    active-color="#13ce66"
                                    :true-value="1"
                                    :false-value="0"/>
                        </FormItem>
                    </Col>
                </Row>
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
    import {isPhone, isEmail,checkPasswordRule} from '@/libs/validate.js'
    import api from '@/http/api'

    export default {
        data() {
            return {
                uploadUrl: api.uploadImg.url,
                isShow: false,
                loading: false,  //确定按钮
                form: {
                    username: '',
                    password: '',
                    confirmPassword: '',
                    phone: '',
                    nickname: '',
                    remark: '',
                    email: '',
                    status: 1,
                    sex: 0,
                    deptId: '',
                    postIds: '',
                    roleIds: '',
                    face: '',
                },
                formTemp: {
                    deptName: '',
                },

                rules: {
                    username: [
                        {required: true, message: '请输入登录名', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {validator: this.checkPassword, trigger: 'blur'},
                    ],
                    confirmPassword: [
                        {required: true, message: '请再输入一遍密码', trigger: 'blur'},
                        {validator: checkPasswordRule, trigger: 'blur'},
                        {validator: this.checkConfirmPassword, trigger: 'blur'},
                    ],
                    phone: [
                        {required: true, message: '请输入手机号', trigger: 'blur'},
                        {validator: isPhone, trigger: 'blur'},
                    ],
                    email: [
                        {validator: isEmail, trigger: 'blur'},
                    ],
                    nickname: [
                        {required: true, message: '请输入姓名', trigger: 'blur'},
                    ],
                    deptId: [
                        {required: true, message: '请选择部门', trigger: 'blur'},
                    ],
                    postIds: [
                        {required: true, message: '请选择岗位', trigger: 'blur'},
                    ],
                    roleIds: [
                        {required: true, message: '请选择角色', trigger: 'blur'},
                    ],
                },

                showDept: false,
                depts: [],   //部门

                posts: [],   //岗位
                selPosts: [],    //选中的

                roles: [],   //角色
                selRoles: [],    //选中的
            }
        },
        methods: {
            show() {
                this.isShow = true
                this.getPost()
            },
            getPost() {
                if (this.$root.isPermission('system:post:query')) {
                    this.$http('post_query').then(res => {
                        this.posts = res.result

                        this.getDept()
                    }).catch(error => {
                    })
                } else {
                    this.getDept()
                }
            },
            getDept() {
                if (this.$root.isPermission('system:dept:query')) {
                    this.$http('dept_query').then(res => {
                        this.depts = res.result
                        this.getRole()
                    }).catch(error => {
                    })
                } else {
                    this.getRole()
                }
            },
            getRole() {
                if (this.$root.isPermission('system:role:query')) {
                    this.$http('role_query').then(res => {
                        this.roles = res.result
                    }).catch(error => {
                    })
                }
            },
            hidden() {
                this.isShow = false
            },
            save() {
                this.$refs.addUserForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('user_add', this.form).then(res => {
                            this.loading = false

                            this.$Message.success('添加成功')

                            this.$refs.addUserForm.resetFields()
                            this.selPosts = []
                            this.selRoles = []
                            this.formTemp.deptName = ''
                            this.form.phone = ''
                            this.$emit("addSuccess")
                            this.hidden()
                        }).catch(error => {
                            this.loading = false
                        })
                    }
                })
            },
            selSex(sex) {
                this.form.sex = sex
            },
            checkConfirmPassword(rule, value, callback) {
                //检查确认密码
                if (!value) {
                    return callback()
                }

                if (value.length < 6 || value !== this.form.password)
                    return callback(new Error('密码错误'))

                return callback()
            },
            showDeptSelect() {
                //显示的时候验证一下
                this.$refs.addUserForm.validateField('deptId')
            },
            selectTree(node) {
                this.form.deptId = node.id
                this.formTemp.deptName = node.name

                this.showDept = false
                this.$refs.addUserForm.validateField('deptId')
            },
            selectPost(posts) {
                //选择岗位
                let ids = ''
                for (let i = 0; i < posts.length; i++) {
                    if (i > 0) ids += ","
                    ids += posts[i]
                }
                this.form.postIds = ids
                this.$refs.addUserForm.validateField('postIds')
            },
            selectRole(roles) {
                //选择角色
                let ids = ''
                for (let i = 0; i < roles.length; i++) {
                    if (i > 0) ids += ","
                    ids += roles[i]
                }
                this.form.roleIds = ids
                this.$refs.addUserForm.validateField('roleIds')
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
        }
    }
</script>
