package com.hotel.interfaces;

import com.hotel.models.Customer;

public interface ICustomer {
    /**
     * Finds a customer by their ID
     * @param customerId The customer ID to search for
     * @return The customer object if found, null otherwise
     * @pre customerId > 0
     * @post Returns customer with given ID or null
     */
    Customer findCustomerById(int customerId);
    
    /**
     * Creates a new customer
     * @param name Customer name
     * @param email Customer email
     * @param phone Customer phone number
     * @return The newly created Customer object
     * @pre name != null && !name.isEmpty() && email != null && !email.isEmpty()
     * @post Returns a new Customer with unique ID
     */
    Customer createCustomer(String name, String email, String phone);
    
    /**
     * Updates an existing customer
     * @param customer The updated customer object
     * @pre customer != null && findCustomerById(customer.getId()) != null
     * @post Customer data is updated
     */
    void updateCustomer(Customer customer);
}