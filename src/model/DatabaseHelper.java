package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatabaseHelper {
	protected Connection con = null;

	public DatabaseHelper() {
        try {
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/gpsdb","root","Blss_0826");
        }catch (SQLException e){
        	SQLCatch(e);
        }
	}
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			SQLCatch(e);
		}
	}
	public java.sql.Date convertToDate(String d){
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf1.parse(d);
		} catch (ParseException e) {
			return null;
		}
		return new java.sql.Date(date.getTime()); 
	}
	
	public String convertToString(Date d){
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		try{
			return sdf1.format(d);
		}catch(Exception e){
		}
		return "";
	}
	
	public Time convertToTime(String t){
		return Time.valueOf(t);
	}
	
	public String convertToString(Time t){
		return t.toString();
	}
	
	public String convertToString(Time t, boolean x){
		return t.toString().substring(0, t.toString().length()-3);
	}

    protected void SQLCatch(SQLException e){
    	System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
        //Logger log = Logger.getLogger("DB Helper");
        //log.log(Level.SEVERE, e.getMessage(), e);
    }

	public static void main(String[] args) {
		DatabaseHelper databaseHelper = new DatabaseHelper();
	}
}
