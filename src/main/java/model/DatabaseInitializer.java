package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.ibatis.jdbc.ScriptRunner;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (getColList().isEmpty()) {
            initDatabaseSchema();
            initData();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private List<String> getColList() {
        List<String> result = new ArrayList<>();
        try (
                Connection connection = DataSourceFactory.getDataSource().getConnection();
                ResultSet res = connection.getMetaData().getTables(connection.getCatalog(), "APP", null, null)) {

            while (res.next()) {
                result.add(res.getString("TABLE_NAME"));
            }

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

        return result;
    }
    
    private void initDatabaseSchema() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Init db schema");
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();) {
            ScriptRunner runner = new ScriptRunner(connection);
            runner.setLogWriter(null);
            Reader schema = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("comptoirs_schema_derby.sql")));
            runner.runScript(schema);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Init db schema OK");
    }
    
    private void initData() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Init data");
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();) {
            ScriptRunner runner = new ScriptRunner(connection);
            runner.setLogWriter(null);
            Reader data = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("comptoirs_data.sql")));
            runner.runScript(data);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Init data ok");
    }
    
    
    
}
