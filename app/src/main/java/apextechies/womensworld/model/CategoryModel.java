package apextechies.womensworld.model;

/**
 * Created by shankar on 3/4/18.
 */

public class CategoryModel {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    private String cat_name;

    public CategoryModel(String id, String cat_name){
        this.id = id;
        this.cat_name = cat_name;
    }
}
