package Server.Utility;

import Server.ServerApp;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * A class for handle database.
 */
public class DatabaseHandler {

    private final HikariDataSource dataSource;
    private final Map<PreparedStatement, Connection> stmConnMap = new ConcurrentHashMap<>();
    private final ThreadLocal<Connection> txConn = new ThreadLocal<>();


    public DatabaseHandler(String url, String user, String pass) {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(url);
        cfg.setUsername(user);
        cfg.setPassword(pass);
        cfg.setMaximumPoolSize(16);
        cfg.setMinimumIdle(4);
        cfg.setIdleTimeout(30_000);
        cfg.setConnectionTimeout(10_000);
        dataSource = new HikariDataSource(cfg);
        ServerApp.logger.info("HikariCP pool started");
    }


    /**
     * @param sqlStatement SQL statement to be prepared.
     * @param generateKeys Is keys needed to be generated.
     * @return Prepared statement.
     */
    public PreparedStatement getPreparedStatement(String sqlStatement,
                                                  boolean generateKeys) {
        try {
            int flag = generateKeys
                    ? Statement.RETURN_GENERATED_KEYS
                    : Statement.NO_GENERATED_KEYS;
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStatement, flag);
            stmConnMap.put(ps, conn);
            return ps;

        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING,
                    "Can't create PreparedStatement", e);
            return null;
        }
    }


    public void closePreparedStatement(PreparedStatement ps) {
        if (ps == null) return;
        try { ps.close(); } catch (SQLException ignored) {}
        Connection conn = stmConnMap.remove(ps);
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
    }

    public void closePool() {
        dataSource.close();
        ServerApp.logger.info("HikariCP pool closed");
    }
    public void setCommitMode() {
        try {
            if (txConn.get() == null) {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                txConn.set(conn);
            }
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING,
                    "Error when enabling commit mode", e);
        }
    }

    public void setNormalMode() {
        Connection conn = txConn.get();
        if (conn == null) return;
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING,
                    "Error when re-enabling auto-commit", e);
        }
    }

    public void commit() {
        Connection conn = txConn.get();
        if (conn == null) return;
        try {
            conn.commit();
        } catch (SQLException e) {
            ServerApp.logger.log(Level.WARNING,
                    "Error when commit", e);
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
            ServerApp.logger.log(Level.WARNING,
                    "Error when rollback", e);
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
            ServerApp.logger.log(Level.WARNING,
                    "Error when set savepoint", e);
        }
    }

    private void closeSilently(Connection c) {
        try { c.close(); } catch (SQLException ignored) {}
    }
}