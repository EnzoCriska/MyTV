package vn.com.dtsgroup.mytv;

public class Chanel {
    private String name;
    private String url;
    private String urlLogo;
    private int views = 0;
    private Boolean isFavoriteChanel = false;

    public Chanel(String name, String url, String urlLogo){
        this.name = name;
        this.url = url;
        this.urlLogo = urlLogo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Boolean getFavoriteChanel() {
        return isFavoriteChanel;
    }

    public void setFavoriteChanel(Boolean favoriteChanel) {
        isFavoriteChanel = favoriteChanel;
    }
}
