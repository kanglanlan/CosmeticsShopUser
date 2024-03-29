package com.meida.cosmeticsshopuser.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    /**
     * md5���ܷ���
     *
     * @param password
     * @return
     */
    public static String md5Password(String password, int code) {
        try {
            // �õ�һ����ϢժҪ��
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // ��ûһ��byte ��һ�������� 0xff;
            for (byte b : result) {
                // ������
                int number = b & 0xff;// ����
                String str = Integer.toHexString(number);
                // System.out.println(str);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
//			String str="";
//			if(code==1){
//				str=buffer.toString().substring(8, 24).toUpperCase();
//			}else if(code==2){
//				str=buffer.toString().toLowerCase();
//			}else if(code==3){
//				str=buffer.toString().substring(8, 24).toLowerCase();
//			}else if(code==0){
//				str=buffer.toString().toUpperCase();
//			}
            // ��׼��md5���ܺ�Ľ��
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    public static String md5Password(String password) {
        try {
            // �õ�һ����ϢժҪ��
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // ��ûһ��byte ��һ�������� 0xff;
            for (byte b : result) {
                // ������
                int number = b & 0xff;// ����
                String str = Integer.toHexString(number);
                // System.out.println(str);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
//			String str="";
//			if(code==1){
//				str=buffer.toString().substring(8, 24).toUpperCase();
//			}else if(code==2){
//				str=buffer.toString().toLowerCase();
//			}else if(code==3){
//				str=buffer.toString().substring(8, 24).toLowerCase();
//			}else if(code==0){
//				str=buffer.toString().toUpperCase();
//			}
            // ��׼��md5���ܺ�Ľ��
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

}
