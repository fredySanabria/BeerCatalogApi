package beer.catalog.api.infrastructure.persistence.adapter;




import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.model.ManufacturerSearchCriteria;
import beer.catalog.api.domain.port.out.IManufacturerCRUDRepository;
import beer.catalog.api.infrastructure.persistence.entity.ManufacturerEntity;
import beer.catalog.api.infrastructure.persistence.mapper.ManufacturerMapper;
import beer.catalog.api.infrastructure.persistence.repository.ManufacturerJPARepository;
import beer.catalog.api.infrastructure.persistence.specification.ManufacturerSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ManufacturerRepositoryAdapter implements IManufacturerCRUDRepository {

    private final ManufacturerJPARepository repository;

    public ManufacturerRepositoryAdapter(ManufacturerJPARepository repository) {
        this.repository = repository;
    }


    @Override
    public Manufacturer createManufacturer(Manufacturer beerManufacturer) {
        ManufacturerEntity entity = ManufacturerMapper.toEntity(beerManufacturer);
        repository.save(entity);
        return ManufacturerMapper.toDomain(entity);
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer beerManufacturer) {
        ManufacturerEntity entity = ManufacturerMapper.toEntity(beerManufacturer);
        repository.save(entity);
        return ManufacturerMapper.toDomain(entity);
    }

    @Override
    public void deleteManufacturer(Manufacturer beerManufacturer) {
        ManufacturerEntity entity = ManufacturerMapper.toEntity(beerManufacturer);
        repository.delete(entity);
    }

    @Override
    public List<Manufacturer> getAllManufacturers(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(ManufacturerMapper::toDomain)
                .toList();
    }

    @Override
    public List<Manufacturer> getAllManufacturers(ManufacturerSearchCriteria criteria, Pageable pageable) {
        Specification<ManufacturerEntity> spec = ManufacturerSpecification.nameContains(criteria.name())
                .and(ManufacturerSpecification.countryEquals(criteria.country()));
        return repository.findAll(spec,pageable)
                .map(ManufacturerMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Manufacturer> getManufacturer(Long id) {
        return repository.findById(id).map(ManufacturerMapper::toDomain);
    }
}
