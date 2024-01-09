package pl.latusikl.demo.app.library.service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.latusikl.demo.app.library.entity.ItemStock;

public interface ItemStockRepository extends JpaRepository<ItemStock, UUID>
{
}
