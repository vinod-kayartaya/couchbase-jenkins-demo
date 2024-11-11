package com.tesco.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.repository.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
@Collection("customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    @Field
    private String firstname;
    @Field
    private String lastname;
    @Field
    private String email;
    @Field
    private String phone;
    @Field
    private String gender;
    @Field
    private String city;
}
