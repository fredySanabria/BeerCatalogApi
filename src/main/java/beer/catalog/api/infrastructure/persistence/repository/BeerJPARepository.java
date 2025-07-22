package beer.catalog.api.infrastructure.persistence.repository;


import beer.catalog.api.infrastructure.persistence.entity.BeerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerJPARepository extends JpaRepository<BeerEntity,Long>, JpaSpecificationExecutor<BeerEntity> {
}
