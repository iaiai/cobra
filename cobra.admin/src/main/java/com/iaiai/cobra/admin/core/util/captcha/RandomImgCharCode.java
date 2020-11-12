package com.iaiai.cobra.admin.core.util.captcha;

import com.iaiai.cobra.common.util.CharUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Package: com.xproject.xcommons.captcha
 * Author: iaiai
 * Create Time: 2018/12/18 6:18 PM
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
public class RandomImgCharCode {

    private Random random = new Random();   //用来生成随机数

    private final static int IMG_TOP_OFFSET = 30;   //图片顶部偏移

    private String path = RandomImgCharCode.class.getProtectionDomain().getCodeSource().getLocation().getPath();    //本类地址

    private List<String> codeBackgroundImg = new ArrayList() {{
        add("img/1.png");  //读取本地图片，做背景图片
        add("img/2.png");  //读取本地图片，做背景图片
        add("img/3.png");  //读取本地图片，做背景图片
        add("img/4.png");  //读取本地图片，做背景图片
        add("img/5.png");  //读取本地图片，做背景图片
        add("img/6.png");  //读取本地图片，做背景图片
        add("img/7.png");  //读取本地图片，做背景图片
        add("img/8.png");  //读取本地图片，做背景图片
        add("img/9.png");  //读取本地图片，做背景图片
        add("img/10.png");  //读取本地图片，做背景图片
    }}; //背景图片

    //    private static String[] codeTips = new String[]{"请依次点击  ","请依次点击:  ","依次点击:  ","依次点击  ","按顺序点击:  ","按顺序点击  "};
    private static String[] codeTips = new String[]{"\u8bf7\u4f9d\u6b21\u70b9\u51fb  ", "\u8bf7\u4f9d\u6b21\u70b9\u51fb:  ", "\u4f9d\u6b21\u70b9\u51fb:  ", "\u4f9d\u6b21\u70b9\u51fb  ", "\u6309\u987a\u5e8f\u70b9\u51fb:  ", "\u6309\u987a\u5e8f\u70b9\u51fb  "};

    private static int[] codeStyles = new int[]{Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC};  //字体风格

    private List<Map<String, Object>> tipFonts = new ArrayList() {{
        Map<String, Object> font1 = new java.util.HashMap<>();
        font1.put("plugin", true);  //是否是独立外挂字体
        font1.put("font", "font/simsun.ttf");    //宋体
        add(font1);

        Map<String, Object> font2 = new java.util.HashMap<>();
        font2.put("plugin", true);  //是否是独立外挂字体
        font2.put("font", "font/msyh.ttf");  //微软雅黑
        add(font2);

        Map<String, Object> font3 = new java.util.HashMap<>();
        font3.put("plugin", true);  //是否是独立外挂字体
        font3.put("font", "font/simhei.ttf");    //黑体
        add(font3);
    }}; //验证码所有的字体

    private List<Map<String, Object>> codeFonts = new ArrayList() {{
        Map<String, Object> font1 = new java.util.HashMap<>();
        font1.put("plugin", true);  //是否是独立外挂字体
        font1.put("font", "font/simsun.ttf");
        add(font1);

        Map<String, Object> font2 = new java.util.HashMap<>();
        font2.put("plugin", true);  //是否是独立外挂字体
        font2.put("font", "font/msyh.ttf");
        add(font2);

        for (int i = 0; i < 7; i++) {
            Map<String, Object> font = new java.util.HashMap<>();
            font.put("plugin", true);  //是否是独立外挂字体
            font.put("font", "font/" + (i + 1) + ".ttf");
            add(font);
        }
    }}; //验证码所有的字体

    private int width = 520;    //图片宽度
    private int height = 320;    //图片高度
    private int tipFontSize = 16;   //字体大小(默认16)
    private int tipFontStyle = Font.BOLD; //字体风格
    private String backgroundImg = codeBackgroundImg.get(random.nextInt(codeBackgroundImg.size()));  //随机背景图片
    private StringBuilder code = new StringBuilder();    //生成的code(中文字)
    private List<ImgCharCodePoint> points = new ArrayList<>();    //生成文字所在图片位置
    private int codeMin = 2;    //生成文字最少数量
    private int codeMax = 4;  //生成文字最多数量
    private boolean debug = false;  //是否是测试

    public RandomImgCharCode setWidth(int width) {
        this.width = width;
        return this;
    }

    public RandomImgCharCode setHeight(int height) {
        this.height = height;
        return this;
    }

    //设置生成文字最少数量
    public RandomImgCharCode setCodeMin(int codeMin) {
        this.codeMin = codeMin;
        return this;
    }

    //设置生成文字最多数量
    public RandomImgCharCode setCodeMax(int codeMax) {
        this.codeMax = codeMax;
        return this;
    }

    //背景图片
    public RandomImgCharCode setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
        return this;
    }

    //设置是否是调试，如果设置成true，则文字会把显示边框
    public RandomImgCharCode setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    //获取图片
    public BufferedImage getImage() throws Exception {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        //保留上面30高度显示要点的字
        g.drawImage(ImageIO.read(new File(path + backgroundImg)), 0, IMG_TOP_OFFSET, width, height, null);

        g.setColor(Color.white);  //设置颜色
        g.drawRect(0, 0, width - 1, height - 1); //画边框

        //设置字体清晰度
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        int number = random.nextInt(codeMax - 1) + codeMin;
        for (int i = 0; i < number; i++) {
            //设置文字颜色
            g.setColor(getRandomColor());

            //获取随机生成的文字
            String str = CharUtil.commonChineseCharacters();
            code.append(str);

            //随机坐标
            int f = random.nextInt(35) + 25;
            int x = random.nextInt(width - 60) + 30;
            int y = random.nextInt(height - 90) + IMG_TOP_OFFSET + 30;

            if (debug) {
                g.drawRect(x, y, f, f); //画边框(这句可显示测试用)
            }
            g.setFont(getRandomFont(f)); //设置字体
            g.drawString(str, x, y + f);

            //存储每个字的位置
            ImgCharCodePoint point = new ImgCharCodePoint();
            point.setX(x);
            point.setY(y);
            point.setFontSize(f);
            points.add(point);
        }

        //设置提示头背景色
        g.setColor(Color.white);
        //画提示头背景
        g.fillRect(0, 0, width, IMG_TOP_OFFSET);

        //设置提示头文字字体
        g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File(path + tipFonts.get(random.nextInt(tipFonts.size())).get("font").toString())).deriveFont(tipFontStyle, tipFontSize)); //设置字体
        //设置提示头文字颜色
        g.setColor(Color.black);
        //画提示文字
        g.drawString(getRandomTip(code.toString()), 10, 20);//写入验证码第一行文字  “点击..”

        //5.释放资源
        g.dispose();

        return image;
    }

    //随机颜色
    private Color getRandomColor() {
        return new Color(random.nextInt(150) + 100, random.nextInt(150) + 100, random.nextInt(150) + 100);
    }

    //随机生成提示
    private String getRandomTip(String val) {
        return codeTips[random.nextInt(codeTips.length)] + val;
    }

    //随机
    private Font getRandomFont(int fontSize) throws Exception {
        Map<String, Object> map = codeFonts.get(random.nextInt(codeFonts.size()));

        Boolean plugin = (Boolean) map.get("plugin");
        Font font = null;
        int style = codeStyles[random.nextInt(codeStyles.length)];

        if (plugin.booleanValue()) {
            //增加的字体库
            Font actionJson = Font.createFont(Font.TRUETYPE_FONT, new File(path + map.get("font").toString()));//返回一个指定字体类型和输入数据的font
            font = actionJson.deriveFont(style, fontSize);//通过复制此 Font 对象并应用新样式和大小，创建一个新 Font 对象。
        } else {
            //系统中自带的
            font = new Font(map.get("font").toString(), style, fontSize);
        }

        //字体旋转
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(random.nextInt(90) - 45), fontSize / 2, -(fontSize / 2));

        return font.deriveFont(affineTransform);
    }

    //获取所有文字在图片中的位置
    public List<ImgCharCodePoint> getPoints() {
        return points;
    }

    //获取验证码
    public String getCode() {
        return code.toString();
    }

}
