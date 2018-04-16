package com.gus.kzis.data;

import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriteToCSV {

    public static void main(String[] args) {

        try {
            String connectionURL = "jdbc:mysql://localhost:3306/professions?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionURL, "root", "root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from profession");
            String csv = "C:\\Baza ofert\\output2.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            List<String[]> data = new ArrayList<>();
            while (rs.next()) {
                data.add(new String[]
                        {rs.getString("code"),
                                rs.getString("name"),
                                rs.getString("synthesis"),
                                rs.getString("tasks"),
                rs.getString("additional_tasks")});
            }

            writer.writeAll(data);
            System.out.println("CSV written successfully.");
            writer.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

