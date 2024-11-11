package com.tesco.controller;

import com.tesco.dto.CustomerRequestDto;
import com.tesco.dto.CustomerResponseDto;
import com.tesco.hateoas.CustomerModel;
import com.tesco.hateoas.CustomerModelAssembler;
import com.tesco.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerModelAssembler assembler;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CustomerModel> createCustomer(@RequestBody CustomerRequestDto requestDto) {
        CustomerResponseDto responseDto = customerService.createCustomer(requestDto);
        CustomerModel customerModel = assembler.toModel(responseDto);

        return ResponseEntity
                .created(customerModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(customerModel);
    }

    @PreAuthorize("hasRole('ADMIN', 'MANAGER', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerModel> getCustomer(@PathVariable String id) {
        CustomerResponseDto responseDto = customerService.getCustomerById(id);
        log.debug("responseDto is {}", responseDto);
        return ResponseEntity.ok(assembler.toModel(responseDto));
    }

    @PreAuthorize("hasRole('ADMIN', 'MANAGER', 'USER')")
    @GetMapping
    public ResponseEntity<CollectionModel<CustomerModel>> getAllCustomers() {
        List<CustomerResponseDto> customers = customerService.getAllCustomers();
        CollectionModel<CustomerModel> customerModels =
                CollectionModel.of(customers.stream()
                        .map(assembler::toModel)
                        .toList());

        customerModels.add(
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel(),
                linkTo(methodOn(CustomerController.class).createCustomer(null)).withRel("create")
        );

        return ResponseEntity.ok(customerModels);
    }

    @PreAuthorize("hasRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerModel> updateCustomer(
            @PathVariable String id,
            @RequestBody CustomerRequestDto requestDto) {
        CustomerResponseDto responseDto = customerService.updateCustomer(id, requestDto);
        CustomerModel customerModel = assembler.toModel(responseDto);

        return ResponseEntity.ok(customerModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN', 'MANAGER', 'USER')")
    @GetMapping("/city/{city}")
    public ResponseEntity<CollectionModel<CustomerModel>> getCustomersByCity(
            @PathVariable String city) {
        List<CustomerResponseDto> customers = customerService.getCustomersByCity(city);
        CollectionModel<CustomerModel> customerModels =
                CollectionModel.of(customers.stream()
                        .map(assembler::toModel)
                        .toList());

        customerModels.add(
                linkTo(methodOn(CustomerController.class)
                        .getCustomersByCity(city)).withSelfRel(),
                linkTo(methodOn(CustomerController.class)
                        .getAllCustomers()).withRel("allCustomers")
        );

        return ResponseEntity.ok(customerModels);
    }
}