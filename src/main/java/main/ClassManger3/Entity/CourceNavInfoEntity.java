package main.ClassManger3.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="courcesNavInfo")
public class CourceNavInfoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long CourceNavID;

    private Long CourceID;

    private String Catogory;

    private String CatogoryLink;

    public CourceNavInfoEntity(Long courceId, String catogory, String catogoryLink) {
        setCourceID(courceId);
        setCatogory(catogory);
        setCatogoryLink(catogoryLink);

    }

    public CourceNavInfoEntity(){
        super();
    }

    public Long getCourceID() {
        return CourceID;
    }

    public void setCourceID(Long courceId) {
        CourceID = courceId;
    }

    public String getCatogory() {
        return Catogory;
    }

    public void setCatogory(String catogory) {
        Catogory = catogory;
    }

    public Long getCourceNavID() {
        return CourceNavID;
    }

    public void setCourceNavID(Long courceNavID) {
        CourceNavID = courceNavID;
    }

    public String getCatogoryLink() {
        return CatogoryLink;
    }

    public void setCatogoryLink(String catogoryLink) {
        CatogoryLink = catogoryLink;
    }
}
