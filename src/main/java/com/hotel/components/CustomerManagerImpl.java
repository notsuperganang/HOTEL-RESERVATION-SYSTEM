package com.hotel.components;

import java.util.HashMap;
import java.util.Map;
import com.hotel.interfaces.ICustomer;
import com.hotel.models.Customer;

/**
 * Implementation of the Customer Manager component
 */
public class CustomerManagerImpl implements ICustomer {
    private Map<Integer, Customer> customers = new HashMap<>();
    private int nextId = 1;
    
    @Override
    public Customer findCustomerById(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        return customers.get(customerId);
    }
    
    @Override
    public Customer createCustomer(String name, String email, String phone) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Customer email cannot be empty");
        }
        
        Customer customer = new Customer(nextId++, name, email, phone);
        customers.put(customer.getId(), customer);
        return customer;
    }
    
    @Override
    public void updateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (!customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Customer not found");
        }
        
        customers.put(customer.getId(), customer);
    }
}