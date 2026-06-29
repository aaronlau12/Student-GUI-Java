/**
* Class: CIST 2372 Java Programming II
* Term: Spring 2026
* Instructor: Jianmin Wang
* Description: Solution to Lab 9 DB & Business Objects
* Due: 03/25/2026
* @author Aaron Lau
* @version 1.0
*
* By turning in this code, I Pledge:
* 1. That I have completed the programming assignment independently.
* 2. I have not copied the code from a student or any source.
* 3. I have not given my code to any student.
* 4. I have not used any AI tools (e.g., ChatGPT) to generate this code
* 
* * References:
* Liang, Y. Daniel. Introduction to Java Programming, 10th ed., Pearson, 2013.
* Used for Java syntax and JavaFX examples.
*/
package lab.pkg10;
import java.sql.*;

public class Student {
    //properties
    private int sid;
    private String firstName;
    private String lastName;
    private String email;
    private double gpa;
    
    public Connection getConnection() {
        Connection conn = null;
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conn = 
                    DriverManager.getConnection("jdbc:ucanaccess://RegistrationDB.mdb");
                
            System.out.println("Driver Loaded");
        }
        catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        
        return conn;
    }
    
    //constructors
    public Student() {
        sid = 0;
        firstName = "";
        lastName = "";
        email = "";
        gpa = 0.0;
    }//end constructor
    
    public Student(int sid,
            String firstName,
            String lastName,
            String email,
            double gpa) {
        
            this.sid = sid;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.gpa = gpa;
    }//end constructors
    
    //set and get methods
    public void setSid(int sid) {
        this.sid = sid;
    }
    public int getSid() {
        return sid;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    public double getGpa() {
        return gpa;
    }
    
    public void selectDB(int sid) {
        try {
            Connection conn = getConnection();
			
            String select = "SELECT * FROM Students WHERE ID = ?";

            //create statement
            PreparedStatement prepared = conn.prepareStatement(select);

            prepared.setInt(1, sid);
			//System.out.println("Query prepared");
            
            ResultSet rs = prepared.executeQuery();
            System.out.println("Query executed.");
            System.out.println();
			
            if (rs.next()) {
                System.out.println("Student Found");
                this.sid = rs.getInt("ID");
                this.firstName = rs.getString("FirstName");
                this.lastName = rs.getString("LastName");
                this.email = rs.getString("EMail");
                this.gpa = rs.getDouble("GPA");
            }
            else {
		System.out.println("Student not found.");
            }

            rs.close();
            prepared.close();
            conn.close();
        }
        
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
	
	public void insertDB(int sid,
                            String firstName,
                            String lastName,
                            String email,
                            double gpa) {
            try {
                //load driver
                Connection conn = getConnection();
			
                String insert = "INSERT INTO Students (ID, FirstName, LastName, EMail, GPA)" +
                                    "VALUES (?, ?, ?, ?, ?)";
							
                PreparedStatement prepared = conn.prepareStatement(insert);
			
                prepared.setInt(1, sid);
                prepared.setString(2, firstName);
                prepared.setString(3, lastName);
                prepared.setString(4, email);
                prepared.setDouble(5, gpa);
			
                int rows = prepared.executeUpdate();
			
                if (rows > 0) {
                    this.sid = sid;
                    this.firstName = firstName;
                    this.lastName = lastName;
                    this.email = email;
                    this.gpa = gpa;
                    System.out.println("Insert executed.");
                }
			
                    System.out.println();
                    prepared.close();
                    conn.close();
		}
		
		catch (SQLException ex) {
                    ex.printStackTrace();
		}
	}//end insert
	
	public void updateDB() {
            try {
                Connection conn = getConnection();
				
                String update = "UPDATE Students " +
				"SET FirstName = ?, LastName = ?, EMail = ?, GPA = ? " +
				"WHERE ID = ?";
							
		PreparedStatement prepared = conn.prepareStatement(update);
			
		prepared.setString(1, this.firstName);
		prepared.setString(2, this.lastName);
		prepared.setString(3, this.email);
		prepared.setDouble(4, this.gpa);
		prepared.setInt(5, this.sid);
			
		int rows = prepared.executeUpdate();
			
		if (rows > 0)
                    System.out.println("Update executed.");
                else 
                    System.out.println("Update failed.");
			
                    prepared.close();
                    conn.close();
		}
		
		catch (SQLException ex) {
                    ex.printStackTrace();
		}		
	}//end 
	
	public void deleteDB() {
            try {
		Connection conn = getConnection();
				
		String delete = "DELETE FROM Students WHERE ID = ?";
			
		PreparedStatement prepared = conn.prepareStatement(delete);
			
		prepared.setInt(1, this.sid);
			
		int rows = prepared.executeUpdate();
                    if (rows > 0)
			System.out.println("Delete executed.");
                    else
			System.out.println("Delete failed.");
			
		prepared.close();
		conn.close();
		}//end try
		
		catch(SQLException ex) {
			ex.printStackTrace();
		}//end catch
	}
	
    public void display() {
        System.out.println("Student ID:     " + getSid());
        System.out.println("First Name:     " + getFirstName());
        System.out.println("Last Name:      " + getLastName());
        System.out.println("Email:          " + getEmail());
        System.out.println("GPA:            " + getGpa());
    }

}