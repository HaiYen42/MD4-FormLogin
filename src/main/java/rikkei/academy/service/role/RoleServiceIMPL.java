package rikkei.academy.service.role;

import rikkei.academy.config.ConnectMySQL;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleServiceIMPL implements IRoleService {
    private Connection connection = ConnectMySQL.getConnection();
    private final String FIND_BY_NAME_ROLE = "SELECT * FROM role WHERE name=?;";

    @Override
    public Role findByName(RoleName name) {
        Role role = null;
        try {
            // Chuẩn bị các tập lệnh để thực thi--> để thực thi truy vấn trên database
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_ROLE);
            // Với câu lệnh có biến số thì set giá trị vào (1-> thứ tự dấu ?, name-> giá trị)
            preparedStatement.setString(1, String.valueOf(name));
            // Tiến hành thực thi truy vấn qua excuteQuery()--> Query là trả về các rows hợp lệ
            ResultSet resultSet = preparedStatement.executeQuery();
            // resultSet để đỡ lấy bộ kết quả qua câu truy vấn trên
            // resultSet.next--> để duệt từng row
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                role = new Role(id, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return role;
    }
}
