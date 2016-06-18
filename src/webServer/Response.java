package webServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
	private static final int BUFFER_SIZE = 1024;
	Request request;
	OutputStream output;

	/**
	 * ʹ�������������һ��Response��������������Ҫ�Լ�ȥ����
	 * @param output
	 */
	public Response(OutputStream output) {
		this.output = output;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	
	/**
	 * ��HTMLԴ���������������ʽ���ظ������
	 * @throws IOException
	 */
	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			//�����������е�������Դ��URI��ȥ��ȡ��Դ
			File file = new File(HttpServer.WEB_ROOT, request.getUri());
			if (file.exists()) {
				fis = new FileInputStream(file);
				int ch = fis.read(bytes, 0, BUFFER_SIZE);
				while (ch != -1) {
					output.write(bytes, 0, BUFFER_SIZE);
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
				System.out.println();
			} else {
				// file not found
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type:text/html\r\n"
						+ "Content-Length:23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
}
