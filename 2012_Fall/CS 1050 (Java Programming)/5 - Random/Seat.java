public class Seat
{
    private int seatRow = 0;
    private int seatCol = 0;
    private int paintAmount = 0;
    private boolean painted = false;
    private boolean splashed = false;

    public Seat()
    {
    }

    public Seat(int row, int col)
    {
        seatRow = row;
        seatCol = col;
    }

    public Seat(int row, int col, boolean p, boolean s)
    {
        seatRow = row;
        seatCol = col;
        painted = p;
        splashed = s;
    }

    public int getSeatRow()
    {
        return seatRow;
    }

    public int getSeatCol()
    {
        return seatCol;
    }

    public void setPainted(boolean b)
    {
        painted = b;
    }

    public void setSplashed(boolean b)
    {
        splashed = b;
    }
    
    public boolean isPainted()
    {
        return painted;
    }
    
    public boolean isSplashed()
    {
        return splashed;
    }
    
    public void setPaintAmount(int i)
    {
        paintAmount = i;
    }
    
    public int getPaintAmount()
    {
        return paintAmount;
    }
}