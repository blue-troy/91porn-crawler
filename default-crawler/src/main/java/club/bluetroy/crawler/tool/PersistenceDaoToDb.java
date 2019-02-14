package club.bluetroy.crawler.tool;

import club.bluetroy.crawler.dao.BaseDao;
import club.bluetroy.crawler.dao.MovieStatus;
import club.bluetroy.crawler.domain.Movie;
import lombok.experimental.UtilityClass;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-02-13
 * Time: 21:01
 */
@UtilityClass
public class PersistenceDaoToDb {
    public static void toDb(BaseDao dao) {
        System.out.println("to db");
        System.out.println("count = " + dao.countScannedMovies());
        insertMovies(dao.listMovies(MovieStatus.SCANNED_MOVIES), "scanned");
        insertMovies(dao.listMovies(MovieStatus.FILTERED_MOVIES), "filtered");
        insertMovies(dao.listMovies(MovieStatus.TO_DOWNLOAD_MOVIES), "to_download");
        insertMovies(dao.listMovies(MovieStatus.DOWNLOADED_MOVIES), "downloaded");
        insertMovies(dao.listMovies(MovieStatus.DOWNLOAD_ERROR), "download_error");
    }

    private static void insertMovies(ConcurrentHashMap<String, Movie> movieConcurrentHashMap, String status) {
        for (Movie value : movieConcurrentHashMap.values()) {
            insertMovie(value, status);
        }
    }

    private static void insertMovie(Movie movie, String status) {
        try {
            PreparedStatement insertStatement = SqlUtils.getInsertStatement();
            insertStatement.setString(1, movie.getTitle());
            insertStatement.setString(2, movie.getLength());
            insertStatement.setString(3, movie.getAddTime());
            insertStatement.setString(4, movie.getAuthor());
            insertStatement.setInt(5, movie.getView());
            insertStatement.setInt(6, movie.getCollect());
            insertStatement.setInt(7, movie.getMessageNumber());
            insertStatement.setInt(8, movie.getIntegration());
            insertStatement.setString(9, movie.getDetailUrl());
            insertStatement.setString(10, movie.getDownloadUrl());
            insertStatement.setString(11, movie.getFileName());
            insertStatement.setString(12, movie.getKey());
            insertStatement.setLong(13, movie.getAddDateTime().toEpochSecond(ZoneOffset.UTC));
            insertStatement.setString(14, status);
            boolean execute = insertStatement.execute();
            System.out.println(execute);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


class SqlUtils {
    private static Connection connection = null;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:crawler91.db");
            System.out.println("init db connetion");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        createDb();
    }

    private static void createDb() {
        try {
            Statement statement = connection.createStatement();
            String schema =
                    "CREATE VIRTUAL TABLE \"movies\" USING FTS4 (\n" +
                    "  \"title\" text NOT NULL,\n" +
                    "  \"length\" text NOT NULL,\n" +
                    "  \"add_time\" text NOT NULL,\n" +
                    "  \"author\" text NOT NULL,\n" +
                    "  \"view\" integer NOT NULL,\n" +
                    "  \"collect\" integer NOT NULL,\n" +
                    "  \"message_number\" integer NOT NULL,\n" +
                    "  \"integration\" integer NOT NULL,\n" +
                    "  \"detail_url\" text NOT NULL,\n" +
                    "  \"download_url\" text,\n" +
                    "  \"file_name\" text NOT NULL,\n" +
                    "  \"key\" text NOT NULL,\n" +
                    "  \"add_date_time\" integer NOT NULL,\n" +
                    "  \"status\" text NOT NULL\n" +
                    ");";
            statement.execute(schema);
            System.out.println("init db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static PreparedStatement getInsertStatement() throws SQLException {

        return getConnection().prepareStatement("REPLACE into movies (title,length,add_time,author,view,collect,message_number,integration,detail_url,download_url,file_name,key,add_date_time,status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

    }

    private static Connection getConnection() {

        return connection;
    }
}
