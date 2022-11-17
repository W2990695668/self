package com.wqq.self.common.utils;

import com.wqq.self.common.constant.RedisKey;
import com.wqq.self.redis.service.RedisService;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/**
 * 生产图片验证码工具类
 */
public class CaptchaUtils {

    private static int weight = 100;           //验证码图片的长和宽
    private static int height = 40;
    private static Random r = new Random();    //获取随机数对象
    private static final String[] fontNames = {"Georgia"}; //字体数组
    //验证码数组
    private static final String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    private static final RedisService redisService  = SpringBeanUtils.getSpringBean(RedisService.class);

    public static Captcha create() throws IOException {
        //创建CaptchaUtils对象时自动生成图片验证码，并放入CacheMem中
        Captcha captcha = new Captcha().create();
        return captcha;
    }

    @Data
    public static class Captcha{
        private BufferedImage image;
        private String imageMd5;
        private String code; //用来保存验证码的文本内容

        /**
         * 创建图片的方法
         *
         * @return
         */
        private BufferedImage createImage() {
            //创建图片缓冲区
            BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
            //获取画笔
            Graphics2D g = (Graphics2D) image.getGraphics();
            //设置背景色随机
            g.setColor(new Color((r.nextInt(245) + 10), (r.nextInt(245) + 10), (r.nextInt(245) + 10)));
            g.fillRect(0, 0, weight, height);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++)             //画四个字符即可
            {
                String s = randomChar() + "";      //随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
                sb.append(s);                      //添加到StringBuilder里面
                float x = i * 1.0F * weight / 4;   //定义字符的x坐标
                g.setFont(randomFont());           //设置字体，随机
                g.setColor(randomColor());         //设置颜色，随机
                g.drawString(s, x, height - 5);
            }
            this.code = sb.toString();
            drawLine(image);
            this.image = image;
            return image;
        }

        /**
         * 获取随机的颜色
         *
         * @return
         */
        private Color randomColor() {
            int r = CaptchaUtils.r.nextInt(225);
            int g = CaptchaUtils.r.nextInt(225);
            int b = CaptchaUtils.r.nextInt(225);
            return new Color(r, g, b);            //返回一个随机颜色
        }

        /**
         * 获取随机字体
         *
         * @return
         */
        private Font randomFont() {
            int index = r.nextInt(fontNames.length);  //获取随机的字体
            String fontName = fontNames[index];
            int style = r.nextInt(4);         //随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
            int size = r.nextInt(10) + 24;    //随机获取字体的大小
            return new Font(fontName, style, size);   //返回一个随机的字体
        }

        /**
         * 获取随机字符
         *
         * @return
         */
        private char randomChar() {
            int index = r.nextInt(codes.length());
            return codes.charAt(index);
        }

        /**
         * 画干扰线，验证码干扰线用来防止计算机解析图片
         *
         * @param image
         */
        private void drawLine(BufferedImage image) {
            int num = r.nextInt(10); //定义干扰线的数量
            Graphics2D g = (Graphics2D) image.getGraphics();
            for (int i = 0; i < num; i++) {
                int x1 = r.nextInt(weight);
                int y1 = r.nextInt(height);
                int x2 = r.nextInt(weight);
                int y2 = r.nextInt(height);
                g.setColor(randomColor());
                g.drawLine(x1, y1, x2, y2);
            }
        }

        //将验证码图片写出的方法
        public void outputImage(OutputStream os) throws IOException {
            ImageIO.write(image, "JPEG", os);
        }

        //将验证码图片写出的方法
        public void outputImage(String path) throws IOException {
            ImageIO.write(image, "JPEG", new File(path));
        }

        public byte[] getImgOutPutStream() throws IOException {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            byte[] bytes = null;
            try {
                bytes = os.toByteArray();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bytes;
        }

        public Captcha create() throws IOException {
            //创建CaptchaUtils对象时自动生成图片验证码，并放入CacheMem中
            BufferedImage image = this.createImage();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            String md5 = FileUtils.calculateFileMd5(input);
            this.imageMd5 = md5;
            redisService.set(RedisKey.IMAGE_CODE_PRE + this.imageMd5, this.getCode(), 600);
            return this;
        }
    }


}