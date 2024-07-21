package com.kafka.Implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class KafkaConsumerService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper; // Jackson ObjectMapper

    @KafkaListener(topics = "employee_topic", groupId = "employee-group")
    public void consume(String message) {
        try {
            // Convert message to Employee object
            Employee employee = objectMapper.readValue(message, Employee.class);
            // Save Employee to the database
            employeeRepository.save(employee);
        } catch (IOException e) {
            // Handle JSON parsing error
            e.printStackTrace();
        }
    }
}
