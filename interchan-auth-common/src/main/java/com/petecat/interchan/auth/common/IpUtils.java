package com.petecat.interchan.auth.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName:  IpUtils   
 * @Description:Ip工具类   
 * @author: mhuang
 * @date:   2017年7月18日 上午9:32:23
 */
public class IpUtils {
	
	public static String getIp(HttpServletRequest request) {
		 String ipAddress = request.getHeader("x-forwarded-for");  
         if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
             ipAddress = request.getHeader("Proxy-Client-IP");  
         }  
         if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
             ipAddress = request.getHeader("WL-Proxy-Client-IP");  
         }  
         if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
             ipAddress = request.getRemoteAddr();  
             if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                 //根据网卡取本机配置的IP  
                 InetAddress inet=null;  
                 try {  
                     inet = InetAddress.getLocalHost();  
                 } catch (UnknownHostException e) {  
                     e.printStackTrace();  
                 }  
                 ipAddress= inet.getHostAddress();  
             }  
         }  
         //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
         if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
             if(ipAddress.indexOf(",")>0){  
                 ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
             }  
         }  
         return ipAddress;   
    }
}
