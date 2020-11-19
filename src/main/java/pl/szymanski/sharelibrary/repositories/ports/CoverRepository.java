package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Cover;

import java.util.Optional;

public interface CoverRepository {

    Optional<Cover> getCoverById(Long id);
}
