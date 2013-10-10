package com.zgy.util.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class RequestUtil {

	// 单例
	private static RequestUtil mRequest = null;
	private static HttpUtil mHttpUtil;

	private RequestUtil() {
	}

	public static RequestUtil getInstance() {
		if (mRequest == null) {
			mRequest = new RequestUtil();
		}
		if (mHttpUtil == null) {
			mHttpUtil = HttpUtil.getInstence();
		}
		return mRequest;
	}

	public DoSomeThingResponse doSomeThing(String requestUrl, boolean isPost) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// TODO 请求参数
		params.add(new BasicNameValuePair("arg1", ""));
		params.add(new BasicNameValuePair("arg2", ""));
		DoSomeThingResponse response = new DoSomeThingResponse();
		// 执行
		mHttpUtil.sendRequest(response, requestUrl, params, isPost);

		return response;

	}
}
