package pl.coderslab.dao;

import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {

    private static final String FIND_ALL_DAY_NAMES = "SELECT * FROM day_name";

    public List<DayName> findAll() {
        List<DayName> DayNameList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAY_NAMES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DayName dayName = new DayName();
                dayName.setName(resultSet.getString("name"));
                dayName.setDisplayOrder(resultSet.getInt("displayOrder"));
                DayNameList.add(dayName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DayNameList;

    }
}