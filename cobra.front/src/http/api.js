const api = {
    //登录
    login: {url: '/api/login.do', method: 'post'},
    login_image: {url: '/api/login/captcha.do', method: 'get'},
    login_captcha: {url: '/api/login/captcha.do', method: 'post'},
    login_captcha_position_img: {url: '/api/login/captchaPosition.img', method: 'get'},
    login_captcha_img: {url: '/api/login/captcha.img', method: 'get'},
    login_accessCode: {url: '/api/login/accessCode.do', method: 'post'},
    //退出
    logout: {url: '/api/logout.do', method: 'post'},

    //首页
    home: {url: '/api/home.json', method: 'post'},
    uploadImg: {url: '/api/upload/img.do', method: 'post'},
    img: {url: '/api/img/', method: 'post'},

    //消息
    message: {url: '/api/msg/index.json', method: 'post'},
    message_detail: {url: '/api/msg/detail.json', method: 'post'},
    message_setting_read: {url: '/api/msg/setting/read.do', method: 'post'},

    //个人信息
    self_detail: {url: '/api/self/detail.json', method: 'post'},
    self_edit: {url: '/api/self/edit.do', method: 'post'},
    self_edit_password: {url: '/api/self/edit/password.do', method: 'post'},

    //全局配置
    config_index: {url: '/api/system/config/index.json', method: 'post'},
    config_setting: {url: '/api/system/config/setting.do', method: 'post'},

    //菜单
    menu_index: {url: '/api/system/menu/index.json', method: 'post'},
    menu_add: {url: '/api/system/menu/add.do', method: 'post'},
    menu_edit: {url: '/api/system/menu/edit.do', method: 'post'},
    menu_edit_show: {url: '/api/system/menu/edit/show.do', method: 'post'},
    menu_del: {url: '/api/system/menu/del.do', method: 'post'},
    menu_dels: {url: '/api/system/menu/dels.do', method: 'post'},
    menu_all: {url: '/api/system/menu/all.json', method: 'post'},

    //角色
    role_index: {url: '/api/system/role/index.json', method: 'post'},
    role_query: {url: '/api/system/role/query.json', method: 'post'},
    role_add: {url: '/api/system/role/add.do', method: 'post'},
    role_edit: {url: '/api/system/role/edit.do', method: 'post'},
    role_edit_status: {url: '/api/system/role/edit/status.do', method: 'post'},
    role_del: {url: '/api/system/role/del.do', method: 'post'},
    role_dels: {url: '/api/system/role/dels.do', method: 'post'},
    role_permission: {url: '/api/system/role/permission.json', method: 'post'},
    role_permission_setting: {url: '/api/system/role/permission.do', method: 'post'},
    role_export: {url: '/api/system/role/export.do', method: 'post'},

    //岗位
    post_index: {url: '/api/system/post/index.json', method: 'post'},
    post_add: {url: '/api/system/post/add.do', method: 'post'},
    post_edit: {url: '/api/system/post/edit.do', method: 'post'},
    post_edit_status: {url: '/api/system/post/edit/status.do', method: 'post'},
    post_del: {url: '/api/system/post/del.do', method: 'post'},
    post_dels: {url: '/api/system/post/dels.do', method: 'post'},
    post_query: {url: '/api/system/post/query.json', method: 'post'},

    //部门
    dept_index: {url: '/api/system/dept/index.json', method: 'post'},
    dept_list: {url: '/api/system/dept/list.json', method: 'post'},
    dept_add: {url: '/api/system/dept/add.do', method: 'post'},
    dept_edit: {url: '/api/system/dept/edit.do', method: 'post'},
    dept_del: {url: '/api/system/dept/del.do', method: 'post'},
    dept_dels: {url: '/api/system/dept/dels.do', method: 'post'},
    dept_edit_status: {url: '/api/system/dept/edit/status.do', method: 'post'},
    dept_query: {url: '/api/system/dept/query.json', method: 'post'},

    //用户
    user_index: {url: '/api/system/user/index.json', method: 'post'},
    user_add: {url: '/api/system/user/add.do', method: 'post'},
    user_edit: {url: '/api/system/user/edit.do', method: 'post'},
    user_edit_status: {url: '/api/system/user/edit/status.do', method: 'post'},
    user_info: {url: '/api/system/user/info.json', method: 'post'},
    user_del: {url: '/api/system/user/del.do', method: 'post'},
    user_dels: {url: '/api/system/user/dels.do', method: 'post'},
    user_edit_password: {url: '/api/system/user/edit/password.do', method: 'post'},
    user_export: {url: '/api/system/user/export.do', method: 'post'},

    //日志
    log_index: {url: '/api/system/log/index.json', method: 'post'},

    //在线用户
    online_index: {url: '/api/system/online/index.json', method: 'post'},
    online_exit: {url: '/api/system/online/exit.do', method: 'post'},

    //上传文件
    upload_file_index: {url: '/api/system/uploadFile/index.json', method: 'post'},

    //在线的websocket
    websocket_index: {url: '/api/websocket/index.json', method: 'post'},
    websocket_send: {url: '/api/websocket/send.do', method: 'post'},

    //新闻类型
    news_type_index: {url: '/api/news/type/index.json', method: 'post'},
    news_type_list: {url: '/api/news/type/list.json', method: 'post'},
    news_type_add: {url: '/api/news/type/add.do', method: 'post'},
    news_type_edit: {url: '/api/news/type/edit.do', method: 'post'},
    news_type_edit_show: {url: '/api/news/type/edit/show.do', method: 'post'},
    news_type_del: {url: '/api/news/type/del.do', method: 'post'},
    news_type_dels: {url: '/api/news/type/dels.do', method: 'post'},

    //新闻
    news_index: {url: '/api/news/index.json', method: 'post'},
    news_add: {url: '/api/news/add.do', method: 'post'},
    news_detail: {url: '/api/news/detail.json', method: 'post'},
    news_edit: {url: '/api/news/edit.do', method: 'post'},
    news_edit_show: {url: '/api/news/edit/show.do', method: 'post'},
    news_del: {url: '/api/news/del.do', method: 'post'},
    news_dels: {url: '/api/news/dels.do', method: 'post'},
}
export default api
