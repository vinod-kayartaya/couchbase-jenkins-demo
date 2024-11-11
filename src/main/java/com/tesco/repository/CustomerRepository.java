package com.tesco.repository;

import com.tesco.entity.Customer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CouchbaseRepository<Customer, String> {
    List<Customer> findByCity(String city);

    Optional<Customer> findByEmail(String email);
}