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

    public User findUserByEmail(String email, String password) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try {
            Connection conn = ConnectionManager.getConnection(db_url, uid, pwd);
            String queryCreate = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement psts = conn.prepareStatement(queryCreate);

            //indsæt name og price i prepared statement
            psts.setString(1, email);
            psts.setString(2, password);

            //execute query
            ResultSet rs = psts.executeQuery();
            while (rs.next()){
//
                return user;
            }

            return null;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }



    }

}
