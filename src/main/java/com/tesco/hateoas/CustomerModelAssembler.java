package com.tesco.hateoas;


import com.tesco.controller.CustomerController;
import com.tesco.dto.CustomerResponseDto;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<CustomerResponseDto, CustomerModel> {

    @Override
    public CustomerModel toModel(CustomerResponseDto dto) {
        CustomerModel customerModel = new CustomerModel(dto);
        customerModel.add(
                linkTo(methodOn(CustomerController.class)
                        .getCustomer(dto.getId()))
                        .withSelfRel(),
                linkTo(methodOn(CustomerController.class)
                        .getCustomersByCity(dto.getCity()))
                        .withRel("customersByCity"),
                linkTo(methodOn(CustomerController.class)
                        .getAllCustomers())
                        .withRel("allCustomers")
        );

        return customerModel;
    }
}