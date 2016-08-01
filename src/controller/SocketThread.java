package controller;

import object.Location;
import view.ClientMonitor;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public class SocketThread extends Thread {
	private Socket clientSocket;
	private BufferedReader input;
	private PrintWriter output;
	private Processor processor;
	private ClientMonitor clientMonitor;
	private static int ID = 0;
	private int receivedNumber = 0;
	private String statusString;
	private int status = 0;
	private String ip;
	public final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final SimpleDateFormat TODAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public SocketThread(Socket socket) {
		super(Integer.toString(++ID));
		this.clientSocket = socket;
		InputStreamReader inputStream;
		OutputStreamWriter outputStream;
		ip = socket.getInetAddress().toString();
		try {
			inputStream = new InputStreamReader(clientSocket.getInputStream());
			outputStream = new OutputStreamWriter(clientSocket.getOutputStream());
			input = new BufferedReader(inputStream);
			output = new PrintWriter(outputStream);
		}
		catch (IOException e) {

		}
		statusString = "Init";
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void run() {
		String string = null;
		statusString = "Connected";
		status = ClientMonitor.BLOCKING;
		clientMonitor.addClient(this);
		try {
			while ((string = input.readLine()) != null  && string.length()> 0) {
				System.out.println(string);
				if (isGPSData(string)) {
					String[] data = string.split("\"");
					String phone = data[1];
					String[] locations = data[2].split(",");
					if (locations.length >= 8 && !locations[5].isEmpty() && !locations[5].isEmpty()) {
						statusString = "Transmitting";
						status = ClientMonitor.NORMAL;
						receivedNumber++;
						Date dateTime = generateDate(locations[1]);

						String longitudeRowData = locations[5];
						double longD = Double.parseDouble(longitudeRowData.substring(0, 3));
						double longM = Double.parseDouble(longitudeRowData.substring(3));
						double longitude = longD + longM / 60.0;

						String latitudeRowData = locations[3];
						double latD = Double.parseDouble(latitudeRowData.substring(0, 2));
						double latM = Double.parseDouble(latitudeRowData.substring(2));
						double latitude = latD + latM / 60.0;

						processor.addLocationToQ(new Location(phone,dateTime,longitude,latitude));
						System.out.println("Data in Q");
						clientMonitor.updateClient(this);
					}else{
						statusString = "Waiting GPS";
						status = ClientMonitor.BLOCKING;
						clientMonitor.updateClient(this);
					}
				}
			}
			System.out.println("Disconnected1");
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				input.close();
				output.close();
				clientSocket.close();
				statusString = "Disconnected";
				status = ClientMonitor.OFFLINE;
				clientMonitor.updateClient(this);
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Disconnected2");
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public void setMonitor(ClientMonitor clientMonitor) {
		this.clientMonitor = clientMonitor;
	}

	public int getStatus() {
		return status;
	}

	public int getReceivedNumber() {
		return receivedNumber;
	}

	public String getID() {
		return this.getName();
	}

	public String getStatusString(){
		return statusString;
	}
	public String getIP() {
		return ip;
	}

	private static boolean isGPSData(String line) {
		return line.contains("$GPRMC");
	}

	private Date generateDate(String timeRow){
		Date day = new Date();
		String today = TODAY_FORMAT.format(day);
		String time = timeRow.substring(0, 2) + ":" + timeRow.substring(2, 4) + ":" + timeRow.substring(4, 6);
		Date dateTime = null;
		try {
			dateTime = DATETIME_FORMAT.parse(today + " " + time);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(dateTime);
		return dateTime;
	}
}
