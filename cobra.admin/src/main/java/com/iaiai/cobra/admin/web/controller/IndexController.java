package com.iaiai.cobra.admin.web.controller;

import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.service.UploadFileService;
import com.iaiai.cobra.common.util.FileUtil;
import com.iaiai.cobra.common.util.PropertiesUtil;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.platform.ali.oss.OssUtil;
import com.iaiai.cobra.repository.beans.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller
 * Author: iaiai
 * Create Time: 2019/12/3 1:20 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@RestController
@RequestMapping
public class IndexController extends BaseController {

    @Autowired
    protected UploadFileService uploadFileService;

    @PostMapping(value = "/home.json")
    public ResultVo home(){
        return success();
    }

    @Value("${cobra.upload.tmp-folder}")
    private String tmpFolder;    //上传时临时存储的目录

    @Value("${cobra.upload.file-folder}")
    private String fileFolder;    //上传时临时存储的目录

    @Value("${cobra.upload.web-request-url}")
    private String webRequestUrl;    //上传时临时存储的目录

    @Value("${cobra.keys.aliyun.accessKeyId-oss}")
    private String ossAccessKeyId;

    @Value("${cobra.keys.aliyun.accessKeySecret-oss}")
    private String ossAccessKeySecret;

    @Value("${cobra.keys.aliyun.endpoint-oss}")
    private String ossEndpoint;

    @Value("${cobra.keys.aliyun.bucket-name-oss}")
    private String ossBucketName;

    @Autowired
    private OssUtil ossUtil;

    //上传图片
    @RequestMapping(value = "/upload/img.do")
    public ResultVo uploadImg(HttpServletRequest request) {
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setId(StringUtil.get32UUID());
            List<FileItem> list = FileUtil.getServletFileUpload(request, tmpFolder);
            String requestPath = null;

            for (FileItem item : list) {
                //获取表单的属性名字
                String name = item.getFieldName();  //这个name就是<input>标签中的name属性，是很重要与服务器通信的方式

                //如果获取的 表单信息是普通的 文本 信息
                if (item.isFormField()) {

                } else {   //对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
                    String filename = uploadFile.getId();
                    //后缀
                    String suffix = FileUtil.getFileSuffix(item.getName());
                    uploadFile.setFilename(item.getName());
                    uploadFile.setSuffix(suffix);

                    log.info("上传图片大小:" + item.getSize());

                    //复制文件 将文件以留的方式复制到指定的dir 目录里
                    requestPath = filename + "." + suffix;
//                    FileCopyUtils.copy(item.getInputStream(), new FileOutputStream(uploadFile.getPath()));
                    ossUtil.uploadFile(ossEndpoint,ossAccessKeyId,ossAccessKeySecret,ossBucketName,fileFolder + requestPath,item.getInputStream());

                    uploadFile.setWebUrl(webRequestUrl + File.separator + fileFolder + requestPath);
                    uploadFile.setSize(item.getSize());
                }
            }

            //插入到数据库中
            uploadFile.setDel(Delete.NO.getValue());
            uploadFile.setCreateTime(new Date());
            uploadFile.setModifyTime(uploadFile.getCreateTime());
            uploadFileService.add(uploadFile);

            return success(uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
            return fail(e.getLocalizedMessage());
        }
    }

}
