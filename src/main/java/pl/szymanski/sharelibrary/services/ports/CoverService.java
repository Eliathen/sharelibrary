package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.Cover;

public interface CoverService {

    Cover getCoverByBookId(Long id);

}
