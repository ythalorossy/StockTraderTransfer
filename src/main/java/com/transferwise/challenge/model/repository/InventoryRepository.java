package com.transferwise.challenge.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transferwise.challenge.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, String> {

}
