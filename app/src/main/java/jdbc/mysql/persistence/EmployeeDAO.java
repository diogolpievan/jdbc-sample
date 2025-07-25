package jdbc.mysql.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import static java.time.ZoneOffset.UTC;
import java.util.ArrayList;
import java.util.List;

import jdbc.mysql.persistence.entity.EmployeeEntity;

public class EmployeeDAO {
    public static void insert(final EmployeeEntity entity) {

        String sql = "INSERT INTO employees (name, salary, birthday) values ( ?, ?, ?)";
        try (
                var connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getSalary());
            statement.setObject(3, entity.getBirthday());

            int affectedRows = statement.executeUpdate();
            System.out.printf("Affected %d rows%n", affectedRows);

            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    entity.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update() {

    }

    public void delete() {

    }

    public static List<EmployeeEntity> findAll() {
        List<EmployeeEntity> allEmployees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY name ASC";

        try (
                var connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {

            statement.executeQuery(sql);
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                EmployeeEntity employee = new EmployeeEntity();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSalary(resultSet.getBigDecimal("salary"));
                var birthdayInstant = resultSet.getTimestamp("birthday").toInstant();
                employee.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
                allEmployees.add(employee);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allEmployees;
    }

    public static EmployeeEntity findById(final long id) {

        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo: " + id);
        
        EmployeeEntity employee = new EmployeeEntity();
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (
                var connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSalary(resultSet.getBigDecimal("salary"));
                var birthdayInstant = resultSet.getTimestamp("birthday").toInstant();
                employee.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employee;
    }
}
