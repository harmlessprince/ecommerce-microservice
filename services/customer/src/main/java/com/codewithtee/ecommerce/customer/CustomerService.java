package com.codewithtee.ecommerce.customer;


import com.codewithtee.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.save(customerMapper.toCustomer(customerRequest));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.findById(customerRequest.id()).orElseThrow(() -> new CustomerNotFoundException(
                String.format("Cannot updated customer: No customer found with the provided ID: %s", customerRequest.id())
        ));

        mergeCustomer(customer, customerRequest);
        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank(customerRequest.firstName())) {
            customer.setFirstName(customerRequest.firstName());
        }
        if (StringUtils.isNotBlank(customerRequest.lastName())) {
            customer.setLastName(customerRequest.lastName());
        }
        if (StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }
    }


    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .toList();

    }

    public Boolean customerExistById(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", customerId)));

    }

    public void deleteCustomer(String customerId) {
         customerRepository.deleteById(customerId);
    }
}
