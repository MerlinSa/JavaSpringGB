package ru.gb.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.timesheet.model.*;
import ru.gb.timesheet.repository.*;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class TimesheetApplication7 {

    /**
     * spring-data - ...
     * spring-data-jdbc - зависимость, которая предоставляет удобные преднастроенные инструемнты
     * для работы c реляционными БД
     * spring-data-jpa - зависимость, которая предоставляет удобные преднастроенные инструемнты
     * для работы с JPA
     * spring-data-jpa
     * /
     * /
     * jpa   <------------- hibernate (ecliselink ...)
     * spring-data-mongo - завимость, которая предоставляет инструменты для работы с mongo
     * spring-data-aws
     */

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TimesheetApplication7.class, args);

        Role adminRole = new Role();
        adminRole.setName("admin");
        Role userRole = new Role();
        userRole.setName("user");
        Role restRole = new Role();
        restRole.setName("rest");

        RoleRepository roleRepository = ctx.getBean(RoleRepository.class);

        roleRepository.save(adminRole);
        roleRepository.save(restRole);
        roleRepository.save(userRole);

        UserRepository userRepository = ctx.getBean(UserRepository.class);
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("$2a$12$6RPHhiLo11ACAIlIoYEJO.OM3eBp2jkf9mUre.Peoj0mnFeOIOmCq"); // admin
        admin.getRoles().add(userRole);
        admin.getRoles().add(adminRole);
        userRole.getUsers().add(admin);
        adminRole.getUsers().add(admin);

        User user = new User();
        user.setLogin("user");
        user.setPassword("$2a$12$wRbd3uehKktK4VbHF1f6huUzuaqZceUDmpg522UagG4FehcRXaUIq"); // user
        user.getRoles().add(userRole);
        userRole.getUsers().add(user);

        //
        User rest = new User();
        rest.setLogin("rest");
        rest.setPassword("$2a$12$MWc4BQYdqQEbVbWSjiFK/eK2FzpBpNaj2X4Xf0T8BguMl1/bEP1XG"); // rest
        rest.getRoles().add(restRole);
        userRole.getUsers().add(rest);

        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(rest);

        ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);

        for (int i = 1; i <= 5; i++) {
            Project project = new Project();
            project.setName("Project #" + i);
            projectRepo.save(project);
        }

        TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);

        LocalDate createdAt = LocalDate.now();
        for (int i = 1; i <= 10; i++) {
            createdAt = createdAt.plusDays(1);

            Timesheet timesheet = new Timesheet();
            timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
            timesheet.setCreatedAt(createdAt);
            timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

            timesheetRepo.save(timesheet);
        }
// TODO HOMEWORK#5
        EmployeeRepository employeeRepository = ctx.getBean(EmployeeRepository.class);
        for (int i = 1; i <= 10; i++) {
            Employee employee = new Employee();
            employee.setName("Name #" + i);
            employee.setSecondName("SecondName #" + i);
            employeeRepository.save(employee);
        }
    }

}
