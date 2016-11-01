package org.barlom.persistence.postgresql;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Experimentation
 */
public class Example {


    public static boolean connect() {
        try {
            Class.forName( "org.postgresql.Driver" );

            String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=barlom-gdb-metamodel";
            Properties props = new Properties();
            props.setProperty( "user", "postgres" );
            props.setProperty( "password", "letmeinplease!" );

            Connection conn = DriverManager.getConnection( url, props );

            //String sql = "SELECT * FROM VertexType";
            String sql = "SELECT * FROM FindVertexTypesAll()";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery( sql );
            System.out.println( "ID, Name" );
            while ( rs.next() ) {
                System.out.print( rs.getLong( 1 ) );
                System.out.print( ", " );
                System.out.println( rs.getString( 2 ) );
            }
            rs.close();
            st.close();

            conn.close();

            return true;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean canConnectWithPool() {

        try {
            PGPoolingDataSource source = new PGPoolingDataSource();
            source.setDataSourceName( "A Data Source" );
            source.setServerName( "localhost" );
            source.setPortNumber( 5432 );
            source.setDatabaseName( "postgres" );
            source.setCurrentSchema( "barlom-gdb-metamodel" );
            source.setUser( "postgres" );
            source.setPassword( "letmeinplease!" );
            source.setMaxConnections( 10 );

            Connection conn = source.getConnection();

            //String sql = "SELECT * FROM VertexType";
            String sql = "SELECT * FROM FindVertexTypesAll()";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery( sql );
            System.out.println( "ID, Name" );
            while ( rs.next() ) {
                System.out.print( rs.getLong( 1 ) );
                System.out.print( ", " );
                System.out.println( rs.getString( 2 ) );
            }
            rs.close();
            st.close();

            conn.close();

            return true;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return false;


    }

}
