package com.iaiai.cobra.common.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/1.
 */
public class PropertiesUtil extends Properties {
    private static final long serialVersionUID = 1L;

    public static final class key{
        public static final String projectName = "project.name";    //项目名
        public static final String projectCode = "project.code";    //项目名
        public static final String projectVersion = "project.version";  //项目版本
    }

    private PropertiesUtil() {
        try {

            File rootFile = new File(PropertiesUtil.class.getResource("/").getFile());

            File[] files = rootFile.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().endsWith(".properties")) {
                        return true;
                    }
                    return false;
                }
            });
            for (File file : files) {
                InputStream is = new FileInputStream(file);
                load(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesUtil getInstance() {
        return SingletonContainer.instance;
    }

    private static class SingletonContainer {
        private static PropertiesUtil instance = new PropertiesUtil();
    }
}
