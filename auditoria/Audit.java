package auditoria;

import java.util.Date;
import java.util.List;

public class Audit {
    private Date date;
    private String poNumber;
    private String articleNumber;
    private int orderQuantity;
    private int sampleQuantity;
    private String status;
    private List<String> defects;
    private List<Integer> quantities;

    public Audit(Date date, String poNumber, String articleNumber, int orderQuantity, int sampleQuantity, String status, List<String> defects, List<Integer> quantities) {
        this.date = date;
        this.poNumber = poNumber;
        this.articleNumber = articleNumber;
        this.orderQuantity = orderQuantity;
        this.sampleQuantity = sampleQuantity;
        this.status = status;
        this.defects = defects;
        this.quantities = quantities;
    }

    // Getters e Setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(int sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getDefects() {
        return defects;
    }

    public void setDefects(List<String> defects) {
        this.defects = defects;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date: ").append(date).append(", ");
        sb.append("PO Number: ").append(poNumber).append(", ");
        sb.append("Article Number: ").append(articleNumber).append(", ");
        sb.append("Order Quantity: ").append(orderQuantity).append(", ");
        sb.append("Sample Quantity: ").append(sampleQuantity).append(", ");
        sb.append("Status: ").append(status).append(", ");
        sb.append("Defects: ").append(defects).append(", ");
        sb.append("Quantities: ").append(quantities);
        return sb.toString();
    }
}




