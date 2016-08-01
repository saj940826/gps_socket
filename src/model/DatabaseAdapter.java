package model;

import object.Location;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class DatabaseAdapter extends DatabaseHelper{
	public DatabaseAdapter() {
		super();
	}

	public int addLocation(Location location) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = con.prepareStatement("INSERT INTO Locations (phone, longitude, latitude, created_at) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, location.getPhone());
			statement.setDouble(2, location.getLongitude());
			statement.setDouble(3, location.getLatitude());
			statement.setTimestamp(4, new Timestamp(location.getCreated_at().getTime()));
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}
		catch (SQLException e) {
			super.SQLCatch(e);
		}
		return -1;
	}

	public ArrayList<Location> getAllLocations() {
		ArrayList<Location> locations = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Location location;
		try {
			statement = con.prepareStatement("SELECT * FROM locations");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				location = new Location(resultSet.getString("phone"), new Date(resultSet.getDate("created_at").getTime()),resultSet.getDouble("longitude"),resultSet.getDouble("latitude"));
				locations.add(location);
			}
		}
		catch (SQLException e) {
			super.SQLCatch(e);
		}
		return locations;
	}

	public ArrayList<Location> getLocationsByPhone(String phone) {
		ArrayList<Location> locations = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Location location;
		try {
			statement = con.prepareStatement("SELECT * FROM locations WHERE phone=?");
			statement.setString(1, phone);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				location = new Location(resultSet.getString("phone"), new Date(resultSet.getDate("created_at").getTime()),resultSet.getDouble("longitude"),resultSet.getDouble("latitude"));
				locations.add(location);
			}
		}
		catch (SQLException e) {
			super.SQLCatch(e);
		}
		return locations;
	}

	public ArrayList<Location> getLocationsByDate(Date date) {
		ArrayList<Location> locations = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Location location;
		Date nextDay = new Date(date.getTime() + TimeUnit.DAYS.toMillis(1));
		try {
			statement = con.prepareStatement("SELECT * FROM locations WHERE created_at >= ? and created_at < ?");
			statement.setDate(1, new java.sql.Date(date.getTime()));
			statement.setDate(2, new java.sql.Date(nextDay.getTime()));
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				location = new Location(resultSet.getString("phone"), new Date(resultSet.getDate("created_at").getTime()),resultSet.getDouble("longitude"),resultSet.getDouble("latitude"));
				locations.add(location);
			}
		}
		catch (SQLException e) {
			super.SQLCatch(e);
		}
		return locations;
	}

	public ArrayList<Location> getLocationsByArea(Date date) {
		ArrayList<Location> locations = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Location location;
		Date nextDay = new Date(date.getTime() + TimeUnit.DAYS.toMillis(1));
		try {
			statement = con.prepareStatement("SELECT * FROM locations WHERE created_at >= ? and created_at < ?");
			statement.setDate(1, new java.sql.Date(date.getTime()));
			statement.setDate(2, new java.sql.Date(nextDay.getTime()));
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				location = new Location(resultSet.getString("phone"), new Date(resultSet.getDate("created_at").getTime()),resultSet.getDouble("longitude"),resultSet.getDouble("latitude"));
				locations.add(location);
			}
		}
		catch (SQLException e) {
			super.SQLCatch(e);
		}
		return locations;
	}

	public static void main(String[] args) {
		for (Location location : (new DatabaseAdapter()).getLocationsByPhone("+8613121455186")) {
			System.out.println(location);

		}
	}

}
