import java.sql.*;


//RENDIPUNKTIDE JA AUTODE SELEKTEERIMISEKS ANDMEBAAS
public class Rendipunktid  {

    public static void main(String args[]) {

        String url = "jdbc:sqlite:test.db";
        Connection con;
        String query = "select * from RENDIAUTOD";
        Statement stmt;

        try {
            Class.forName("myDriver.ClassName");

        } catch(java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            con = DriverManager.getConnection(url,
                    "", "");

            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            PrintColumnTypes.printColTypes(rsmd);
            System.out.println("");

            int numberOfColumns = rsmd.getColumnCount();

            for (int i = 1; i <= numberOfColumns; i++) {
                if (i > 1) System.out.print(",  ");
                String columnName = rsmd.getColumnName(i);
                System.out.print(columnName);
            }
            System.out.println("");

            while (rs.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

            stmt.close();
            con.close();
        } catch(SQLException ex) {
            System.err.print("SQLException: ");
            System.err.println(ex.getMessage());
        }
    }
}

class PrintColumnTypes  {

    public static void printColTypes(ResultSetMetaData rsmd)
            throws SQLException {
        int columns = rsmd.getColumnCount();
        for (int i = 1; i <= columns; i++) {
            int jdbcType = rsmd.getColumnType(i);
            String name = rsmd.getColumnTypeName(i);
            System.out.print("Column " + i + " is JDBC type " + jdbcType);
            System.out.println(", which the DBMS calls " + name);
        }
    }
}