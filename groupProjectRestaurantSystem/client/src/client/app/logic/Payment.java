package client.app.logic;

/**
 * @author markharrison
 *
 */
public class Payment {

  private double amount;
  private int orderNum;
  private int expiry;
  private int cvc;
  private String custName;
  private String cardNum;

  /**
   * 
   * @param amount
   *          the amount in pounds and pence
   * @param orderNum
   *          the associated order number from the order
   * @param cardNum
   *          the customers payment card number
   * @param expiry
   *          the customers payment card expiry
   * @param cvc
   *          the customers payment card cvc
   * @param custName
   *          the customers name as on the card
   */
  public Payment(double amount, int orderNum, String cardNum,  int cvc, String custName, int expiry) {

    this.amount = amount;
    this.orderNum = orderNum;
    this.cardNum = cardNum;
    this.cvc = cvc;
    this.custName = custName;
    this.expiry = expiry;

  }

  /**
   *  Returns a textual representation of the transaction 
   *  @return receipt as a formatted string
   */
  public String Receipt() {

    String strDouble = String.format("%.2f", this.amount);
    return "Amount: £" +  strDouble+ "\nOrder number: " + this.orderNum + "\nCard number: " + " "
        + this.cardNum.substring(0, 12) + "***\nCVC: "+ (this.cvc % 100)+ "*\nExpiry: " + (this.expiry % 100) +"**\nName: " + this.custName;

  }
  /**
   * getter for Cvc variable
   * @return cvc
   */
  public int getCvc(){
    
    return this.cvc;
  }

}
