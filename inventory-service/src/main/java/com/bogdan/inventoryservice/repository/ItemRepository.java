package com.bogdan.inventoryservice.repository;

import com.bogdan.inventoryservice.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
