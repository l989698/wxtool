package com.tool.wxtoolproject.api.imgProcess.service.impl;

import com.tool.wxtoolproject.api.imgProcess.params.ImgProcessParam;
import com.tool.wxtoolproject.api.imgProcess.service.ImgProcesssService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImgProcesssServiceImpl implements ImgProcesssService {

    /**
     * 处理文字图片
     *
     * @param param
     * @return java.lang.String
     * @author liuhy
     * @date 2021/6/13 8:48
     */
    @Override
    public String textPicture(MultipartFile mfile, ImgProcessParam param) {

        String base = param.getName();

        try {
            File file = transferToFile(mfile);
            //读取图片
            BufferedImage image = ImageIO.read(file);
            //读取后删除图片
            delteTempFile(file);
            //获取缓冲图像
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            //获取图形
            Graphics2D graphics2D = (Graphics2D) newImage.getGraphics();
            //设置字的字体,加粗,字大小
            graphics2D.setFont(new Font("黑体", Font.BOLD, param.getSize()));

            int index = 0;
            //for循环，循环遍历每一个像素点，将每隔12个像素点就替换为文字。
            for (int y = 0; y < image.getHeight(); y += param.getSize()) {
                for (int x = 0; x < image.getWidth(); x += param.getSize()) {
                    int pxcolor = image.getRGB(x, y);

                    int r = (pxcolor & 0xff0000) >> 16,

                            g = (pxcolor & 0xff00) >> 8,

                            b = pxcolor & 0xff;

                    graphics2D.setColor(new Color(r, g, b));

                    graphics2D.drawString(String.valueOf(base.charAt(index % base.length())), x, y);

                    index++;

                }

            }
            //将处理结果图片流推出
            ImageIO.write(newImage, "JPG", new FileOutputStream("D:\\temp.jpg"));
            String resultBase = imgTurnBase64Str(newImage);
            return resultBase;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }


    /**
     * multipartFile转成 File
     *
     * @param multipartFile
     * @return java.io.File
     * @author liuhy
     * @date 2021/6/13 9:14
     */
    public File transferToFile(MultipartFile multipartFile) throws IOException {
        File toFile = null;
        if (multipartFile.equals("") || multipartFile.getSize() <= 0) {
            multipartFile = null;
        } else {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            toFile = new File(multipartFile.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * 获取流文件
     *
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

    /**
     * BufferedImage 转成base64 返回给前端
     *
     * @param bufferedImage
     * @return java.lang.String
     * @author liuhy
     * @date 2021/6/13 9:14
     */
    public String imgTurnBase64Str(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        ImageIO.write(bufferedImage, "png", baos);//写入流中
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n

        //        ImageIO.write(bufferedImage, "png", new File("D:/qrcode1.png"));
        StringBuffer result = new StringBuffer();
        result.append("data:image/jpg;base64,");
        result.append(png_base64);
        //打印结果
        System.out.println("值为：" + result.toString());
        return result.toString();
    }
}
