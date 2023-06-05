package com.tutor.matcher.userLogic.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
	
	private static Connection getConnection() {
		return DBCPDataSource.getConnection();
	}
	
	public static void insertUser(User user) {
		try (Connection conn = getConnection()){
			long addresId = setUserAddress(user.getAddress(), conn);
			conn.setAutoCommit(false);
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into users(name, surname, phone, mail, addressid)"
                    + " values (?, ?, ?, ?, ?);");
            prepStmt.setString(1, user.getName());
            prepStmt.setString(2, user.getSurname());
            prepStmt.setString(3, user.getPhone());
            prepStmt.setString(4, user.getMail());
            prepStmt.setLong(5, addresId);
            prepStmt.execute();
    		if(user instanceof Teacher) {			
    			user.setId(getUserIdByEmail(user.getMail()));
    			insertTeacher((Teacher)user, conn);
    		}
    		conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	private static void insertTeacher(Teacher teacher, Connection conn) throws SQLException{
        PreparedStatement prepStmt = conn.prepareStatement(
                "insert into teachers(userId, hourlyrate, maxdistance, verification, teachingLevel)"
                + " values (?, ?, ?, ?, ?);");
        prepStmt.setLong(1, teacher.getId());
        prepStmt.setBigDecimal(2, teacher.getHourlyRate());
        prepStmt.setInt(3, teacher.getMaxDistance());
        prepStmt.setString(4, teacher.getVerification());
        prepStmt.setString(5, teacher.getTeachingLevel());
        prepStmt.execute();
		for(String subject : teacher.getSubjects()) {
			insertSubject(teacher.getId(), subject, conn);
		}
		for(String lessonForm : teacher.getLessonForm()) {
			insertLessonForm(teacher.getId(), lessonForm, conn);
		}
	}
	
	private static void insertSubject(long teacherId, String subject, Connection conn)  throws SQLException{
        PreparedStatement prepStmt = conn.prepareStatement(
                "insert into subjects_teachers(teacherId, subject)"
                + " values (?, ?);");
        prepStmt.setLong(1, teacherId);
        prepStmt.setString(2, subject);
        prepStmt.execute();
	}
	
	private static void insertLessonForm(long teacherId, String lessonForm, Connection conn)  throws SQLException{
        PreparedStatement prepStmt = conn.prepareStatement(
                "insert into lessonForms_teachers(teacherId, lessonForm)"
                + " values (?, ?);");
        prepStmt.setLong(1, teacherId);
        prepStmt.setString(2, lessonForm);
        prepStmt.execute();
	}
	
	private static long setUserAddress(Address address, Connection conn) throws SQLException{
		Long addressId = getAdrressId(address);
		if(addressId == null) {
			addressId = insertAddress(address, conn);
		}
		return addressId;
	}
	
	private static long insertAddress(Address address, Connection conn) throws SQLException{
		Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery("Select nextval('addresses_addressid_seq'::regclass) as new_id;");
        result.next();
        address.setId(result.getLong("new_id"));
        PreparedStatement prepStmt = conn.prepareStatement(
                "insert into addresses(addressid, street, city, zip)"
                + " values (?, ?, ?, ?);");
        prepStmt.setLong(1, address.getId());
        prepStmt.setString(2, address.getStreet());
        prepStmt.setString(3, address.getCity());
        prepStmt.setString(4, address.getZip());
        prepStmt.execute();
		return address.getId();
	}
	
	public static void updateUser(User user) {
		try (Connection conn = getConnection()){
			conn.setAutoCommit(false);
            PreparedStatement prepStmt = conn.prepareStatement(
                    "update users"
                    + " SET name=?, surname=?, mail=?, phone=?"
                    + "	where userid = ?");
            prepStmt.setString(1, user.getName());
            prepStmt.setString(2, user.getSurname());
            prepStmt.setString(3, user.getMail());
            prepStmt.setString(4, user.getPhone());
            prepStmt.setLong(5, user.getId());
            prepStmt.execute();
    		if(user instanceof Teacher) {			
    			updateTeacher((Teacher)user, conn);
    		}
    		user.getAddress().setId(getUserAddress(user.getId()).getId());
    		setUserAddress(user.getAddress(), conn);
    		conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	private static void updateTeacher(Teacher teacher, Connection conn) throws SQLException {
        PreparedStatement prepStmt = conn.prepareStatement(
                "update teachers"
                + " set hourlyrate=?, maxdistance=?, verification=?, teachingLevel=?"
                + " where userid=?;");
        prepStmt.setBigDecimal(1, teacher.getHourlyRate());
        prepStmt.setInt(2, teacher.getMaxDistance());
        prepStmt.setString(3, teacher.getVerification());
        prepStmt.setString(4, teacher.getTeachingLevel());
        prepStmt.setLong(5, teacher.getId());
        prepStmt.execute();
		updateSubjects(teacher.getId(), teacher.getSubjects(), conn);
		updateLessonForms(teacher.getId(), teacher.getLessonForm(), conn);
	}
	
	public static User getUser(long userId) {
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select name, surname, phone, mail from users"
                    + " where userId = ?;");
            prepStmt.setLong(1, userId);
            ResultSet result = prepStmt.executeQuery();
            if(result.next()) {
            	String name = result.getString("name");
            	String surname = result.getString("surname");
            	String phone = result.getString("phone");
            	String mail = result.getString("mail");
            	Address address = getUserAddress(userId);
            	return new User(userId, name, surname, phone, mail, address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return null;
	}
	
	public static Teacher getTeacher(long userId) {
		Teacher teacher = new Teacher(getUser(userId));
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select hourlyrate, maxdistance, verification, teachingLevel from teachers"
                     + " where userid=?;");
            prepStmt.setLong(1, userId);
            ResultSet result = prepStmt.executeQuery();
            if(result.next()) {
            	BigDecimal hourlyrate = result.getBigDecimal("hourlyrate");
            	int maxdistance = result.getInt("maxdistance");
            	String verification = result.getString("verification");
            	teacher.setHourlyRate(hourlyrate);
            	teacher.setMaxDistance(maxdistance);
            	teacher.setVerification(verification);
            	teacher.setLessonForm(getLessonFormsByTeacher(userId));
            	teacher.setTeachingLevel("teachingLevel");
            	teacher.setSubjects(getSubjectsByTeacher(userId));
            	return teacher;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return null;
	}
	
	public static User getUserOrTeacher(long userId) {
		Teacher t = getTeacher(userId);
		if(t == null) {
			return getUser(userId);
		}
		return t;
	}
	
	private static List<String> getSubjectsByTeacher(long techerId){
		ArrayList<String> list = new ArrayList<>();
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select subject from subjects_teachers"
                     + " where teacherid=?;");
            prepStmt.setLong(1, techerId);
            ResultSet result = prepStmt.executeQuery();
            while(result.next()) {
            	list.add(result.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return list;
	}
	
	private static List<String> getLessonFormsByTeacher(long techerId){
		ArrayList<String> list = new ArrayList<>();
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select lessonForm from lessonForms_teachers"
                     + " where teacherid=?;");
            prepStmt.setLong(1, techerId);
            ResultSet result = prepStmt.executeQuery();
            while(result.next()) {
            	list.add(result.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return list;
	}
	
	private static void deleteSubject(long techerId, String subject, Connection conn) throws SQLException{
        PreparedStatement prepStmt = conn.prepareStatement(
                "delete from subjects_teachers"
                 + " where teacherid=? and subject=?;");
        prepStmt.setLong(1, techerId);
        prepStmt.setString(2, subject);
        prepStmt.execute();
	}
	
	private static void deleteLessonForm(long techerId, String lessonForm, Connection conn) throws SQLException{
        PreparedStatement prepStmt = conn.prepareStatement(
                "delete from lessonForms_teachers"
                 + " where teacherid=? and lessonForm=?;");
        prepStmt.setLong(1, techerId);
        prepStmt.setString(2, lessonForm);
        prepStmt.execute();
	}
	
	private static void updateLessonForms(long teacherId, List<String> lessonForms, Connection conn) throws SQLException{
		List<String> old = getLessonFormsByTeacher(teacherId);
		for(String form : old) {
			if(!lessonForms.contains(form)) {
				deleteLessonForm(teacherId, form, conn);
			}
		}
		for(String form : lessonForms) {
			if(!old.contains(form)) {
				insertLessonForm(teacherId, form, conn);
			}
		}
	}
	
	private static void updateSubjects(long teacherId, List<String> subjects, Connection conn) throws SQLException{
		List<String> old = getSubjectsByTeacher(teacherId);
		for(String subject : old) {
			if(!subjects.contains(subject)) {
				deleteSubject(teacherId, subject, conn);
			}
		}
		for(String subject : subjects) {
			if(!old.contains(subject)) {
				insertSubject(teacherId, subject, conn);
			}
		}
	}
	
	private static Address getUserAddress(long userId) {
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select addresses.addressid, street, city, zip from addresses"
                    + " join users on users.addressid = addresses.addressid"
	                + " where userid = ?;");
            prepStmt.setLong(1, userId);
            ResultSet result = prepStmt.executeQuery();
            if(result.next()) {
            	String street = result.getString("street");
            	Long addressId = result.getLong("addressid");
            	String city = result.getString("city");
            	String zip = result.getString("zip");
            	return new Address(addressId, street, city, zip);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return null;
	}
	
	private static Long getAdrressId(Address address) {
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select addressid from addresses"
	                + " where street = ? and city = ? and zip = ?;");
            prepStmt.setString(1, address.getStreet());
            prepStmt.setString(2, address.getCity());
            prepStmt.setString(3, address.getZip());
            ResultSet result = prepStmt.executeQuery();
            if(result.next()) {
            	Long addressId = result.getLong("addressid");
            	return addressId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return null;
	}
	
	public static void deleteUser(long userId) {
		try (Connection conn = getConnection()){
			conn.setAutoCommit(false);
            PreparedStatement prepStmt = conn.prepareStatement(
                    "delete from lessonForms_teachers"
                     + " where teacherid=?;");
            prepStmt.setLong(1, userId);
            prepStmt.execute();
            
            prepStmt = conn.prepareStatement(
                    "delete from subjects_teachers"
                     + " where teacherid=?;");
            prepStmt.setLong(1, userId);
            prepStmt.execute();
            
            prepStmt = conn.prepareStatement(
                    "delete from teachers"
                     + " where userid=?;");
            prepStmt.setLong(1, userId);
            prepStmt.execute();
            
            prepStmt = conn.prepareStatement(
                    "delete from users"
                     + " where userid=?;");
            prepStmt.setLong(1, userId);
            prepStmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public static Long getUserIdByEmail(String email) {
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select userid from users where mail = ?;");
            prepStmt.setString(1, email);
            ResultSet result = prepStmt.executeQuery();
            if(result.next()) {
            	return result.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return null;
	}
	
	
	//TODO: Matcher
	private static List<Long> getTeacherIds(String teachingLevel, String subject, List<String> lessonForms){
		ArrayList<Long> list = new ArrayList<>();
		if(lessonForms.isEmpty()) {
			return list;
		}
		String tempText = "";
		for(int i=1; i<lessonForms.size(); i++) {
			tempText += " or lessonForm = ? ";
		}
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select distinct(userid) from teachers "
                    + " join subjects_teachers on subjects_teachers.teachersId = userId "
                    + " join lessonForms_teachers on lessonForms_teachers.teacherId = userId"
                    + "where teachingLevel = ? and subject = ? and (lessonForm = ?"
                    + tempText + ");");
            prepStmt.setString(1, teachingLevel);
            prepStmt.setString(2, subject);
            for(int i=0; i<lessonForms.size(); i++) {            	
            	prepStmt.setString(3+i, lessonForms.get(i));
            }
            ResultSet result = prepStmt.executeQuery();
            while(result.next()) {
            	list.add(result.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return list;
	}
	
	public static List<Teacher> getTeachers(String teachingLevel, String subject, List<String> lessonForms){
		List<Long> teacherIds = getTeacherIds(teachingLevel, subject, lessonForms);
		List<Teacher> teachers = new ArrayList<>();
		for(long id : teacherIds) {
			teachers.add(getTeacher(id));
		}
		return teachers;
	}
	
	public static void insertDistance(long addressId1, long addressId2, BigDecimal distance) {
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into distances(address1, address2, distance)"
                    + " values(?, ?, ?);");
            prepStmt.setLong(1, addressId1);
            prepStmt.setLong(2, addressId2);
            prepStmt.setBigDecimal(3, distance);
            prepStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public static BigDecimal getDistance(long addressId1, long addressId2) {
		if(addressId1 == addressId2) {
			return BigDecimal.ZERO;
		}
		try (Connection conn = getConnection()){
            PreparedStatement prepStmt = conn.prepareStatement(
                    "select distance from distances"
	                + " where (address1 = ? and address2 = ?) or (address1 = ? and address2 = ?);");
            prepStmt.setLong(1, addressId1);
            prepStmt.setLong(2, addressId2);
            prepStmt.setLong(3, addressId2);
            prepStmt.setLong(4, addressId1);
            ResultSet result = prepStmt.executeQuery();
            if(result.next()) {
            	BigDecimal distance = result.getBigDecimal("distance");
            	return distance;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return null;
	}
	
	
}
