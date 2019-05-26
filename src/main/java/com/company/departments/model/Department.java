package com.company.departments.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Richard Kamara
 */
public class Department implements Serializable {

    private static final long serialVersionUID = 3121763104099320339L;

    private Long id;
    private String name;
    private Integer amountOfEmployees;
    private Set<Employee> employees;

    private Department(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.amountOfEmployees = builder.amountOfEmployees;
        this.employees = builder.employees;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountOfEmployees() {
        return amountOfEmployees;
    }

    public void setAmountOfEmployees(Integer amountOfEmployees) {
        this.amountOfEmployees = amountOfEmployees;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(amountOfEmployees, that.amountOfEmployees))
            return false;
        return Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (amountOfEmployees != null ? amountOfEmployees.hashCode() : 0);
        result = 31 * result + (employees != null ? employees.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountOfEmployees=" + amountOfEmployees +
                ", employees=" + employees +
                '}';
    }

    public static class Builder {

        private Long id;
        private String name;
        private Integer amountOfEmployees;
        private Set<Employee> employees = new HashSet<>();

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder amountOfEmployees(Integer amount) {
            this.amountOfEmployees = amount;
            return this;
        }

        public Builder employees(HashSet<Employee> employees) {
            this.employees = employees;
            return this;
        }

        public Department build() {
            return new Department(this);
        }

    }

}
