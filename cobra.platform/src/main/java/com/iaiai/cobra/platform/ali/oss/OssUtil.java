package com.iaiai.cobra.platform.ali.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.ali.oss
 * Author: iaiai
 * Create Time: 2020/8/29 3:19 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@Component
public class OssUtil {

    /**
     *
     * @param endpoint
     * @param accessKeyId 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
     * @param accessKeySecret
     * @param bucketName
     * @param pathFile 目录，例:mini/，images/
     */
    public void uploadFile(String endpoint,
                           String accessKeyId,
                           String accessKeySecret,
                           String bucketName,
                           String pathFile,
                           File file) throws FileNotFoundException {
        this.uploadFile(endpoint,accessKeyId,accessKeySecret,bucketName,pathFile,new FileInputStream(file));
    }

    /**
     *
     * @param endpoint
     * @param accessKeyId 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
     * @param accessKeySecret
     * @param bucketName
     * @param pathFile 目录，例:mini/，images/
     */
    public void uploadFile(String endpoint,
                           String accessKeyId,
                           String accessKeySecret,
                           String bucketName,
                           String pathFile,
                           InputStream input) throws FileNotFoundException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, pathFile, input);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
