/**
 * 因为现在对于需求都不确定，所以数据库中没有加未读已读状态，只要有消息，websocket连接着就推给这个人
 * 如果没开则不管，直接让他点击消息自己看，这里等以后把需求确定了之后再说消息这块怎么去加逻辑
 *
 * ==================================
 * send发送的数据格式:
 * {
 *     url:'这里写请求地址',
 *     code: '唯一码',
 *     token:'这里是所有请求要传的token',
 *     result: 其它参数，和服务器规定其它参数
 * }
 * ==================================
 * 接收到msg消息心跳{}，无内容
 * ==================================
 * 接收msg消息数据格式:
 * {
 *  url:'请求的url',
 *  result: '数据',
 *  code: '唯一码',
 * }
 *
 * url说明:
 * ------------------------------------------------
 * | /login | 登录                                  |
 * ------------------------------------------------
 * | /beat | 心跳                                  |
 * ------------------------------------------------
 * | /send/msg | 消息                                  |
 * ------------------------------------------------
 * | /error | 返回这个说明传的数据整体有问题，未解析出数据         |
 * ------------------------------------------------
 *
 * result说明:根据不同的url返回不同的对象
 * ==================================
 */

class WSocket {
    //构造方法
    constructor (root, url, token, msgCallback, openCallback, closeCallback) {
        this.options = {
            url: url,
            token: token,
            // beatTimeout: 45000,  //心跳时间45秒
            // resetTimeout: 30000,  //重连时间30秒
            beatTimeout: 45000,  //心跳时间45秒
            resetTimeout: 30000,  //重连时间30秒
            msgCallback: msgCallback,  //消息回调方法
            openCallback: openCallback,  //连接成功回调方法
            closeCallback: closeCallback,  //连接关闭回调方法
        }
        this.websocket = null
        this.listener = null
        this.readyState = 0 //状态，0未连接,1已连接,2断开或关闭
        this.$root = root   //main.js对象
    }

    //设置心跳，默认30秒
    setBeatTimeout(beatTimeout){
        this.options.beatTimeout = beatTimeout
    }

    //连接
    connection(){
        //如果连着则什么也不处理
        if(this.readyState == 1)return

        //如果未登录则什么也不处理
        if(this.$root.$isEmpty(this.$root.getToken()))return

        var _self = this

        try {
            this.websocket = new WebSocket(this.options.url)
        } catch (e){
            console.log('【websocket】建立连接异常')
            if(this.websocket)this.websocket.close()
        }

        //创建心跳
        _self.listener = new HeartListener(_self.websocket,_self.options.beatTimeout)

        this.websocket.onopen = function () {
            if(_self.options.openCallback){
                _self.options.openCallback()
            }

            _self.readyState = 1
            _self.listener.close().start()

            //建立连接的时候去登录一下
            _self.login()
        }

        //接收消息
        this.websocket.onmessage = function (evt) {
            _self.listener.close().start()

            if(evt.data) {
                console.log("【websocket】",evt.data)
                let json = JSON.parse(evt.data)
                if (_self.options.msgCallback) {
                    if(json.url=='/beat') return    //这是心跳
                    if(json.url=='/login') return    //这是登录

                    _self.options.msgCallback(json)
                }
            }
        }

        this.websocket.onclose = function () {
            if(_self.options.closeCallback){
                _self.options.closeCallback()
            }
            _self.readyState = 2
            if (_self.listener){
                _self.listener.close()
                _self.listener = null
            }
            _self.resetConnection()
        }

        this.websocket.onerror = function () {
            console.log("【websocket】连接错误。")
        }

        return this
    }

    closeConnection(){
        if(this.options.closeCallback){
            this.options.closeCallback()
        }
        if(this.websocket!=null)this.websocket.close()
        if(this.listener!=null)this.listener.close()

        // this.resetConnection()
    }

    resetConnection(){
        var _self = this
        setTimeout(function() {
            _self.connection()
        }, this.options.resetTimeout)
    }

    login(){
        console.log("websocket login send token")
        this.websocket.send(JSON.stringify({
            url:'/login',
            result:this.options.token,
            code: this.uuid(),
        }))
    }

    uuid() {
        var s = []
        var hexDigits = "0123456789abcdef"
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
        }
        s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-"

        var uuid = s.join("")
        return uuid
    }
}

class HeartListener{
    //构造方法
    constructor (websocket,beatTimeout) {
        this.websocket = websocket
        this.options = {
            beatTimeout: beatTimeout,  //心跳时间30秒
            responseTimeOut: 20000,     //心跳发送后多长时间如果没有返回信息多长时间断开连接，默认10秒
        }
        this.timeoutObj= null   //定时器，这个是用于发送心跳的
        this.serverTimeoutObj= null //定时器，这个是发送心跳之后等待如果等待超时了就认为断开连接了
    }

    //启动心跳
    start(beatTimeout){
        if(beatTimeout){
            this.options.beatTimeout = beatTimeout
        }

        var _self = this
        this.timeoutObj = setTimeout(function() {
            //这里发送一个心跳，后端收到后，返回一个心跳消息，
            //onmessage拿到返回的心跳就说明连接正常
            _self.websocket.send(JSON.stringify({
                url:'/beat',
                code: _self.uuid(),
            }))

            _self.serverTimeoutObj = setTimeout(function(){//如果超过一定时间还没重置，说明后端主动断开了
                if(_self.options.closeCallback){
                    _self.options.closeCallback()
                }
                _self.websocket.close();//如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
            }, _self.options.responseTimeOut)
        }, this.options.beatTimeout)
    }

    //关闭
    close(){
        if(this.timeoutObj){
            clearTimeout(this.timeoutObj)
            this.timeoutObj = null
        }
        if(this.serverTimeoutObj){
            clearTimeout(this.serverTimeoutObj)
            this.serverTimeoutObj = null
        }

        return this
    }

    uuid() {
        var s = []
        var hexDigits = "0123456789abcdef"
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
        }
        s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-"

        var uuid = s.join("")
        return uuid
    }
}

export default WSocket
