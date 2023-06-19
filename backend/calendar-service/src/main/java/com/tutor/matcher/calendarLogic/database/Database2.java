package com.tutor.matcher.calendarLogic.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Database2 {
	
	private static Connection getConnection() {
		return DBCPDataSource.getConnection();
	}
	
	public static boolean isCalendarExist(long userId) {
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select userId from calendars where userId = ?;");
            prepStmt.setLong(1, userId);
            ResultSet result = prepStmt.executeQuery();
            return result.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public static boolean insertCalendar(long userId) {
		if(isCalendarExist(userId)) {
			return false;
		}
        try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into calendars(userId) values (?);");
            prepStmt.setLong(1, userId);
            prepStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    public static boolean updateCalendar(long userId, String googleId, String googleAvailableId) {
        try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "update calendars set googleId = ?, googleAvailableId = ? where userID = ?;");
            prepStmt.setString(1, googleId);
            prepStmt.setString(2, googleAvailableId);
            prepStmt.setLong(3, userId);
            prepStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    public static boolean removeCalendar(long userId) {
        try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "delete from calendars where userID = ?;");
            prepStmt.setLong(1, userId);
            prepStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    public static Calendar getCalendar(long userId) {
        try (Connection conn = getConnection()){
        	Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT googleId, googleAvailableId "
            		+ "FROM calendars WHERE userId = " + userId);
            if(result.next()) {
            	String googleCalendar = result.getString("googleId");
            	String googleAvailableCalendar = result.getString("googleAvailableId");
                return new Calendar(userId, googleCalendar, googleAvailableCalendar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
    public static boolean insertEvent(SingleEvent event) {
        try (Connection conn = getConnection()){
	    	if(!insertEvent(conn, event)) {
	    		return false;
	    	}

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into singleEvents(eventId, dateRange)"
                    + " values (?, '[" + event.getStartDate().format(formatter) + "," + 
                    event.getEndDate().format(formatter) + ")');");
            
            prepStmt.setLong(1, event.getEventId());
            prepStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    public static boolean insertEvent(WeeklyEvent event) {
        try (Connection conn = getConnection()){
	    	if(!insertEvent(conn, event)) {
	    		return false;
	    	}
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	    	
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into weeklyEvents(eventId, weekDay, timeRange)"
                    + " values (?, ?, '[" + event.getStartTime().format(formatter) + "," + 
                    event.getEndTime().format(formatter) + ")');");
            prepStmt.setLong(1, event.getEventId());
            prepStmt.setInt(2, event.getWeekDay());
            prepStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    private static boolean insertEvent(Connection conn, BaseEvent event) {
    	try {
    		Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("Select nextval('events_eventid_seq'::regclass) as new_id;");
            result.next();
            event.setEventId(result.getLong("new_id"));
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into events(eventId, calendarId, name, googleId, isAvailable)"
                    + " values (?, ?, ?, NULL, ?);");
            prepStmt.setLong(1, event.getEventId());
            prepStmt.setLong(2, event.getCalendarId());
            prepStmt.setString(3, event.getName());
            prepStmt.setBoolean(4, event.isAvailable());
            prepStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    public static boolean updateEvent(long calendarId, long eventId, String googleId) {
        try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "update events set googleId = ? where calendarId = ? and eventId = ?;");
            prepStmt.setString(1, googleId);
            prepStmt.setLong(2, calendarId);
            prepStmt.setLong(3, eventId);
            prepStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    public static boolean removeEvent(long calendarId, long eventId) {
        try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement("delete from weeklyEvents where eventId = ?;");
            prepStmt.setLong(1, eventId);
            prepStmt.execute();
            prepStmt = conn.prepareStatement("delete from singleEvents where eventId = ?;");
            prepStmt.setLong(1, eventId);
            prepStmt.execute();
            prepStmt = conn.prepareStatement(
                    "delete from events where calendarID = ? and eventId = ?;");
            prepStmt.setLong(1, calendarId);
            prepStmt.setLong(2, eventId);
            prepStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    public static List<BaseEvent> getEvents(long calendarId) {
        List<BaseEvent> events = new ArrayList<BaseEvent>();
        try (Connection conn = getConnection()){
    		PreparedStatement prepStmt = conn.prepareStatement(
    				"SELECT events.eventId as event_id, name, googleId, isAvailable, "
    				+ "weekDay, lower(timeRange) as start_time, upper(timeRange) as end_time, "
    				+ "lower(dateRange) as start_date, upper(dateRange) as end_date "
    				+ "FROM events "
    				+ "LEFT JOIN singleEvents ON events.eventId = singleEvents.eventId "
    				+ "LEFT JOIN weeklyEvents ON events.eventId = weeklyEvents.eventId "
    				+ "WHERE calendarId = ?");
    		prepStmt.setLong(1, calendarId);
            ResultSet result = prepStmt.executeQuery();
            long eventId;
            boolean isAvailable;
            String name, googleId;
            LocalDateTime startDate, endDate;
            int weekDay;
            LocalTime startTime, endTime;
            while(result.next()) {
            	eventId = result.getInt("event_id");
            	isAvailable = result.getBoolean("isAvailable");
            	name = result.getString("name");
            	googleId = result.getString("googleId");
            	weekDay = result.getInt("weekDay");
            	startDate = result.getObject("start_date", LocalDateTime.class);
            	endDate = result.getObject("end_date", LocalDateTime.class);
            	startTime = result.getObject("start_time", LocalTime.class);
            	endTime = result.getObject("end_time", LocalTime.class);
            	if(startDate != null) {
            		events.add(new SingleEvent(eventId, calendarId, name, isAvailable, googleId, startDate, endDate));
            	}
            	else {
            		events.add(new WeeklyEvent(eventId, calendarId, name, isAvailable, googleId, startTime, endTime, weekDay));
            	}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }
    
    public static List<SingleEvent> getSingleEvents(long calendarId, LocalDateTime globalStartDate, LocalDateTime globalEndDate) {
        List<SingleEvent> events = new ArrayList<SingleEvent>();
        try (Connection conn = getConnection()){
    		PreparedStatement prepStmt = conn.prepareStatement(
    				"SELECT events.eventId as event_id, name, googleId, isAvailable, "
    				+ "lower(dateRange) as start_date, upper(dateRange) as end_date "
    				+ "FROM events "
    				+ "JOIN singleEvents ON events.eventId = singleEvents.eventId "
    				+ "WHERE calendarId = ? AND dateRange && '[ " + globalStartDate.toString() + ", " +
    				globalEndDate.toString() + ")' = true");
    		prepStmt.setLong(1, calendarId);
            mapSingleEvents(calendarId, events, prepStmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }
    
    public static List<SingleEvent> getSingleEvents(long calendarId) {
        List<SingleEvent> events = new ArrayList<SingleEvent>();
        try (Connection conn = getConnection()){
    		PreparedStatement prepStmt = conn.prepareStatement(
    				"SELECT events.eventId as event_id, name, googleId, isAvailable, "
    				+ "lower(dateRange) as start_date, upper(dateRange) as end_date "
    				+ "FROM events "
    				+ "JOIN singleEvents ON events.eventId = singleEvents.eventId "
    				+ "WHERE calendarId = ? ");
    		prepStmt.setLong(1, calendarId);
            mapSingleEvents(calendarId, events, prepStmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }


	private static void mapSingleEvents(long calendarId, List<SingleEvent> events, PreparedStatement prepStmt)
			throws SQLException {
		ResultSet result = prepStmt.executeQuery();
		long eventId;
		boolean isAvailable;
		String name, googleId;
		LocalDateTime startDate, endDate;
		while(result.next()) {
			eventId = result.getInt("event_id");
			isAvailable = result.getBoolean("isAvailable");
			name = result.getString("name");
			googleId = result.getString("googleId");
			startDate = result.getObject("start_date", LocalDateTime.class);
			endDate = result.getObject("end_date", LocalDateTime.class);
			events.add(new SingleEvent(eventId, calendarId, name, isAvailable, googleId, startDate, endDate));
		}
	}
    
    public static List<WeeklyEvent> getWeeklyEvents(long calendarId) {
        List<WeeklyEvent> events = new ArrayList<WeeklyEvent>();
        try (Connection conn = getConnection()){
    		PreparedStatement prepStmt = conn.prepareStatement(
    				"SELECT events.eventId as event_id, name, googleId, isAvailable, "
    				+ "weekDay, lower(timeRange) as start_time, upper(timeRange) as end_time "
    				+ "FROM events "
    				+ "JOIN weeklyEvents ON events.eventId = weeklyEvents.eventId "
    				+ "WHERE calendarId = ?");
    		prepStmt.setLong(1, calendarId);
            ResultSet result = prepStmt.executeQuery();
            long eventId;
            boolean isAvailable;
            String name, googleId;
            int weekDay;
            LocalTime startTime, endTime;
            while(result.next()) {
            	eventId = result.getInt("event_id");
            	isAvailable = result.getBoolean("isAvailable");
            	name = result.getString("name");
            	googleId = result.getString("googleId");
            	weekDay = result.getInt("weekDay");
            	startTime = result.getObject("start_time", LocalTime.class);
            	endTime = result.getObject("end_time", LocalTime.class);
            	events.add(new WeeklyEvent(eventId, calendarId, name, isAvailable, googleId, startTime, endTime, weekDay));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }
}
