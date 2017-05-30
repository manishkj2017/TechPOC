package shop.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shop.core.domain.PetInventory;

@Repository
public interface PetInventoryRepository extends JpaRepository<PetInventory, Integer>{
	
	@Query("select i from PetInventory i")
	public List<PetInventory> getPetsInventory();

}
