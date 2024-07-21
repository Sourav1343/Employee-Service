package com.kafka.Implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // Endpoint to create a new employee
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        logger.info("Received request to create employee: {}", employee);
        Employee savedEmployee = employeeRepository.save(employee);
        logger.info("Employee created and saved: {}", savedEmployee);

        // Send message to Kafka
        String message = "New employee created: " + employee.getId()+" "+employee.getName() +" "+ employee.getDepartment() +" "+employee.getPosition();
        kafkaProducerService.sendMessage(message);
        logger.info("Sent Kafka message: {}", message);

        return ResponseEntity.ok(savedEmployee);
    }

    // Endpoint to get an employee by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        logger.info("Received request to get employee with ID: {}", id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            logger.info("Found employee: {}", employee.get());
            return ResponseEntity.ok(employee.get());
        } else {
            logger.warn("Employee with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get all employees
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Employee> getAllEmployees() {
        logger.info("Received request to get all employees");
        List<Employee> employees = employeeRepository.findAll();
        logger.info("Found {} employees", employees.size());
        return employees;
    }
}
