package com.company.departments.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = -5603268368167953276L;

    private Long id;
    private String fullName;
    private String email;
    private LocalDateTime startedWorkAt;
    private Long departmentId;

    private EmployeeDTO(Builder builder) {
        this.id = builder.id;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.startedWorkAt = builder.startedWorkAt;
        this.departmentId = builder.departmentId;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeDTO that = (EmployeeDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(fullName, that.fullName)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(startedWorkAt, that.startedWorkAt))
            return false;
        return Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (startedWorkAt != null ? startedWorkAt.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private Long id;
        private String fullName;
        private String email;
        private LocalDateTime startedWorkAt;
        private Long departmentId;

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

        public Builder departmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public EmployeeDTO build() {
            return new EmployeeDTO(this);
        }

    }
}
