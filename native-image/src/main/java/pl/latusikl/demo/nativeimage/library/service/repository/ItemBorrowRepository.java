package pl.latusikl.demo.nativeimage.library.service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.latusikl.demo.nativeimage.library.entity.ItemBorrow;

@Repository
public interface ItemBorrowRepository extends JpaRepository<ItemBorrow, UUID>
{
}
