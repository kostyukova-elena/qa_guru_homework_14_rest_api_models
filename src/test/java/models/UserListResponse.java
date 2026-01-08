package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListResponse {
    private int page;
    private int total_pages;
    private List<Data> data;
}
