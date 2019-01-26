package net.ngeor.bitbucket;

import java.util.List;
import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Generated("com.robohorse.robopojogenerator")
public class Repositories implements Paginated<Repository> {

    @JsonProperty("next")
    private String next;

    @JsonProperty("size")
    private int size;

    @JsonProperty("values")
    private List<Repository> values;

    @JsonProperty("page")
    private int page;

    @JsonProperty("pagelen")
    private int pagelen;

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String getNext() {
        return next;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setValues(List<Repository> values) {
        this.values = values;
    }

    @Override
    public List<Repository> getValues() {
        return values;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPagelen(int pagelen) {
        this.pagelen = pagelen;
    }

    public int getPagelen() {
        return pagelen;
    }

    @Override
    public String toString() {
        return "Repositories{"
            + "next = '" + next + '\'' + ",size = '" + size + '\'' + ",values = '" + values + '\'' + ",page = '" + page
            + '\'' + ",pagelen = '" + pagelen + '\'' + "}";
    }
}
