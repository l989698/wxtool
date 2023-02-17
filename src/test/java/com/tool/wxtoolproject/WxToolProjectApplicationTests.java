package com.tool.wxtoolproject;

import com.tool.wxtoolproject.api.common.util.RedisUtil;
import com.tool.wxtoolproject.api.function.VUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Exchanger;

@SpringBootTest
class WxToolProjectApplicationTests {

    @Resource
    private RedisUtil redisUtil;

    public static void createFongImg(String path, int size, String name) {

        String base = name;
//
//        try {
//            BufferedImage image = ImageIO.read(new File(path));
//            //获取缓冲图像
//            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
//            //获取图形
//            Graphics2D graphics2D = (Graphics2D) newImage.getGraphics();
//            //设置字的字体,加粗,字大小
//            graphics2D.setFont(new Font("黑体", Font.BOLD, size));
//
//            int index = 0;
//            //for循环，循环遍历每一个像素点，将每隔12个像素点就替换为文字。
//            for (int y = 0; y < image.getHeight(); y += size) {
//                for (int x = 0; x < image.getWidth(); x += size) {
//                    int pxcolor = image.getRGB(x, y);
//
//                    int r = (pxcolor & 0xff0000) >> 16,
//
//                            g = (pxcolor & 0xff00) >> 8,
//
//                            b = pxcolor & 0xff;
//
//                    graphics2D.setColor(new Color(r, g, b));
//
//                    graphics2D.drawString(String.valueOf(base.charAt(index % base.length())), x, y);
//
//                    index++;
//
//                }
//
//            }
//            //将处理结果图片流推出
//            ImageIO.write(newImage, "JPG", new FileOutputStream("D:\\temp.jpg"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }

    }


    public static void main(String[] args) throws Exception {
        System.out.println("start");
        Thread t = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        //阻塞主线程
        t.join();
        System.out.println("end");
    }
    public static Boolean verifyDomainName(String host){
        List<String> domainName = Arrays.asList(new String[]{"v93.douyinvod.com", "v5-che.douyinvod.com", "v6-qos-hourly.douyinvod.com", "v26-che.douyinvod.com", "v6-cold.douyinvod.com", "v83-x.douyinvod.com", "v5-coldb.douyinvod.com", "v3-z.douyinvod.com", "v1-x.douyinvod.com", "v6-ab-e1.douyinvod.com", "v5-abtest.douyinvod.com", "v9-che.douyinvod.com", "v83-y.douyinvod.com", "v5-litea.douyinvod.com", "v3-che.douyinvod.com", "v29-cold.douyinvod.com", "v5-lite.douyinvod.com", "v29-qos-control.douyinvod.com", "v5-gdgz.douyinvod.com", "v5-ttcp-a.douyinvod.com", "v3-b.douyinvod.com", "v9-z-qos-control.douyinvod.com", "v9-x-qos-hourly.douyinvod.com", "v9-chc.douyinvod.com", "v9-qos-hourly.douyinvod.com", "v5-ttcp-b.douyinvod.com", "v6-z-qos-control.douyinvod.com", "v5-dlyd.douyinvod.com", "v5-coldy.douyinvod.com", "v3-c.douyinvod.com", "v5-jbwl.douyinvod.com", "v26-0015c002.douyinvod.com", "v5-gdwy.douyinvod.com", "v3-d.douyinvod.com", "v3-p.douyinvod.com", "v5-gdhy.douyinvod.com", "v26-cold.douyinvod.com", "v5-lite-a.douyinvod.com", "v5-i.douyinvod.com", "v5-g.douyinvod.com", "v26-qos-daily.douyinvod.com", "v5-dash.douyinvod.com", "v5-h.douyinvod.com", "v5-f.douyinvod.com", "v3-a.douyinvod.com", "v83.douyinvod.com", "v5-cold.douyinvod.com", "v3-y.douyinvod.com", "v26-x.douyinvod.com", "v27-ipv6.douyinvod.com", "v9-ipv6.douyinvod.com", "v5-yacu.douyinvod.com", "v29-ipv6.douyinvod.com", "v26-coldf.douyinvod.com", "v5.douyinvod.com", "v11.douyinvod.com", "v6-z.douyinvod.com", "v1.douyinvod.com", "v9-y.douyinvod.com", "v9-z.douyinvod.com", "v9.douyinvod.com", "v3-x.douyinvod.com", "v6-y.douyinvod.com", "v3-ipv6.douyinvod.com", "v5-e.douyinvod.com", "v3.douyinvod.com", "v6-ipv6.douyinvod.com", "v9-x.douyinvod.com", "v6-p.douyinvod.com", "v1-2p.douyinvod.com", "v1-p.douyinvod.com", "v1-ipv6.douyinvod.com", "v24.douyinvod.com", "v1-dy.douyinvod.com", "v6.douyinvod.com", "v6-x.douyinvod.com", "v26-ipv6.douyinvod.com", "v27.douyinvod.com", "v92.douyinvod.com", "v95.douyinvod.com", "douyinvod.com", "v26.douyinvod.com", "v29.douyinvod.com"});
        int begin = host.indexOf("https://");
        int end = host.indexOf(".com/");
        String substring = host.substring(begin+8, end+4);
        if (domainName.contains(substring)) {
            return false;
        }
        return true;
    }

    @Test
    public void test() {
        Boolean aBoolean = verifyDomainName("https://v5.douyinvod.com/4bbcb81a1f01ab32b61a497f6947d850/62ca7d01/video/tos/cn/tos-cn-ve-15c001-alinc2/292e0a413a8d4792881a6e8bfa0f1d4c/?a=1128&ch=0&cr=0&dr=0&cd=0%7C0%7C0%7C0&cv=1&br=2384&bt=2384&btag=80000&cs=0&ds=3&ft=GYTg38VVyw3cRm_80mo~xj7ScoAwHXamwVaVJEjaWAfCPD&mime_type=video_mp4&qs=0&rc=ZDdnODQ2aGc4NDU1Mzc5Z0BpM2loNGk6ZjZwZTMzNGkzM0A0NTI0XjExNjIxMmNeYGEzYSNwY2locjRnay1gLS1kLWFzcw%3D%3D&l=202207101416590102080370672BB2EA1B");
        System.out.println(aBoolean);
        //姑娘照片
//        WxToolProjectApplicationTests.createFongImg("D:\\test.jpg", 20,"测试");
//        System.out.println("OK");
        //测试redis
//        redisUtil.set("jinyue","是250");
//        System.out.println(redisUtil.get("jinyue"));

    }


    @Test
    public void test2() {
        Exchanger exchanger = new Exchanger();

        for (int i = 1; i <= 10; i++) {
            Integer data = i;
            new Thread(() -> {
                try {
                    Object exchange = exchanger.exchange(data);
                    System.out.println(Thread.currentThread().getName() + "-" + exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Java技术栈" + i).start();
        }
    }

    @Test
    void functionTest() {
        VUtils.isTure(true).throwMessage("俺要整个异常了!");
    }

    @Test
    void runnableTest(){
        VUtils.isTureOrFalse(false).trueOrFalseHandle(() ->{
            System.out.println("true,俺开始秀了");
        },() -> {
            System.out.println("false,秀不动了,跑啊");
        });
    }

    @Test
    void isBlankOrNoBlank(){
        VUtils.isBlankOrNoBlank("hello").presentOrElseHandle(System.out::println,() ->{
            System.out.println("空字符串");
        });
    }


    @Test
    void testMap(){
        Map map = new HashMap<>();
        if (map.get("a") != null){
            System.out.println("");
        }else {
            System.out.println("111");
        }
    }
}
