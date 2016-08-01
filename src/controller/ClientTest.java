package controller;

import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class ClientTest {
	public static void main(String[] args) {
		new Thread(){
			@Override
			public void run() {
				try {
					//1.建立客户端socket连接，指定服务器位置及端口
					Socket socket =new Socket("192.168.1.103",4700);
					//2.得到socket读写流
					OutputStream os=socket.getOutputStream();
					PrintWriter pw=new PrintWriter(os);
					//输入流
					InputStream is=socket.getInputStream();
					BufferedReader br=new BufferedReader(new InputStreamReader(is));
					//3.利用流按照一定的操作，对socket进行读写操作
					String info="\"+8613121455186\"$GPRMC,150526.00,A,3958.23584,N,11619.25402,E,1.063,,150716,,,A*75";

					pw.write(info);
					pw.flush();
					int count = 0;
					while (count<50) {
						count++;
						try {
							Thread.sleep(1000);
						}
						catch (InterruptedException e) {

						}
					}

					socket.shutdownOutput();
					//接收服务器的相应
					String reply=null;
					//4.关闭资源
					br.close();
					is.close();
					pw.close();
					os.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			@Override
			public void run() {
				try {
					//1.建立客户端socket连接，指定服务器位置及端口
					Socket socket =new Socket("192.168.1.103",4700);
					//2.得到socket读写流
					OutputStream os=socket.getOutputStream();
					PrintWriter pw=new PrintWriter(os);
					//输入流
					InputStream is=socket.getInputStream();
					BufferedReader br=new BufferedReader(new InputStreamReader(is));
					//3.利用流按照一定的操作，对socket进行读写操作
					String info="\"+8613121455186\"$GPRMC,159526.00,A,3958.23584,N,11619.25402,E,1.063,,150716,,,A*75";

					pw.write(info);
					pw.flush();
					int count = 0;
					socket.shutdownOutput();
					//接收服务器的相应
					String reply=null;
					//4.关闭资源
					br.close();
					is.close();
					pw.close();
					os.close();
					while (count<50) {
						count++;
						try {
							Thread.sleep(200);
						}
						catch (InterruptedException e) {

						}
					}
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			@Override
			public void run() {
				try {
					//1.建立客户端socket连接，指定服务器位置及端口
					Socket socket =new Socket("192.168.1.103",4700);
					//2.得到socket读写流
					OutputStream os=socket.getOutputStream();
					PrintWriter pw=new PrintWriter(os);
					//输入流
					InputStream is=socket.getInputStream();
					BufferedReader br=new BufferedReader(new InputStreamReader(is));
					//3.利用流按照一定的操作，对socket进行读写操作
					String info="\"+8613121455186\"$GPRMC,15626.00,A,3958.23584,N,11619.25402,E,1.063,,150716,,,A*75";

					pw.write(info);
					pw.flush();
					int count = 0;
					while (count<50) {
						count++;
						try {
							Thread.sleep(1000);
						}
						catch (InterruptedException e) {

						}
					}

					socket.shutdownOutput();
					//接收服务器的相应
					String reply=null;
					//4.关闭资源
					br.close();
					is.close();
					pw.close();
					os.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
