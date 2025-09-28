public class Balance {
    private int leftWeight;
    private int rightWeight;

    public void addLeft(int weight) {
        leftWeight = leftWeight + weight;
    }

    public void addRight(int weight) {
        rightWeight = rightWeight + weight;
    }

    public String result() {
        if (leftWeight > rightWeight) {
            return "L";
        } else if (rightWeight > leftWeight) {
            return "R";
        } else {
            return "=";
        }
    }
}