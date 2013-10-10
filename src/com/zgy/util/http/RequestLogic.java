package com.zgy.util.http;

public class RequestLogic {

	public void doSomeThing(final RequestObserver observer) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					// 发起http请求，数据库操作等
					DoSomeThingResponse response = RequestUtil.getInstance().doSomeThing("", true);

					if (response != null) {
						boolean result = response.getResult();
						observer.doSomeThingFinished(result);
					} else {
						observer.doSomeThingFinished(false);
					}

				} catch (Exception e) {
					e.printStackTrace();
					observer.doSomeThingFinished(false);
				}
			}
		}).start();
	}
}
