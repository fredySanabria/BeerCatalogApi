package beer.catalog.api.infrastructure.persistence.adapter;

import beer.catalog.api.domain.model.Beer;
import beer.catalog.api.domain.port.out.IBeerCRUDRepository;
import beer.catalog.api.infrastructure.persistence.entity.BeerEntity;
import beer.catalog.api.infrastructure.persistence.mapper.BeerMapper;
import beer.catalog.api.infrastructure.persistence.repository.BeerJPARepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BeerRepositoryAdapter implements IBeerCRUDRepository {

    public BeerRepositoryAdapter(BeerJPARepository repository) {
        this.repository = repository;
    }

    private final BeerJPARepository repository;

    @Override
    public Beer createBeer(Beer beer) {
        BeerEntity beerEntity = repository.save(BeerMapper.toEntity(beer));
        return BeerMapper.toDomain(beerEntity);
    }

    @Override
    public Beer updateBeer(Beer beer) {
        BeerEntity beerEntity = repository.save(BeerMapper.toEntity(beer));
        return BeerMapper.toDomain(beerEntity);
    }

    @Override
    public void deleteBeer(Beer beer) {
        repository.delete(BeerMapper.toEntity(beer));
    }

    @Override
    public List<Beer> getAllBeers() {
        return repository.findAll().stream()
                .map(BeerMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Beer> getBeer(Long id) {
        return repository.findById(id).map(BeerMapper::toDomain);
    }
}
