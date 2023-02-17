package com.tool.wxtoolproject.api.tiktok.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.tool.wxtoolproject.api.common.util.RedisUtil;
import com.tool.wxtoolproject.api.common.vo.Result;
import com.tool.wxtoolproject.api.tiktok.params.TikTokParams;
import com.tool.wxtoolproject.api.tiktok.service.TikTokService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TikTokServiceImpl implements TikTokService {

    @Autowired
    private  RedisUtil redisUtil;

    /**
     * 获取抖音无水印视频
     *
     * @param tikTokParams
     * @return java.lang.String
     * @author liuhy
     * @date 2021/5/29 20:04
     */
    @Override
    public Result getTikTokVideoWithoutWatermark(TikTokParams tikTokParams) throws Exception {

        if (ObjectUtil.isNotNull(tikTokParams)) {
            String url = tikTokParams.getUrl();
            if (url.contains("v.kuaishou.com")) {
                return Result.ok(ksParseUrl(filterUrl(url)));
            }
            if (url.contains("v.douyin.com")) {
                return Result.ok(douYinParseUrl(filterUrl(url)));
            }
        }
        return Result.error("链接地址错误");
    }

    /**
     * 获取抖音无水印图片
     *
     * @param tikTokParams
     * @return
     * @throws IOException
     */
    @Override
    public List<Map<String, Object>> getTikTokImageWithoutWatermark(TikTokParams tikTokParams) throws IOException {
        List<Map<String, Object>> douyinImages = this.getDouyinImages(filterUrl(tikTokParams.getUrl()));
        return douyinImages;
    }

    /**
     * 过滤后返回地址
     *
     * @param url
     * @return java.lang.String
     * @author liuhy
     * @date 2021/11/29 10:10
     */
    public static String filterUrl(String url) {
        String regex = "https?://(\\w|-)+(\\.(\\w|-)+)+(/(\\w+(\\?(\\w+=(\\w|%|-)*(\\&\\w+=(\\w|%|-)*)*)?)?)?)+";//匹配网址
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        if (m.find()) {
            return url.substring(m.start(), m.end());
        }
        return "";
    }

    /**
     * 快手解析无水印视频
     *
     * @param url
     * @return java.lang.String
     * @author liuhy
     * @date 2021/6/14 7:53
     */
    public String ksParseUrl(final String url) {
        HashMap headers = MapUtil.newHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
        String redirectUrl = HttpUtil.createGet(url).addHeaders(headers).execute().header("Location");
        String body = HttpUtil.createGet(redirectUrl).addHeaders(headers).execute().body();
        Document doc = Jsoup.parse(body);
        Elements videoElement = doc.select("video[id=video-player]");
        String videoUrl = videoElement.get(0).attr("src");//视频地址
        String title = videoElement.get(0).attr("alt");//视频标题
        return videoUrl;
    }

    /**
     * 抖音解析无水印视频
     *
     * @param url
     * @return java.lang.String
     * @author liuhy
     * @date 2021/6/14 7:58
     */
    public String douYinParseUrl(String url) throws Exception {

        JSONObject urlJson = getUrlJson(url);
        String videoAddress = urlJson.getJSONArray("item_list").getJSONObject(0).getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").get(0).toString();
        HashMap headers = MapUtil.newHashMap();
        //伪装head信息
        headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
        String replace = videoAddress.replace("playwm", "play");
        HttpResponse execute = HttpUtil.createGet(replace).addHeaders(headers).execute();
        String finalVideoAddress = execute.header("Location");
        if (verifyDomainName(finalVideoAddress)) {
            this.douYinParseUrl(url);
        }
        return finalVideoAddress;
    }

    /**
     * 抖音无水印图片
     *
     * @param url
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> getDouyinImages(String url) throws IOException {
        JSONObject urlJson = getUrlJson(url);
        JSONArray images = urlJson.getJSONArray("item_list").getJSONObject(0).getJSONArray("images");
        List<Map<String, Object>> urls = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("image", "");
        resultMap.put("hide", true);
        resultMap.put("id", "0");
        urls.add(resultMap);
        for (int i = 0; i < images.size(); i++) {
            resultMap = new HashMap<>();
            JSONObject jsonObject = images.getJSONObject(i);
            JSONArray url_list = jsonObject.getJSONArray("url_list");
            Integer id = i + 1;
            resultMap.put("id", id.toString());
            resultMap.put("image", url_list.get(0).toString());
            urls.add(resultMap);
        }

        return urls;
    }


    /**
     * 获取抖音分享地址解析后json信息
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static JSONObject getUrlJson(String url) throws IOException {
        final String videoPath = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";
        Connection con = Jsoup.connect(url);
        //伪装head信息
        con.header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
        Connection.Response resp = con.method(Connection.Method.GET).execute();
        String strUrl = resp.url().toString();
        String itemId = strUrl.substring(strUrl.indexOf("video/"), strUrl.lastIndexOf("/")).replace("video/", "");
        String videoUrl = videoPath + itemId;
        //获取返回body
        String jsonStr = Jsoup.connect(videoUrl).ignoreContentType(true).execute().body();
        JSONObject json = new JSONObject(jsonStr);
        return json;
    }

    public  Boolean verifyDomainName(String host) {
        List<String> domainName = Arrays.asList(new String[]{"v5-colde.douyinvod.com","v6-cold2.douyinvod.com", "v93.douyinvod.com", "v5-che.douyinvod.com", "v6-qos-hourly.douyinvod.com", "v26-che.douyinvod.com", "v6-cold.douyinvod.com", "v83-x.douyinvod.com", "v5-coldb.douyinvod.com", "v3-z.douyinvod.com", "v1-x.douyinvod.com", "v6-ab-e1.douyinvod.com", "v5-abtest.douyinvod.com", "v9-che.douyinvod.com", "v83-y.douyinvod.com", "v5-litea.douyinvod.com", "v3-che.douyinvod.com", "v29-cold.douyinvod.com", "v5-lite.douyinvod.com", "v29-qos-control.douyinvod.com", "v5-gdgz.douyinvod.com", "v5-ttcp-a.douyinvod.com", "v3-b.douyinvod.com", "v9-z-qos-control.douyinvod.com", "v9-x-qos-hourly.douyinvod.com", "v9-chc.douyinvod.com", "v9-qos-hourly.douyinvod.com", "v5-ttcp-b.douyinvod.com", "v6-z-qos-control.douyinvod.com", "v5-dlyd.douyinvod.com", "v5-coldy.douyinvod.com", "v3-c.douyinvod.com", "v5-jbwl.douyinvod.com", "v26-0015c002.douyinvod.com", "v5-gdwy.douyinvod.com", "v3-d.douyinvod.com", "v3-p.douyinvod.com", "v5-gdhy.douyinvod.com", "v26-cold.douyinvod.com", "v5-lite-a.douyinvod.com", "v5-i.douyinvod.com", "v5-g.douyinvod.com", "v26-qos-daily.douyinvod.com", "v5-dash.douyinvod.com", "v5-h.douyinvod.com", "v5-f.douyinvod.com", "v3-a.douyinvod.com", "v83.douyinvod.com", "v5-cold.douyinvod.com", "v3-y.douyinvod.com", "v26-x.douyinvod.com", "v27-ipv6.douyinvod.com", "v9-ipv6.douyinvod.com", "v5-yacu.douyinvod.com", "v29-ipv6.douyinvod.com", "v26-coldf.douyinvod.com", "v5.douyinvod.com", "v11.douyinvod.com", "v6-z.douyinvod.com", "v1.douyinvod.com", "v9-y.douyinvod.com", "v9-z.douyinvod.com", "v9.douyinvod.com", "v3-x.douyinvod.com", "v6-y.douyinvod.com", "v3-ipv6.douyinvod.com", "v5-e.douyinvod.com", "v3.douyinvod.com", "v6-ipv6.douyinvod.com", "v9-x.douyinvod.com", "v6-p.douyinvod.com", "v1-2p.douyinvod.com", "v1-p.douyinvod.com", "v1-ipv6.douyinvod.com", "v24.douyinvod.com", "v1-dy.douyinvod.com", "v6.douyinvod.com", "v6-x.douyinvod.com", "v26-ipv6.douyinvod.com", "v27.douyinvod.com", "v92.douyinvod.com", "v95.douyinvod.com", "douyinvod.com", "v26.douyinvod.com", "v29.douyinvod.com"});
        int begin = host.indexOf("https://");
        int end = host.indexOf(".com/");
        String substring = host.substring(begin + 8, end + 4);
        Integer anInt = redisUtil.getInt(substring);
        if (null != anInt && anInt > 0) {
            anInt += 1;
        } else {
            anInt = 1;
        }
        redisUtil.set(substring, anInt);
        if (domainName.contains(substring)) {
            return false;
        }
        return true;
    }

    /**
     * 获取字符串中网址
     *
     * @param input
     * @return java.lang.String
     * @author liuhy
     * @date 2021/5/29 20:21
     */
    public static String getUrl(String input) {
        //匹配douyin分享链接中短链接规则
        String regex = "(https:\\/\\/?[a-zA-Z].[a-zA-Z]{0,6}.[a-zA-Z]{0,3}/[a-zA-z1-9]{0,8}/)";
        //设置不区分大小写
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        //创建匹配器
        Matcher matcher = pattern.matcher(input);
        //验证匹配器是否发现
        if (matcher.find()) {
            //发现返回匹配串
//            System.out.println(matcher.group(1)); 关闭打印结果
            return matcher.group(1);
        }
        //未匹配返回空串
        return "";
    }

    /**
     * 下载视频保存到本地
     *
     * @param videoAddress
     * @author liuhy
     * @date 2021/5/29 20:04
     */
    private static void downloadVideo(String videoAddress) {
        int byteRead;
        try {
            URL url = new URL(videoAddress);
            //获取链接
            URLConnection conn = url.openConnection();
            //输入流
            InputStream inStream = conn.getInputStream();
            //封装一个保存文件的路径对象
            File fileSavePath = new File("d:/SanMu/1.mp4");
            //注:如果保存文件夹不存在,那么则创建该文件夹
            File fileParent = fileSavePath.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            //写入文件
            FileOutputStream fs = new FileOutputStream(fileSavePath);
            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            inStream.close();
            fs.close();
            System.out.println("\n-----视频保存路径-----\n" + fileSavePath.getAbsolutePath());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
}
