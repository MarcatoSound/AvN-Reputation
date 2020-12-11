package net.playavalon.avnrep.data;

import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.player.PlayerReputation;
import net.playavalon.avnrep.data.player.PlayerReputationManager;
import net.playavalon.avnrep.data.reputation.Reputation;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.Properties;

import static net.playavalon.avnrep.AvNRep.debugPrefix;
import static net.playavalon.avnrep.AvNRep.plugin;

public class OnlineSQLDatabase {

    private static Connection dbConnection;
    private static String dbHost, dbDatabase, dbUserName, dbPassword;
    private static int dbPort;
    Statement statement;


	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	[ CLASS CONSTRUCTOR ]
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public OnlineSQLDatabase() {

        // Database Stuff
        //MySQLKeepAlive keepAlive = new MySQLKeepAlive();
        //keepAlive.startCheck();

        FileConfiguration config = plugin.getConfig();

        dbHost = config.getString("Database.Host");
        dbPort = config.getInt("Database.Port");
        dbDatabase = config.getString("Database.Database");
        dbUserName = config.getString("Database.Username");
        dbPassword = config.getString("Database.Password");

        //System.out.println(Main.DEBUG_PREFIX + " Database support enabled!");

        // Attempt the connection to the database
        try {
            System.out.println(debugPrefix + "# Connecting to database...");
            openConnection();
            statement = dbConnection.createStatement();

            DatabaseMetaData dbm = dbConnection.getMetaData();

            ResultSet tables = dbm.getTables(null, null, "AvN_PlayerRep", null);
            if (!tables.next()) {
                System.out.println(debugPrefix + "## Player reputation table is missing! Creating it...");
                createPlayerRepTable();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        plugin.dbEnabled = true;

    }

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	[ DATABASE SETUP ]
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private void createPlayerRepTable() throws SQLException {
        try {
            openConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        openStatement();

        // TODO Rewrite this statement for correct table configuration
        String typesEnumSQL = "--\n" +
                "-- Table structure for table `AvN_ItemTypes`\n" +
                "--\n" +
                "CREATE TABLE IF NOT EXISTS `AvN_ItemTypes` (\n" +
                "  `type` varchar(256) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
                "--\n" +
                "INSERT INTO `AvN_ItemTypes` (`type`) VALUES \n" +
                "  ('ARMOR'),\n" +
                "  ('MISC'),\n" +
                "  ('TOOL'),\n" +
                "  ('WEAPON');\n" +
                "--\n" +
                "-- Indexes for table `AvN_ItemTypes` \n" +
                "--\n" +
                "ALTER TABLE `AvN_ItemTypes`\n" +
                "  ADD PRIMARY KEY (`type`);";

        statement.execute(typesEnumSQL);

        statement.close();

    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	[ OTHER METHODS ]
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private void openConnection() throws SQLException, ClassNotFoundException {

        // If SQL Database Connection failed, Do Nothing more
        if (dbConnection != null && !dbConnection.isClosed() && dbConnection.isValid(2)) {
            return;
        }

        Class.forName("com.mysql.jdbc.Driver");

        Properties info = new Properties();
        info.setProperty("user", dbUserName);
        info.setProperty("password", dbPassword);
        info.setProperty("autoReconnect", "true");
        info.setProperty("allowMultiQueries", "true");

        dbConnection = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbDatabase, info);

        System.out.println(debugPrefix + "# Connected to database!");

    }

    public void openStatement() throws SQLException {

        if (statement == null || statement.isClosed()) {

            System.out.println(debugPrefix + "Statement closed! Reconnecting to database...");

            this.statement = dbConnection.createStatement();

        }

    }


    public void loadPlayerData(AvalonPlayer ap) throws SQLException {
        try {
            openConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        openStatement();

        String SELECT = "SELECT * FROM " + dbDatabase + ".AvN_PlayerRep ";
        String WHERE = "WHERE uuid = '" + ap.getPlayer().getUniqueId() + "'";

        ResultSet result = statement.executeQuery(SELECT + WHERE);

        PlayerReputationManager manager = ap.getPlayerReputationManager();

        while (result.next()) {
            String namespace = result.getString("reputation");
            int level = result.getInt("level");
            double value = result.getDouble("value");

            PlayerReputation rep = manager.getReputation(namespace.toUpperCase());
            rep.setRepLevel(level);
            rep.setRepValue(value);
        }

    }

    public void savePlayerData(AvalonPlayer ap) throws SQLException {

    }

}
