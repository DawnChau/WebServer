package webServer;

import java.io.InputStream;

public class Request {
	private InputStream input;

	private String uri;

	/**
	 * 用输入流构造请求对象
	 * @param input 输入流，浏览器发送过来的输入流
	 */
	public Request(InputStream input) {
		this.input = input;
	}

	/**
	 * 对输入流进行解析
	 */
	public void parse() {
		// Read a set of characters from the socket
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = input.read(buffer);
		} catch (Exception e) {
			e.printStackTrace();
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			request.append((char) buffer[j]);
		}
		//将请求头信息打印出来
		System.out.print(request.toString());
		//从输入流中解析要请求的资源URI
		uri = parseUri(request.toString());
	}

	/**
	 * 从输入流中解析要请求的资源URI
	 * @param requestString 输入流字符串
	 * @return 返回要请求的资源URI
	 */
	public String parseUri(String requestString) {
		int index1, index2;
		index1 = requestString.indexOf(" ");
		if (index1 != -1) {
			index2 = requestString.indexOf(" ", index1 + 1);
			if (index2 > index1) {
				return requestString.substring(index1 + 1, index2);
			}
		}
		return null;
	}

	public String getUri() {
		return this.uri;
	}
}
