package carRental.module;

import javafx.beans.property.SimpleStringProperty;

public class ChartShow {
    private SimpleStringProperty index;
    private SimpleStringProperty profit;

    public ChartShow(String index,String profit){
        this.index=new SimpleStringProperty(index);
        this.profit=new SimpleStringProperty(profit);
    }

    public String getIndex() {
        return index.get();
    }

    public SimpleStringProperty indexProperty() {
        return index;
    }

    public void setIndex(String index) {
        this.index.set(index);
    }

    public String getProfit() {
        return profit.get();
    }

    public SimpleStringProperty profitProperty() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit.set(profit);
    }
}
