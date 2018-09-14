package com.fims.common.utils;

public class ServerUtil {

	
	public static String SetDynamicPassword(byte[] sn,byte[] count)
	{
		String password = "";
		int tempnum;

		tempnum=(byte)(count[0]+55)^(byte)(count[1]+66)^(byte)(count[2]+77)^(byte)(count[3]+88);
		for(int i=0;i<6;i++)
		{
			int x;
			x =(sn[i]+i*2+3)|(tempnum+i*5+1);
			x ^=tempnum-i;
			x &=0xffff;
		//	System.out.println("x:"+x);
			password +=x%10;
		}
		return password;
	}  
	
	
	public static byte[] hexStringToBytes(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        return d;  
    }
	
	private static byte charToByte(char c) {  
        return (byte) "0123456789ABCDEF".indexOf(c);  
    }

	
	/**
	 * 将16进制值的String解释成byte[]（如"A1B2C3" -->> byte[] = { 0xA1 ,0xB2 , 0xC3}
	 * 
	 * @param hexString
	 * @return 解释后的 byte[]
	 */
	public static byte[] parseHexStringToByteArr(String hexString)
	{

		if (hexString == null || hexString.trim().equals(""))
			return null;

		// 每两个字符描述一个16进制的字节
		int len = hexString.length() / 2;

		byte[] byteArr = new byte[len];

		try
		{
			for (int i = 0; i < len; i++)
			{
				String tempStr = hexString.substring(i * 2, i * 2 + 2);
				byteArr[i] = (byte) Integer.parseInt(tempStr, 16);
			}
		}
		catch (Exception e)
		{
			System.out.println("转换中发生异常：" + e.getMessage());
		}

		return byteArr;
	}
	
	public static void main(String[] args) {
		byte[] ss = parseHexStringToByteArr("018000000001");
		byte[] count = new byte[]{0x12,0x34,0x56,0x78,(byte)0x90 };
		System.out.println(SetDynamicPassword(parseHexStringToByteArr("018000000001"),parseHexStringToByteArr("1234567890")));
		System.out.println(SetDynamicPassword(ss,count));
	}
}
