package ru.cronfire.test.servlets1.implementations;

public class Part {

    private final String number;
    private final String name;
    private final String vendor;
    private final Integer qty;
    private final String shipped; // dates are hard, serve with SQL
    private final String receive;

    public Part(String number, String name, String vendor, Integer qty, String shipped, String receive) {
        this.name = name;
        this.number = number;
        this.vendor = vendor;
        this.qty = qty;
        this.shipped = shipped;
        this.receive = receive;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getVendor() {
        return vendor;
    }

    public Integer getQty() {
        return qty;
    }

    public String getShipped() {
        return shipped;
    }

    public String getReceive() {
        return receive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Part part = (Part) o;

        if (name != null ? !name.equals(part.name) : part.name != null) return false;
        if (number != null ? !number.equals(part.number) : part.number != null) return false;
        if (vendor != null ? !vendor.equals(part.vendor) : part.vendor != null) return false;
        if (qty != null ? !qty.equals(part.qty) : part.qty != null) return false;
        if (shipped != null ? !shipped.equals(part.shipped) : part.shipped != null) return false;
        return receive != null ? receive.equals(part.receive) : part.receive == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (vendor != null ? vendor.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (shipped != null ? shipped.hashCode() : 0);
        result = 31 * result + (receive != null ? receive.hashCode() : 0);
        return result;
    }
}
