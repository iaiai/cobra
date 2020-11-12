import axios from "axios"
import api from '@/http/api'

/**
 * 自定义上传图片插件
 */
class MyUploadAdapter {
    constructor(loader,root) {
        this.loader = loader
        this.root = root
    }

    async upload() {
        const data = new FormData();
        data.append("file", await this.loader.file);

        console.log(this.root)

        const res = await axios({
            // url: process.env.VUE_APP_BASE_URL + `/upload`,
            url: api.uploadImg.url,
            method: "POST",
            headers:this.root.getHeaderObj(),
            data,
            withCredentials: true, // true 为不允许带 token, false 为允许
        });

        console.log(res.data);
        // 后台返回数据：
        // {"code":0,"msg":"success","data":{"url":"/upload/struts2.jpeg"}}

        // 方法返回数据格式： {default: "url"}
        return {
            // default: process.env.VUE_APP_TARGET_URL + res.data.data.url,
            default: res.data.result.webUrl,
        };
    }
}

export default MyUploadAdapter;