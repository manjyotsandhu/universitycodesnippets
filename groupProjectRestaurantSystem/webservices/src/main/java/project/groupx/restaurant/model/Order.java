package project.groupx.restaurant.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
  private long id;
  private int tableId;
  private int waiterId;
  private long timeOfOrder;
  private String menuItemList;
  private double totalCost;
  private double totalPaid;
  private int orderStage;
  
  public Order() {
    this.id = 0;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getTableId() {
    return tableId;
  }

  public void setTableId(int tableId) {
    this.tableId = tableId;
  }

  public long getTimeOfOrder() {
    return timeOfOrder;
  }

  public void setTimeOfOrder(long timeOfOrder) {
    this.timeOfOrder = timeOfOrder;
  }

  public String getMenuItemList() {
    return menuItemList;
  }

  public void setMenuItemList(String menuItemList) {
    this.menuItemList = menuItemList;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
  }

  public double getTotalPaid() {
    return totalPaid;
  }

  public void setTotalPaid(double totalPaid) {
    this.totalPaid = totalPaid;
  }

  public int getOrderStage() {
    return orderStage;
  }

  public void setOrderStage(int orderStage) {
    this.orderStage = orderStage;
  }
  
  public int getWaiterId() {
    return waiterId;
  }

  public void setWaiterId(int waiterId) {
    this.waiterId = waiterId;
  }
  
  
}
