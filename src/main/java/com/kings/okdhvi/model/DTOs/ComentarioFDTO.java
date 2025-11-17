package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ComentarioFDTO extends ComentarioDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
