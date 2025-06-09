package Server.Utility;

import Server.ServerApp;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * A class for handle database without a connection pool.
 */
public class DatabaseHandler {

    private final String url;
    private final String user;
    private final String pass;

    private final Map<PreparedStatement, Connection> stmConnMap = new ConcurrentHashMap<>();
    private final ThreadLocal<Connection> txConn = new ThreadLocal<>();

    public DatabaseHandler(String url, String user, String pass) {
        this.url  = url;
        this.user = user;
        this.pass = pass;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            ServerApp.logger.log(Level.SEVERE, "JDBC driver not found", e);
        }
    }

    /**
     * @param sqlStatement  SQL to prepare
     * @param generateKeys  true ↔ Statement.RETURN_GENERATED_KEYS
     */
    public PreparedStatement getPreparedStatement(String sqlStatement, boolean generateKeys) {
        try {
            int flag = generateKeys
                    ? Statement.RETURN_GENERATED_KEYS
                    : Statement.NO_GENERATED_KEYS;

            Connection conn = txConn.get();
            if (conn == null) {
                conn = DriverManager.getConnection(url, user, pass);
            }

            PreparedStatement ps = conn.prepareStatement(sqlStatement, flag);
            stmConnMap.put(ps, conn);
            return ps;

        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Can't create PreparedStatement", e);
            return null;
        }
    }

    public void closePreparedStatement(PreparedStatement ps) {
        if (ps == null) return;
        Connection conn = stmConnMap.remove(ps);
        try { ps.close(); } catch (SQLException ignored) {}
        if (conn != null && conn != txConn.get()) closeSilently(conn);
    }


    public void setCommitMode() {
        try {
            if (txConn.get() == null) {
                Connection conn = DriverManager.getConnection(url, user, pass);
                conn.setAutoCommit(false);
                txConn.set(conn);
            }
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Error when enabling commit mode", e);
        }
    }

    public void setNormalMode() {
        Connection conn = txConn.get();
        if (conn == null) return;
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Error when re-enabling auto-commit", e);
        }
    }

    public void commit() {
        Connection conn = txConn.get();
        if (conn == null) return;
        try {
            conn.commit();
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Error when commit", e);
        } finally {
            closeSilently(conn);
            txConn.remove();
        }
    }

    public void rollback() {
        Connection conn = txConn.get();
        if (conn == null) return;
        try {
            conn.rollback();
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Error when rollback", e);
        } finally {
            closeSilently(conn);
            txConn.remove();
        }
    }

    public void setSavepoint() {
        Connection conn = txConn.get();
        if (conn == null) return;
        try {
            conn.setSavepoint();
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING, "Error when set savepoint", e);
        }
    }


    private void closeSilently(Connection c) {
        try { c.close(); } catch (SQLException ignored) {}
    }
}
