<style lang="less" scoped>
    .face{
        width:100px;
        height:100px;
        object-fit: cover;
    }
</style>

<template>
    <Drawer
            width="800"
            v-model="isShow"
            :mask-closable="false"
            :closable="true">
        <Row class-name="fbold" slot="header">编辑系统用户</Row>
        <Row>
            <Form ref="editUserForm" :rules="rules" :model="form" :label-width="80">
                <Row class="center">
                    <Upload
                            class="center"
                            :action="uploadUrl"
                            :show-upload-list="false"
                            :format="['jpg','jpeg','png']"
                            :max-size="2048"
                            type="drag"
                            :on-success="handleFaceSuccess"
                            :before-upload="beforeFaceUpload"
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
    import {isPhone, isEmail} from '@/libs/validate.js'
    import api from '@/http/api'

    export default {
        data() {
            return {
                uploadUrl: api.uploadImg.url,
                isShow: false,
                loading: false,  //确定按钮

                form: {
                    id: '',
                    username: '',
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
            show(obj) {
                this.isShow = true

                this.$nextTick(() => {
                    this.$refs.editUserForm.resetFields()
                    this.selRoles = []
                    this.selPosts = []
                    this.formTemp.deptName = ''

                    this.form.id = obj.id
                    this.form.username = obj.username
                    this.form.nickname = obj.nickname
                    this.form.phone = obj.phone
                    this.form.email = obj.email
                    this.form.remark = obj.remark
                    this.form.sex = obj.sex
                    this.form.status = obj.status
                    this.form.deptId = obj.deptId
                    this.form.face = obj.face
                    this.form.roleIds = ''
                    this.form.postIds = ''

                    this.getPost()
                })
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

                        this.getInfo()
                    }).catch(error => {
                    })
                } else {
                    this.getInfo()
                }
            },
            getInfo() {
                this.$http('user_info', {
                    id: this.form.id,
                }).then(res => {
                    //设置选中的岗位
                    if (res.result.posts && res.result.posts.length > 0) {
                        let ids = ''
                        for (let i = 0; i < res.result.posts.length; i++) {
                            if (i > 0) ids += ","

                            this.selPosts.push(res.result.posts[i].id)
                            ids += res.result.posts[i].id
                        }
                        this.form.postIds = ids
                    }

                    //角色
                    if (res.result.roles && res.result.roles.length > 0) {
                        let ids = ''
                        for (let i = 0; i < res.result.roles.length; i++) {
                            if (i > 0) ids += ","

                            this.selRoles.push(res.result.roles[i].id)
                            ids += res.result.roles[i].id
                        }
                        this.form.roleIds = ids
                    }

                    //部门
                    if (res.result.depts && res.result.depts.length > 0) {
                        this.formTemp.deptName = res.result.depts[0].name
                        this.form.deptId = res.result.depts[0].id
                    }
                }).catch(error => {
                })
            },
            hidden() {
                this.isShow = false
            },
            save() {
                this.$refs.editUserForm.validate((valid) => {
                    if (valid) {
                        this.loading = true

                        this.$http('user_edit', this.form, true).then(res => {
                            this.loading = false

                            this.$Message.success("编辑成功")

                            this.$refs.editUserForm.resetFields()
                            this.$emit("editSuccess")
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
            showDeptSelect() {
                //显示的时候验证一下
                this.$refs.editUserForm.validateField('deptId')
            },
            selectTree(node) {
                this.form.deptId = node.id
                this.formTemp.deptName = node.name

                this.showDept = false
                this.$refs.editUserForm.validateField('deptId')
            },
            selectPost(posts) {
                //选择岗位
                let ids = ''
                for (let i = 0; i < posts.length; i++) {
                    if (i > 0) ids += ","
                    ids += posts[i]
                }
                this.form.postIds = ids
                this.$refs.editUserForm.validateField('postIds')
            },
            selectRole(roles) {
                //选择角色
                let ids = ''
                for (let i = 0; i < roles.length; i++) {
                    if (i > 0) ids += ","
                    ids += roles[i]
                }
                this.form.roleIds = ids
                this.$refs.editUserForm.validateField('roleIds')
            },

            beforeFaceUpload(file) {
                const isJPG = file.type === 'image/jpeg'
                const isPNG = file.type === 'image/png'
                const isLt2M = file.size / 1024 / 1024 < 1

                if (!(isJPG || isPNG)) {
                    this.$Message.error('上传头像图片只能是 jpg或png 格式!')
                    return false
                }
                if (!isLt2M) {
                    this.$Message.error('上传图片大小不能超过 1MB!')
                    return false
                }
                return true
            },
            handleFaceSuccess(res, file, fileList) {
                this.form.face = res.result.webUrl
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
