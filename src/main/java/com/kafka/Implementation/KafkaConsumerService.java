package com.kafka.Implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @KafkaListener(topics = "employee_topic", groupId = "employee-group")
    public void consume(String message) {
        logger.info("Received message from Kafka topic 'employee_topic': {}", message);
        try {
            // Create a new Employee object based on the message
            Employee employee = new Employee();
            employee.setName(message); // Set other fields if needed

            // Log before saving
            logger.debug("Creating Employee object: {}", employee);

            // Save Employee to the database
            Employee savedEmployee = employeeRepository.save(employee);

            // Log after saving
            logger.info("Successfully saved employee: {}", savedEmployee);

        } catch (Exception e) {
            // Handle parsing or other errors
            logger.error("Failed to process message: {}", message, e);
        }
    }
}
