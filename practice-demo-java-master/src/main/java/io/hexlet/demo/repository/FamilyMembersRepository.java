package io.hexlet.demo.repository;

import io.hexlet.demo.dao.FamilyMembersDao;
import io.hexlet.demo.dao.JobDao;
import io.hexlet.demo.dao.ProductsAndServicesDao;
import io.hexlet.demo.dao.PurchasesDao;
import io.hexlet.demo.model.Job;
import io.hexlet.demo.model.Member;
import io.hexlet.demo.model.Purchase;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class FamilyMembersRepository {
    public static List<Member> getAll() {
        List<Member> members = FamilyMembersDao.getAllMembers();
        members.forEach(member -> {
            Job job = JobDao.getJobByName(member.getFullName()).orElse(null);
            member.setJob(job);
        });
        return members;
    }

    public static String spendingToIncomeRatio(Member member) {
        int income = member.getJob().getSalary();
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentMonth = today.withDayOfMonth(1);
        LocalDate lastDayOfCurrentMonth = YearMonth.from(today).atEndOfMonth();
        Date dateFrom = Date.valueOf(firstDayOfCurrentMonth);
        Date dateTo = Date.valueOf(lastDayOfCurrentMonth);
        List<Purchase> purchases = PurchasesDao.getAllBetween(member, dateFrom, dateTo);
        int spending = purchases.stream()
                .map(purchase -> {
                    var productOrService = ProductsAndServicesDao.getByName(purchase.getProductOrServiceName());
                    return productOrService.getPrice() * purchase.getQuantity();
                })
                .mapToInt(totalPrice -> totalPrice)
                .sum();
        return income > spending ? "Профицит бюджета" : "Дефицит бюджета";
    }

    public static void update(Member member) {
        if (member.getJob().getId() == null) {
            JobDao.create(member.getJob(), member);
        } else {
            JobDao.update(member.getJob());
        }
    }

    public static void create(Member member) {
        FamilyMembersDao.create(member);
    }
}
