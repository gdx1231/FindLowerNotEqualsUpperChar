/**
 * 
 */
package com.gdxsoft.temp;

import java.io.UnsupportedEncodingException;

/**
 * 遍历UTF8中的小写转大写后长度大于一的字符
 * @author admin
 *
 */
public class FindLowerNotEqualsUpperChar {
	public static void main(String[] args) throws UnsupportedEncodingException {

		FindLowerNotEqualsUpperChar o = new FindLowerNotEqualsUpperChar();
		o.findLowerNotEqualsUpperChar();
	}

	/**
	 * 遍历3字节的utf8
	 * @throws UnsupportedEncodingException
	 * 
	 */
	public void findLowerNotEqualsUpperChar() throws UnsupportedEncodingException {
		// Unicode符号范围     | UTF-8编码方式
		// 0000 0000-0000 007F | 0xxxxxxx（单字节）
		// 0000 0080-0000 07FF | 110xxxxx 10xxxxxx（2字节）
		// 0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx（3字节）
		// 0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx（4字节）
		
		// 
		// 第零字节开始
		byte start = bit2byte("1110xxxx".replace("x", "0"));
		// 第零字节结束
		byte end = bit2byte("1110xxxx".replace("x", "1"));

		// 第1、第2字节开始
		byte extStart = bit2byte("10xxxxxx".replace("x", "0"));
		// 第1、第2字节结束
		byte extEnd = bit2byte("10xxxxxx".replace("x", "1"));

		int total = 0;

		StringBuilder sb = new StringBuilder();
		for (byte byte0 = start; byte0 <= end; byte0++) {
			for (byte byte1 = extStart; byte1 <= extEnd; byte1++) {
				for (byte byte2 = extStart; byte2 <= extEnd; byte2++) {
					// 3字节字符的二进制
					byte[] charBytes = new byte[3];
					charBytes[0] = byte0;
					charBytes[1] = byte1;
					charBytes[2] = byte2;

					// 转换为字符
					String newChar = new String(charBytes, "utf-8");
					// 大写
					String newCharUpper = newChar.toUpperCase();
					if (newChar.length() == newCharUpper.length()) {
						// 长度一致
						continue;
					}
					String hexUtf8 = this.bytes2hex(charBytes); // utf8 内码
					String hexUnicode = Integer.toHexString(newChar.codePointAt(0)); // unicode 内码
					sb.append(newChar + "\t" + newCharUpper + "\t" + hexUtf8 + "\t" + hexUnicode + "\n");
					total++;
				}
			}
		}
		System.out.println("total：" + total);
		System.out.println(sb);
	}

	/**
	 * 将二进制转换为字节
	 * 
	 * @param binary 二进制字符串
	 * @return
	 */
	private byte bit2byte(String binary) {
		byte result = 0;
		for (int i = binary.length() - 1, j = 0; i >= 0; i--, j++) {
			byte b = Byte.parseByte(binary.charAt(i) + "");
			if (b == 1) {
				result += b * Math.pow(2, j);
			}
		}
		return result;
	}

	/**
	 * 将byte数组转换为16进制字符串
	 * 
	 * @param b byte数组
	 * @return 16进制字符串
	 */
	private String bytes2hex(byte[] bytes) {
		String stmp = "";
		StringBuilder sb = new StringBuilder();
		for (int n = 0; n < bytes.length; n++) {
			stmp = (java.lang.Integer.toHexString(bytes[n] & 0XFF));
			if (stmp.length() == 1)
				sb.append("0" + stmp);
			else
				sb.append(stmp);
		}
		return sb.toString().toUpperCase();
	}

}
