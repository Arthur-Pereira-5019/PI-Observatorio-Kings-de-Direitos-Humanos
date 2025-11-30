package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class NewsResponseDTO {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String status;
    private int totalResults;
    private List<NoticiaAgregadaDTO> results;
    private String nextPage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NoticiaAgregadaDTO> getResults() {
        return results;
    }

    public void setResults(List<NoticiaAgregadaDTO> results) {
        this.results = results;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }
}
