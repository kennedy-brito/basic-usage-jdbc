package application;

import db.DB;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class Program {
    public static void main(String[] args) {

        Connection conn = null;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PreparedStatement st = null;

        try{
            conn = DB.getConnection();

            st = conn.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, "Carl Purple");
            st.setString(2, "carl@gmail.com");
            st.setDate(3, Date.valueOf(LocalDate.parse("22/04/1985", fmt)));
            st.setDouble(4, 3000.0);
            st.setInt(5, 4);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                while(rs.next()){
                    int id = rs.getInt(1);
                    System.out.println("Done! Id = " + id);
                }
            }
            else {
                System.out.println("No row affected!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {

            DB.closeStatement(st);
            DB.closeConnection();
        }
    }

    public static void select(){
        Connection conn = null;

        Statement st = null;

        ResultSet rs = null;

        try{
            conn = DB.getConnection();

            st = conn.createStatement();

            rs = st.executeQuery("SELECT * FROM department");

            while(rs.next()){

                System.out.println(
                        rs.getInt("Id") + ", " + rs.getString("Name"));

            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {

            DB.closeStatement(st);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
    }
}
