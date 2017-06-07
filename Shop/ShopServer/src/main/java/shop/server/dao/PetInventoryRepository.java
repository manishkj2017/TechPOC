package shop.server.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shop.server.domain.PetInventoryEntity;

@Repository
public interface PetInventoryRepository extends JpaRepository<PetInventoryEntity, Integer>{
	
	@Query("select i from PetInventoryEntity i")
	public List<PetInventoryEntity> getPetsInventory();

}
