public class ActionCost {
    Costs.ACTIONS action;
    private double cost;

    public ActionCost(Costs.ACTIONS action, double cost) {
        this.action = action;
        this.cost = cost;
    }

    public Costs.ACTIONS getAction() {
        return action;
    }

    public void setAction(Costs.ACTIONS action) {
        this.action = action;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
