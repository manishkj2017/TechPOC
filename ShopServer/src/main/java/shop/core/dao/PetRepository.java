package shop.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.core.domain.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer>{

}
