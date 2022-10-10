package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class CustomerService {

    static Collection<Customer> customers = new ArrayList<>();
    private static CustomerService customerService = null;

        public static Collection<Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(Collection<Customer> customers) {
        CustomerService.customers = customers;
    }

    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.add(customer);
    }

    public Customer getCustomer(String customerEmail) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(customerEmail.toLowerCase(Locale.ROOT))) {
                return customer;
            }
        }
        return null;
    }

        public Collection<Customer> getAllCustomer() {
        return customers;
    }
}
