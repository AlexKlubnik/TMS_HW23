package com.klubnikov;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/jdbc")
public class JDBCServlet extends HttpServlet {

    public static final String SELECT_ALL_STUDENTS = "select * from tms_students";
    public static final String GET_PAGE_VISITS_COUNTER = "select * from page_visit_counter";
    public static final String UPDATE_VISIT_COUNTER = "update page_visit_counter set counter = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String title = "Teach me skills students";
        String docType = "<!DOCTYPE html";

        Connection connection = (Connection) getServletContext().getAttribute("connectionToDb");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_STUDENTS);
             ResultSet resultSet1 = statement.executeQuery(GET_PAGE_VISITS_COUNTER)) {

            writer.println(docType + "<html><head><title>" + title + "</title></head><body>");
            writer.println("<h2>Tms students</h2>");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                int age = resultSet.getInt("age");

                writer.println("ID: " + id + "</br>");
                writer.println("First name: " + firstName + "</br>");
                writer.println("Age: " + age + "</br>");
            }

            int visitCounter = resultSet1.getInt("counter");
            writer.println("This page was visited " + visitCounter + " times.");
            visitCounter++;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VISIT_COUNTER);
            preparedStatement.setInt(1, visitCounter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        writer.println("</body></html>");
    }


}
