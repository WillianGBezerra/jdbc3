package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES "
					+ "(?, ?, ?, ? , ?)",
					/*Retorna a chave craida no banco */
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, "Carl Purple");
			st.setString(2, "Carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
			st.setDouble(4, 3000.00);
			st.setInt(5, 4);
			
			st2 = conn.prepareStatement(
					"insert into department (Name) values ('D1'), ('D2')",
					Statement.RETURN_GENERATED_KEYS);
			int rowsAffecteddepartment = st2.executeUpdate();
			if (rowsAffecteddepartment > 0) {
				ResultSet rs = st2.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id department= " + id);
				}
			}
			else {
				System.out.println("No row affected department!");
			}
			
			/*Adiciona os dados no banco e retorna a quantidade de linhas afetada pela inserção.*/
			int rowsAffected = st.executeUpdate();
			
			/*Se a variável rowsAffected for maior que 0,00 retornará o id criados no banco.*/
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			}
			else {
				System.out.println("No row affected!");
			}
			
		//System.out.println("Done! Rows affected: " + rowsAffected);
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		
		
	//System.out.println("Done! Rows affected: " + rowsAffected);
		
	

        finally {
        	DB.closeStatement(st);
        	DB.closeConnection();
        }

	}
}
