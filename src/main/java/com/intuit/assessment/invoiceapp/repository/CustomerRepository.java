package com.intuit.assessment.invoiceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.intuit.assessment.invoiceapp.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query("SELECT c FROM Customer c WHERE c.name LIKE CONCAT(:name,'%')")
    List<Customer> findCustomersStartingWith(@Param("name") String name);

}
