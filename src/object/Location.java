package object;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class Location {
	private int id;
	private String phone;
	private Date created_at;
	private String ip;
	private double longitude;
	private double latitude;
	private User user;

	public Location(String phone, Date created_at, double longitude, double latitude) {
		this.phone = phone;
		this.created_at = created_at;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getPhone() {
		return phone;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public String getIp() {
		return ip;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public String toString() {
		return this.phone + ": " + this.longitude + ", " + this.latitude + " at " + this.created_at.toString();
	}
}
