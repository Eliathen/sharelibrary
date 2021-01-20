package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Cover;

public class CoverGenerator {


    public static Cover getCover() {
        Cover cover = new Cover();
        cover.setId(1L);
        cover.setName("test Photo");
        cover.setType("image/jpeg");
        cover.setData(new byte[]{'z'});
        return cover;
    }
}
