package beer.catalog.api.infrastructure.web.controller;

import beer.catalog.api.application.services.beer.BeerService;
import beer.catalog.api.domain.model.Beer;
import beer.catalog.api.domain.port.in.IBeerUseCases;
import beer.catalog.api.domain.model.BeerSearchCriteria;
import beer.catalog.api.infrastructure.web.dto.CreateBeerDTO;
import beer.catalog.api.infrastructure.web.dto.BeerDTO;
import beer.catalog.api.infrastructure.web.mapper.BeerMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/beers")
public class BeerWebController {
    private final IBeerUseCases service;

    public BeerWebController(BeerService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANUFACTURER')")
    @PostMapping
    public ResponseEntity<BeerDTO> createBeer(@RequestBody CreateBeerDTO dtoRequest){
        Beer created = service.createBeer(
                dtoRequest.name(),
                dtoRequest.ABV(),
                dtoRequest.type(),
                dtoRequest.description(),
                dtoRequest.idManufacturer());
        return ResponseEntity.status(HttpStatus.CREATED).body(BeerMapper.toDto(created));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<BeerDTO> getBeer(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(BeerMapper.toDto(service.getBeer(id)));
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<BeerDTO>> listBeers(BeerSearchCriteria criteria, Pageable pageable){
        List<Beer> beersList;
        if (criteria.isEmpty()){
            beersList = service.getAllBeers(pageable);
        }else{
            beersList = service.getAllBeers(criteria, pageable);
        }
        return ResponseEntity.ok(
                beersList.stream()
                .map(BeerMapper::toDto)
                .toList());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANUFACTURER')")
    @PutMapping("/{id}")
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable Long id,@RequestBody BeerDTO updateBeer){
        if(Objects.equals(id, updateBeer.id())){
            Beer updatedBeer = service.updateBeer(
                    updateBeer.id(),
                    updateBeer.name(),
                    updateBeer.ABV(),
                    updateBeer.type(),
                    updateBeer.description(),
                    updateBeer.idManufacturer());
            return ResponseEntity.ok(BeerMapper.toDto(updatedBeer));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Beer id is different from existing in the body");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeer (@PathVariable Long id) {
        service.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }
}

