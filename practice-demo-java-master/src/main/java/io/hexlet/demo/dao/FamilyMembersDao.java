package io.hexlet.demo.dao;

import io.hexlet.demo.model.Member;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilyMembersDao extends BaseDao {
    public static List<Member> getAllMembers() {
        String sql = "SELECT * FROM members";
        List<Member> members = new ArrayList<>();
        try (var ps = connection.prepareStatement(sql)) {
            var rs = ps.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString("full_name");
                Date birthday = rs.getDate("birthday");
                Member member = new Member(fullName, birthday);
                members.add(member);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }

    public static void create(Member member) {
        String sql = "INSERT INTO members VALUES (?, ?)";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setString(1, member.getFullName());
            ps.setDate(2, member.getBirthday());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
