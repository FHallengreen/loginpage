package com.example.cookielogin.repository;

import com.example.cookielogin.model.User;
import com.example.cookielogin.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String uid;

    @Value("${spring.datasource.password}")
    private String pwd;

    public User findUserByEmail(String email) {

        User user = new User();
        user.setEmail(email);

        try {
            Connection conn = ConnectionManager.getConnection(db_url, uid, pwd);
            String queryCreate = "SELECT * FROM users WHERE email=?";
            PreparedStatement psts = conn.prepareStatement(queryCreate);

            //indsæt name og price i prepared statement
            psts.setString(1, email);

            //execute query
            ResultSet rs = psts.executeQuery();
            while (rs.next()){
//            rs.next();
            String password = rs.getString(2);
            user.setPassword(password);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(user);

        return user;
    }

}
