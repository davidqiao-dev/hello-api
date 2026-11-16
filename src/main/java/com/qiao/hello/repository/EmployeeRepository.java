package com.qiao.hello.repository;

import com.qiao.hello.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @EntityGraph(attributePaths = "department")
    List<Employee> findByDepartmentId(Long id);

    @EntityGraph(attributePaths = "department")
    List<Employee> findByDepartment_Name(String Name);

    @EntityGraph(attributePaths = "department")
    List<Employee> findByDepartment_NameContainingIgnoreCase(String keyword);

    @Query("""
            select e from Employee e
            join fetch e.department d
            where lower(d.name) like lower(concat('%', :keyword, '%'))
            """)
    List<Employee> searchByDepartment(@Param("keyword") String keyword);
}
