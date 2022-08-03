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

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	
//	1) Create login page with DB checking
//	 Create user user table
//	   insert some records
//
//	   Create a login page, take user name and pwd, submit
//	   Loginservlet, 
//	   check credentilas
//
//
//	2) REgistration for a cource
//
//	3) Get all registred users

	public void init() {
		System.out.println("from login init()");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userName = request.getParameter("username");
		String password = request.getParameter("password");

		PrintWriter out = response.getWriter();

		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String sql = "select * from day8hw.studentlogin where username = ? and password = ?";
			String sql2 = "select * from day8hw.studentlogin";
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/day8hw", "root", "root");

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				out.println("Welcome " + userName);
				// get all registered users
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql2);
				out.println("All registered users");
				out.println();
				while (rs.next()) {
					out.println(rs.getString("username"));
				}

			} else {
				out.println("Wrong login info. Please try again.");
			}
			pstmt.close();
			con.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");

		boolean loginSuccess = true;

		if (userName.isEmpty()) {
			loginSuccess = false;
		}

		if (loginSuccess) {
			RequestDispatcher rd = request.getRequestDispatcher("./home");
			rd.include(request, response);
		}

		// doGet(request, response);
	}

}
