package controller;

import model.DatabaseAdapter;
import object.Location;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public class Processor implements Runnable{
	private ConcurrentLinkedQueue<Location> locationsQ = new ConcurrentLinkedQueue<>();
	private volatile int locationsNumber;
	private DatabaseAdapter databaseAdapter;

	public Processor(DatabaseAdapter databaseAdapter){
		this.databaseAdapter = databaseAdapter;
		Thread dataIn = new Thread(this);
		dataIn.start();
	}

	public void addLocationToQ(Location location){
		locationsQ.add(location);
		locationsNumber++;
	}

	@Override
	public void run(){
		Location firstLocation;
		while(true){
			if(!locationsQ.isEmpty()){
				firstLocation = locationsQ.poll();
				databaseAdapter.addLocation(firstLocation);
				locationsNumber--;
				System.out.println("Data stored");
			}
			else{
				try{
					Thread.currentThread().sleep(500);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}

	public int getLocationsNumber(){
		return locationsNumber;
	}

}
