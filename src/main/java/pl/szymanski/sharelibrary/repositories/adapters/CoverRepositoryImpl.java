package pl.szymanski.sharelibrary.repositories.adapters;

import org.springframework.stereotype.Repository;
import pl.szymanski.sharelibrary.entity.Cover;
import pl.szymanski.sharelibrary.repositories.jpa.CoverJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.CoverRepository;

import java.util.Optional;

@Repository
public class CoverRepositoryImpl implements CoverRepository {

    private CoverJPARepository coverJpaRepository;

    @Override
    public Optional<Cover> getCoverById(Long id) {
        return coverJpaRepository.findCoverById(id);
    }
}
