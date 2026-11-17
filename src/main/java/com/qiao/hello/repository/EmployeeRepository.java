package com.qiao.hello.repository;

import com.qiao.hello.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @EntityGraph(attributePaths = "department")
    List<Employee> findByDepartmentId(Long id);

    @EntityGraph(attributePaths = "department")
    Page<Employee> findByDepartmentId(Long id, Pageable pageable);

    @EntityGraph(attributePaths = "department")
    List<Employee> findByDepartment_Name(String Name);

    @EntityGraph(attributePaths = "department")
    Page<Employee> findByDepartment_Name(String Name, Pageable pageable);

    @EntityGraph(attributePaths = "department")
    List<Employee> findByDepartment_NameContainingIgnoreCase(String keyword);

    @EntityGraph(attributePaths = "department")
    Page<Employee> findByDepartment_NameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("""
            select e from Employee e
            join fetch e.department d
            where lower(d.name) like lower(concat('%', :keyword, '%'))
            """)
    List<Employee> searchByDepartment(@Param("keyword") String keyword);

    @Query("""
                select e from Employee e
                join fetch e.department d
                where lower(d.name) like lower(concat('%', :keyword, '%'))
            """)
    Page<Employee> searchByDepartmentAndName(@Param("keyword") String keyword, Pageable pageable);
}
