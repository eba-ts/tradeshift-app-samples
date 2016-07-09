package com.tradeshift.thirdparty.samples.springboot.domain.dto;


public class GridRowDTO {

    private Integer id;
    private String character;
    private String alignment;

    public GridRowDTO(Integer id, String character, String alignment) {
        this.id = id;
        this.character = character;
        this.alignment = alignment;
    }

    public GridRowDTO() {
        super();
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }
}

