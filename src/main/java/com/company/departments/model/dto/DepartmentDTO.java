package com.company.departments.model.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DepartmentDTO implements Serializable {

    private static final long serialVersionUID = -1601932630450176840L;

    private Long id;
    private String name;
    private Integer amountOfEmployees;
    private Set<Long> employeeIds;

    private DepartmentDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.amountOfEmployees = builder.amountOfEmployees;
        this.employeeIds = builder.employeeIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(Set<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentDTO that = (DepartmentDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(amountOfEmployees, that.amountOfEmployees))
            return false;
        return Objects.equals(employeeIds, that.employeeIds);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (amountOfEmployees != null ? amountOfEmployees.hashCode() : 0);
        result = 31 * result + (employeeIds != null ? employeeIds.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private Long id;
        private String name;
        private Integer amountOfEmployees;
        private Set<Long> employeeIds = new HashSet<>();

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

        public Builder employees(HashSet<Long> employeeIds) {
            this.employeeIds = employeeIds;
            return this;
        }

        public DepartmentDTO build() {
            return new DepartmentDTO(this);
        }

    }

}
