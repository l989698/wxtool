package com.tool.wxtoolproject.api.common.util;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ImageAnalysisUtil {
	protected static final Logger logger = LoggerFactory.getLogger(ImageAnalysisUtil.class);

	private static String APP_ID = "14241231";
	private static String API_KEY = "hhMbkAZ8XUhIvxMUDwAR4Grx";
	private static String SECRET_KEY = "uz7xUcUGVDevnua6dnvPqfhLOC88WDi8";

	public static String analysisLocalCharactor(byte[] image) {
		// 传入可选参数调用接口
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("detect_direction", "true");
		options.put("probability", "true");
		JSONObject res = client.basicGeneral(image, options);
		return res.toString(2);
	}

	public static String analysisRemoteCharactor(String remoteURL) {
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("language_type", "CHN_ENG");
		options.put("detect_direction", "true");
		options.put("detect_language", "true");
		options.put("probability", "true");
		JSONObject res = client.basicGeneralUrl(remoteURL, options);
		return res.toString(2);
	}

}
