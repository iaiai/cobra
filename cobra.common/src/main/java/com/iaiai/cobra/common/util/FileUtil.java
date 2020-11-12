package com.iaiai.cobra.common.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.core.util
 * Author: iaiai
 * Create Time: 2019/12/30 4:25 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public class FileUtil {

    /**
     * 获取上传的文件列表
     * @param request
     * @param tempPath
     * @return
     */
    public final static List<FileItem> getServletFileUpload(HttpServletRequest request, String tempPath){
        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
        //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
        /**
         * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，
         * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到 对应目录的硬盘上
         */
        File tempFolder = new File(tempPath);
        if(tempFolder.exists()==false){
            tempFolder.mkdirs();
        }
        //设置缓存文件夹
        factory.setRepository(new File(tempPath));
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
        factory.setSizeThreshold(1024*1024);
        //高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            return upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件名
     *
     * @param filename
     * @return
     */
    public static String getFilename(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 获取文件后缀
     *
     * @param filename
     * @param def      默认值,如果为空的话返回
     * @return
     */
    public static String getFileSuffix(String filename, String def) {
        String suffix = def;
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                suffix = filename.substring(dot + 1);
            }
        }
        return suffix;
    }

    /**
     * 获取文件后缀
     *
     * @param filename
     * @return
     */
    public static String getFileSuffix(String filename) {
        String suffix = null;
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                suffix = filename.substring(dot + 1);
            }
        }
        return suffix;
    }

}
