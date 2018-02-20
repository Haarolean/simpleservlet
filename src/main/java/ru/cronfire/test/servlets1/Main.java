package ru.cronfire.test.servlets1;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ru.cronfire.test.servlets1.implementations.Part;
import ru.cronfire.test.servlets1.util.Util;
import ru.cronfire.test.servlets1.util.database.PostgreSQL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends HttpServlet {

    private static Logger logger = Logger.getLogger(Main.class);
    private PostgreSQL db;

    public static Logger getLogger() {
        return Main.logger;
    }

    @Override
    public void init() throws ServletException { // No spring? Feels bad
        String log4jLocation = getInitParameter("log4j-properties-location");
        ServletContext sc = getServletContext();

        String cfg = sc.getRealPath("/") + log4jLocation;
        if (Files.exists(Paths.get(cfg))) {
            System.out.println("Initializing log4j with: " + cfg);
            PropertyConfigurator.configure(cfg);
        } else {
            System.err.println(cfg + " file not found, initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        }

        db = new PostgreSQL(getInitParameter("dbHostname"),
                Integer.valueOf(getInitParameter("dbPort")),
                getInitParameter("dbDatabase"),
                getInitParameter("dbUser"),
                getInitParameter("dbPassword"));

        super.init();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Part> parts = new ArrayList<>();

        // filtering via sql, sorting via js
        String query = "SELECT name, number, vendor, qty, " +
                "TO_CHAR(shipped, 'Mon dd, yyyy') AS shipped, " +
                "TO_CHAR(received, 'Mon dd, yyyy') AS received FROM parts WHERE 1=1"; // no ORM? Okay.
        String number = req.getParameter("number");
        if (Util.isNotEmpty(number)) query += " AND UPPER(number) LIKE UPPER(?)";
        String name = req.getParameter("name");
        if (Util.isNotEmpty(name)) query += " AND UPPER(name) LIKE UPPER(?)";
        String vendor = req.getParameter("vendor");
        if (Util.isNotEmpty(vendor)) query += " AND UPPER(vendor) LIKE UPPER(?)";
        String qty = req.getParameter("qty");
        if (Util.isNotEmpty(qty)) query += " AND qty >= ?";

        String shippedAfter = req.getParameter("shipped_after");
        if (Util.isNotEmpty(shippedAfter) && Util.isValidDate(shippedAfter)) query += " AND shipped >= ?::date";
        String shippedBefore = req.getParameter("shipped_before");
        if (Util.isNotEmpty(shippedBefore) && Util.isValidDate(shippedBefore)) query += " AND shipped <= ?::date";

        String receivedAfter = req.getParameter("received_after");
        if (Util.isNotEmpty(receivedAfter) && Util.isValidDate(receivedAfter)) query += " AND received >= ?::date";
        String receivedBefore = req.getParameter("received_before");
        if (Util.isNotEmpty(receivedBefore) && Util.isValidDate(receivedBefore)) query += " AND received <= ?::date";

        PreparedStatement ps = null;
        try {
            ps = db.getConnection().prepareStatement(query);
            int i = 0;

            if (Util.isNotEmpty(number)) ps.setString(++i, "%" + number + "%");
            if (Util.isNotEmpty(name)) ps.setString(++i, "%" + name + "%");
            if (Util.isNotEmpty(vendor)) ps.setString(++i, "%" + vendor + "%");
            if (Util.isNotEmpty(qty) && Util.isValidInt(qty)) ps.setInt(++i, Integer.parseInt(qty));

            if (Util.isNotEmpty(shippedAfter) && Util.isValidDate(shippedAfter)) ps.setString(++i, shippedAfter);
            if (Util.isNotEmpty(shippedBefore) && Util.isValidDate(shippedBefore)) ps.setString(++i, shippedBefore);
            if (Util.isNotEmpty(receivedAfter) && Util.isValidDate(receivedAfter)) ps.setString(++i, receivedAfter);
            if (Util.isNotEmpty(receivedBefore) && Util.isValidDate(receivedBefore)) ps.setString(++i, receivedBefore);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                parts.add(new Part(rs.getString("number"),
                        rs.getString("name"),
                        rs.getString("vendor"),
                        rs.getInt("qty"),
                        rs.getString("shipped"),
                        rs.getString("received")));
            }

            ps.close(); // ResultSet is being closed as well
        } catch (Exception e) {
            Main.getLogger().log(Level.ERROR, "AAAH EXCEPTION!", e);
            Main.getLogger().info("Query: " + ps.toString());
        }

//        req.setAttribute("GET", req.);
        req.setAttribute("parts", parts);
        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }

    public void destroy() {
        try {
            db.closeConnection();
            db = null;
        } catch (SQLException e) {
            Main.getLogger().log(Level.ERROR, "AAAH EXCEPTION!", e);
        }
    }
}
