package com.audieni.models;

import java.util.Objects;

public class Ticket {
    private Integer id;
    private Integer userId;
    private double amount;
    private String description;
    private String status;

    public Ticket() {}

    public Ticket(Integer id, Integer userId, double amount, String description, String status) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public Ticket(Integer userId, double amount, String description) {
        this.userId = userId;
        this.amount = amount;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Double.compare(ticket.amount, amount) == 0 && Objects.equals(id, ticket.id) && Objects.equals(userId, ticket.userId) && Objects.equals(description, ticket.description) && Objects.equals(status, ticket.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, amount, description, status);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
