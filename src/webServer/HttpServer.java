package webServer;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	public static final String WEB_ROOT = "webroot";

	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	private boolean shutdown = false;

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.await();
	}
	/**
	 * 等待客户端的连接，等待客户端发送的Request
	 */
	public void await() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		//只要没有被关闭
		while (!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				//用输入流构造请求对象，并解析
				Request request = new Request(input);
				request.parse();

				//创建Response对象
				Response response = new Response(output);
				response.setRequest(request);
				//将html代码显示到浏览器上
				response.sendStaticResource();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
