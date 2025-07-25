package jdbc.mysql.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import jdbc.mysql.persistence.entity.EmployeeEntity;

public class EmployeeDAO {
    public static void insert(final EmployeeEntity entity){

        String sql = "INSERT INTO employees (name, salary, birthday) values ( ?, ?, ?)";
        try(
            var connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getSalary());
            statement.setObject(3, entity.getBirthday());

            int affectedRows = statement.executeUpdate();
            System.out.printf("Affected %d rows%n", affectedRows);
            
            try(var generatedKeys = statement.getGeneratedKeys()){
                if(generatedKeys.next()) entity.setId(generatedKeys.getLong(1));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update() {

    }

    public void delete() {

    }

    public List<EmployeeEntity> findAll() {
        return null;
    }

    public EmployeeEntity findById() {
        return null;
    }

}
