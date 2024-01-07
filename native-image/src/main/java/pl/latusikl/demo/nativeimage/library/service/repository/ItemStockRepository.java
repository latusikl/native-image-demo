package pl.latusikl.demo.nativeimage.library.service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.latusikl.demo.nativeimage.library.entity.ItemStock;

public interface ItemStockRepository extends JpaRepository<ItemStock, UUID>
{
}
