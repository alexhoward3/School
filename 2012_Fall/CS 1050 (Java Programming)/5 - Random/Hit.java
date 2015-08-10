public class Hit
{
    private int hitRow;
    private int hitCol;

    public Hit()
    {
    }

    public Hit(int row, int col)
    {
        hitRow = row;
        hitCol = col;
    }

    public int getHitRow()
    {
        return hitRow;
    }

    public int getHitCol()
    {
        return hitCol;
    }
}