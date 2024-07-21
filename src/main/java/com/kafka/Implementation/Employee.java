package com.kafka.Implementation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Employee {

    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;
    private String position;

    // Getters and setters

    public Long getId() {
        logger.debug("Getting ID: {}", id);
        return id;
    }

    public void setId(Long id) {
        logger.debug("Setting ID: {}", id);
        this.id = id;
    }

    public String getName() {
        logger.debug("Getting Name: {}", name);
        return name;
    }

    public void setName(String name) {
        logger.debug("Setting Name: {}", name);
        this.name = name;
    }

    public String getDepartment() {
        logger.debug("Getting Department: {}", department);
        return department;
    }

    public void setDepartment(String department) {
        logger.debug("Setting Department: {}", department);
        this.department = department;
    }

    public String getPosition() {
        logger.debug("Getting Position: {}", position);
        return position;
    }

    public void setPosition(String position) {
        logger.debug("Setting Position: {}", position);
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
