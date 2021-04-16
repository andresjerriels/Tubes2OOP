package Map;

public class Position {
    private int x;
    private int y;

    public Position(int _x, int _y){
        x = _x;
        y = _y;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public void setXY(String direction){
        if(direction.equals("w")){
            y--;
        } else if (direction.equals("a")){
            x--;
        } else if(direction.equals("s")){
            y++;
        } else if (direction.equals("d")){
            x++;
        }
    }

    public void setXY(Position other){
        x= other.getX();
        y = other.getY();
    }

    public void resetXY(String direction){
        if(direction.equals("w")){
            y++;
        } else if (direction.equals("a")){
            x++;
        } else if(direction.equals("s")){
            y--;
        } else if (direction.equals("d")){
            x--;
        }
    }

    public boolean isEqual(int x, int y){
        return x == this.x && y == this.y;
    }
}
