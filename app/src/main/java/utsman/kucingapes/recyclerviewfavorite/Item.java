package utsman.kucingapes.recyclerviewfavorite;

public class Item {

    private String img, title;

    public Item() {
    }

    public Item(String img, String title) {
        this.img = img;
        this.title = title;
    }

    public Item(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
