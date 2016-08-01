package controller;

import model.DatabaseAdapter;
import object.Location;
import view.ClientMonitor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public class GPSReceiver {
	private final int PORT = 4700;
	private Processor dataProcessor;
	private ClientMonitor clientMonitor;
	private DatabaseAdapter adapter;
	private ArrayList<Location> locations = new ArrayList<>();
	private ArrayList<SocketThread> clientsThread = new ArrayList<>();

	public GPSReceiver() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		SocketThread receiveThread = null;
		clientMonitor = new ClientMonitor();
		adapter = new DatabaseAdapter();
		dataProcessor = new Processor(adapter);
		locations = adapter.getAllLocations();
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server start");
			int count = 1;
			new SocketMonitor().start();
			while (true) {
				socket = serverSocket.accept();
				receiveThread = new SocketThread(socket);
				receiveThread.setProcessor(dataProcessor);
				receiveThread.setMonitor(clientMonitor);
				clientsThread.add(receiveThread);
				receiveThread.start();
				System.out.println("Connected for client: " + count);
				count++;
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				serverSocket.close();
			}
			catch (IOException e) {
			}
		}
	}

	class SocketMonitor extends Thread {
		@Override
		public void run() {
			checkConnect();
		}

		private void checkConnect() {
			Socket checkingSocket = null;
			while (true) {
				if (clientsThread.size() != 0) {
					for (SocketThread thread : clientsThread) {
						checkingSocket = thread.getClientSocket();
						System.out.println(checkingSocket.isConnected());
						if(!checkingSocket.isConnected()){
							thread.interrupt();
						}
						System.out.println(thread.getID()+": "+thread.isAlive());
					}
				}
				try {
					sleep(1500);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new GPSReceiver();
	}
}
