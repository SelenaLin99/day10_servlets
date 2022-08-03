package day10_servlets_hw;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

@WebServlet("/registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");

		// store the new user creds to DB

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// INSERT INTO day8hw.studentlogin VALUES (?,?)
			String sql = "INSERT INTO day8hw.studentlogin VALUES (?,?)";

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/day8hw", "root", "root");

//			stmt = con.createStatement();
//			stmt.executeUpdate("INSERT INTO day8hw.studentlogin VALUES ('selena.lin', '12345')");

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			pstmt.addBatch();
			pstmt.executeBatch();

			pstmt.close();
			con.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");

		response.sendRedirect("./html/registrationSuccess.html");

	}

}
