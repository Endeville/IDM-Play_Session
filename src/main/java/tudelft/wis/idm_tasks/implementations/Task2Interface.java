package tudelft.wis.idm_tasks.implementations;

import tudelft.wis.idm_tasks.basicJDBC.interfaces.JDBCTask2Interface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Task2Interface implements JDBCTask2Interface {

    public Task2Interface(Connection connection) {
        this.connection = connection;
    }

    Connection connection;
    @Override
    public Connection getConnection(){
        return this.connection;
    }

    @Override
    public Collection<String> getTitlesPerYear(int year) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                "select primary_title from titles where start_year=? "
            );
            statement.setInt(1,year);
            ResultSet rs = statement.executeQuery();
            List<String> list = new ArrayList<>();
            while(rs.next()){
                list.add(rs.getString("primary_title"));
            }
            return list;
        }catch (SQLException e){
            return new ArrayList<>();
        }
    }

    @Override
    public Collection<String> getJobCategoriesFromTitles(String searchString) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    """
                            select distinct ci.job_category from cast_info as ci
                            left join titles as t on t.title_id=ci.title_id
                            where t.primary_title like ?"""
            );
            statement.setString(1,"%" + searchString + "%");
            ResultSet rs = statement.executeQuery();
            List<String> list = new ArrayList<>();
            while(rs.next()){
                list.add(rs.getString("job_category"));
            }
            return list;
        }catch (SQLException e){
            return new ArrayList<>();
        }
    }

    @Override
    public double getAverageRuntimeOfGenre(String genre) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    """
                            select avg(t.runtime) as avg_runtime from titles_genres tg
                            left join titles t on tg.title_id=t.title_id
                            where tg.genre=?"""
            );
            statement.setString(1,genre);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getDouble("avg_runtime");
        }catch (SQLException e){
            return 0;
        }
    }

    @Override
    public Collection<String> getPlayedCharacters(String actorFullname) {
        return null;
    }
}
