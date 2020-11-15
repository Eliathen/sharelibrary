package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.szymanski.sharelibrary.entity.Cover;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoverView {

    private String id;

    private String name;

    private String type;

    private byte[] data;

    public static CoverView of(Cover cover) {
        return new CoverView(
                cover.getId(),
                cover.getName(),
                cover.getType(),
                cover.getData()
        );
    }


}
