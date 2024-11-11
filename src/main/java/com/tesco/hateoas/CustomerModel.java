package com.tesco.hateoas;

import com.tesco.dto.CustomerResponseDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CustomerModel extends RepresentationModel<CustomerModel> {
    private final String id;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String gender;
    private final String city;
    private final String phone;

    public CustomerModel(CustomerResponseDto dto) {
        this.id = dto.getId();
        this.firstname = dto.getFirstname();
        this.lastname = dto.getLastname();
        this.email = dto.getEmail();
        this.gender = dto.getGender();
        this.city = dto.getCity();
        this.phone = dto.getPhone();
    }
}