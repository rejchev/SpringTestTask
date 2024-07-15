package pw.rejchev.springtesttask.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SearcherStruct {

    String search;
    Integer limit;
    Integer offset;

    @Override
    public String toString() {
        return "search=" + search + "&limit=" + limit + "&offset=" + offset;
    }
}
