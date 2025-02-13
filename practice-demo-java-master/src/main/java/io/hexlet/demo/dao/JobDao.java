package io.hexlet.demo.dao;

import io.hexlet.demo.model.Job;
import io.hexlet.demo.model.Member;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class JobDao extends BaseDao {
    public static Optional<Job> getJobByName(String fullName) {
        String sql = "SELECT * FROM jobs WHERE full_name = ? ORDER BY start_work_at DESC";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setString(1, fullName);
            var rs = ps.executeQuery();
            if (rs.next()) {
                Integer id = rs.getInt("id");
                String profession = rs.getString("profession");
                String organization = rs.getString("organization");
                Integer salary = rs.getInt("salary");
                Date startWorkAt = rs.getDate("start_work_at");
                return Optional.of(new Job(id, profession, organization, salary, startWorkAt));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Job job) {
        String sql = "UPDATE jobs SET salary=? WHERE id=?";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setInt(1, job.getSalary());
            ps.setInt(2, job.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void create(Job job, Member member) {
        String sql = """
                INSERT INTO jobs
                (full_name, profession, organization, salary, start_work_at)
                VALUES (?, ?, ?, ?, ?)""";
        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, member.getFullName());
            ps.setString(2, job.getProfession());
            ps.setString(3, job.getOrganization());
            ps.setInt(4, job.getSalary());
            ps.setDate(5, job.getStartWorkAt());
            ps.execute();
            var keys = ps.getGeneratedKeys();
            if (keys.next()) {
                Integer id = keys.getInt("id");
                job.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
