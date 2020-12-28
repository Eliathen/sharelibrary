package pl.szymanski.sharelibrary.repositories.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.szymanski.sharelibrary.entity.Cover;
import pl.szymanski.sharelibrary.repositories.jpa.CoverJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.CoverRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CoverRepositoryImpl implements CoverRepository {

    private final CoverJPARepository coverJPARepository;

    @Override
    public Optional<Cover> getCoverById(Long id) {
        return coverJPARepository.findCoverById(id);
    }
}
