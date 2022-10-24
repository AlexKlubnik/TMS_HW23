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
    public static final String GET_PAGE_VISITS_COUNTER = "select counter from page_visit_counter where id = 1";
    public static final String UPDATE_VISIT_COUNTER = "update page_visit_counter set counter = ? where id = 1";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String title = "Teach me skills students";
        String docType = "<!DOCTYPE html";

        Connection connection = (Connection) getServletContext().getAttribute("connectionToDb");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_STUDENTS);

        ) {

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


            int visitCounter = getVisitCounterFromDb(connection);
            writer.println("This page was visited " + visitCounter + " times." + "</br>");
            visitCounter++;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VISIT_COUNTER);
            preparedStatement.setInt(1, visitCounter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        writer.println("</body></html>");
    }

    private int getVisitCounterFromDb(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet1 = statement.executeQuery(GET_PAGE_VISITS_COUNTER);
        resultSet1.next();
        return resultSet1.getInt("counter");
    }
}
