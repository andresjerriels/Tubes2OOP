package model;

public enum ENGIMON {
    CHARMAMON("view/resources/engimons/charmamon.gif"),
    PIKAMON("view/resources/engimons/pikamon.gif");

    String urlImg;

    private ENGIMON(String urlImg) {
        this.urlImg = urlImg;
    }
}
