package com.example.inspire_environment;

import com.example.inspire_environment.entity.Department;
import com.example.inspire_environment.entity.Role;
import com.example.inspire_environment.entity.Staff;
import com.example.inspire_environment.repository.DepartmentRepository;
import com.example.inspire_environment.repository.RoleRepository;
import com.example.inspire_environment.repository.StaffRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class InspireEnvironmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(InspireEnvironmentApplication.class, args);
    }

    @Bean
    CommandLineRunner init(DepartmentRepository departmentRepository, RoleRepository roleRepository, StaffRepository staffRepository) {
        return args -> {

            if(roleRepository.count() == 0 && departmentRepository.count() == 0 && staffRepository.count() == 0)
            {

                // role initialization

                Role roleADMIN = new Role();
                roleADMIN.setName("ADMIN");
                roleADMIN.setDescription("Administrator with full access");

                roleRepository.save(roleADMIN);

                Role roleSTAFF = new Role();
                roleSTAFF.setName("STAFF");
                roleSTAFF.setDescription("Staff member with limited access");

                roleRepository.save(roleSTAFF);

                Role roleSTUDENT = new Role();
                roleSTUDENT.setName("STUDENT");
                roleSTUDENT.setDescription("Student user with basic access");

                roleRepository.save(roleSTUDENT);

                // department  initialization

                Department deptInspie = new Department();
                deptInspie.setName("INSPIRE");
                deptInspie.setDescription("Department of INSPIRE in Bengrir");

                departmentRepository.save(deptInspie);

                Department deptNKOONyoussofia = new Department();
                deptNKOONyoussofia.setName("NKOON - Youssoufia");
                deptNKOONyoussofia.setDescription("Department of NKOON in Youssoufia");

                departmentRepository.save(deptNKOONyoussofia);

                Department deptNKOONeljadida = new Department();
                deptNKOONeljadida.setName("NKOON - El Jadida");
                deptNKOONeljadida.setDescription("Department of NKOON in El Jadida");

                departmentRepository.save(deptNKOONeljadida);

                Department deptNKOONkhouribga = new Department();
                deptNKOONkhouribga.setName("NKOON - khouribga");
                deptNKOONkhouribga.setDescription("Department of NKOON in khouribga");

                departmentRepository.save(deptNKOONkhouribga);

                // staff  initialization

                Staff staffInspire = new Staff();
                staffInspire.setFirstName("Admin");
                staffInspire.setLastName("Inspire");
                staffInspire.setEmail("admin@gmail.com");
                staffInspire.setPassword("InspirePa$$w0rd");
                staffInspire.setPosition("Administrator");
                staffInspire.setRole(roleADMIN);
                staffInspire.setDepartment(deptInspie);

                staffRepository.save(staffInspire);


            }

        };
    }
}
