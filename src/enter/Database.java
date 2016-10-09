/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anand
 */
public class Database {
    public static final String DATABASE_NAME="example";
    public static final String STUDENT_TABLE="student"; //to store messages not sent
    
    Connection con;
    public Database() throws ClassNotFoundException, SQLException{
        
    }
    public void resetQuery() throws SQLException, ClassNotFoundException{
        Statement stmt;
        String url = "jdbc:mysql://localhost:3306";
//        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, "root", "123"); 
        stmt = con.createStatement();
        String sql="create database "+DATABASE_NAME;
        stmt.execute(sql);
        stmt.execute("USE "+DATABASE_NAME);
        sql="Create TABLE "+STUDENT_TABLE+" ( ID VARCHAR(20) PRIMARY KEY, Name varchar(15), Class varchar(5), Address VARCHAR(100));";
        stmt.execute(sql);
        
        stmt.close();
        con.close();
    }
    
    public Statement getConnectionStatement() throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        
        String url = "jdbc:mysql://localhost:3306/"+DATABASE_NAME;
//        Class.forName("com.mysql.jdbc.Driver");
        boolean connected=true;
        try{
        con = DriverManager.getConnection(url, "root", ""); 
        stmt = con.createStatement();
        
        }
        catch(Exception e){
            connected = false;
        }
        if(!connected){
            resetQuery();
            return getConnectionStatement();
        }
        return stmt;
    }
    
    public String getAllData() throws SQLException, ClassNotFoundException{
        StringBuilder result=new StringBuilder();
        Statement stmt=getConnectionStatement();
        String sql="Select * from "+STUDENT_TABLE+";";
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
            result.append(rs.getString(1)).append(" ").append(rs.getString(2)).append(" ").
                    append(rs.getString(3)).append(" ").append(rs.getString(4)).append("\n");
            
        }
        stmt.close();
        con.close();
        return result.toString();
    }
    
    public List<String> getParticularData(String id) throws SQLException, ClassNotFoundException{
        List<String> result=new ArrayList<String>();
        Statement stmt=getConnectionStatement();
        String sql="Select * from "+STUDENT_TABLE+ "where ID = '"+id+"'";
        ResultSet rs=(ResultSet) stmt.executeQuery(id);
        result.add(rs.getString(1));
        result.add(rs.getString(2));
        result.add(rs.getString(3));
        stmt.close();
        con.close();
        return result;
    }
    
    public void enterData(String id,String name,String cls,String address) throws SQLException, ClassNotFoundException{
        Statement stmt=getConnectionStatement();
        String sql="insert into "+STUDENT_TABLE+" values('"+id+"','"+name+"','"+cls+"','"+address+"');";
        System.out.println(stmt.executeUpdate(sql));
        stmt.close();
        con.close();
    }
   
    public void updateData(String id,String name,String cls,String address) throws SQLException, ClassNotFoundException{
        Statement stmt=getConnectionStatement();
        String sql="update  "+STUDENT_TABLE+" set name = '"+name+"',class='"+cls+"',address='"+address+"' where id='"+id+"'";
        System.out.println(stmt.executeUpdate(sql));
        stmt.close();
        con.close();
    }
    
    public void deleteData(String id) throws SQLException, ClassNotFoundException{
        Statement stmt=getConnectionStatement();
        String sql="Delete from  "+STUDENT_TABLE+"  where id='"+id+"'";
        System.out.println(stmt.executeUpdate(sql));
        stmt.close();
        con.close();
    }
//    public ArrayList<>
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Database d=new Database();
        d.resetQuery();
//        d.logFailedMessage("1234", 45);
//        System.out.println(d.getNotSentMessages());
    }
    
}
