package webServer;

import java.io.InputStream;

public class Request {
	private InputStream input;

	private String uri;

	/**
	 * �������������������
	 * @param input ����������������͹�����������
	 */
	public Request(InputStream input) {
		this.input = input;
	}

	/**
	 * �����������н���
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
		//������ͷ��Ϣ��ӡ����
		System.out.print(request.toString());
		//���������н���Ҫ�������ԴURI
		uri = parseUri(request.toString());
	}

	/**
	 * ���������н���Ҫ�������ԴURI
	 * @param requestString �������ַ���
	 * @return ����Ҫ�������ԴURI
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
