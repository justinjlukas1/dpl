public class linePosition {
    public int lineNum;
    public int colNum;

    public linePosition(int lineNum, int colNum) {
        this.lineNum = lineNum;
        this.colNum = colNum;
    }

    @Override
    public String toString() {
        return "(" + lineNum + "," + colNum + ")";
    }
}