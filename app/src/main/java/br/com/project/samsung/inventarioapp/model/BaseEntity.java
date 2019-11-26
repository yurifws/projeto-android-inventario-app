package br.com.project.samsung.inventarioapp.model;

public class BaseEntity {
    private String id = "";

    public BaseEntity(){
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
