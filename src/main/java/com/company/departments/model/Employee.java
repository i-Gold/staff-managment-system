package com.company.departments.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Richard Kamara
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = -439800626348300485L;

    private Long id;
    private String fullName;
    private String email;
    private LocalDateTime startedWorkAt;
    private Department department;

    private Employee(Builder builder) {
        this.id = builder.id;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.startedWorkAt = builder.startedWorkAt;
        this.department = builder.department;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getStartedWorkAt() {
        return startedWorkAt;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!Objects.equals(id, employee.id)) return false;
        if (!Objects.equals(fullName, employee.fullName)) return false;
        if (!Objects.equals(email, employee.email)) return false;
        if (!Objects.equals(startedWorkAt, employee.startedWorkAt))
            return false;
        return Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (startedWorkAt != null ? startedWorkAt.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", startedWorkAt=" + startedWorkAt +
                ", department=" + department +
                '}';
    }

    public static class Builder {

        private Long id;
        private String fullName;
        private String email;
        private LocalDateTime startedWorkAt;
        private Department department;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder startedWorkAt(LocalDateTime startedWorkAt) {
            this.startedWorkAt = startedWorkAt;
            return this;
        }

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

}