package shop.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.server.domain.PetEntity;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Integer>{

}
