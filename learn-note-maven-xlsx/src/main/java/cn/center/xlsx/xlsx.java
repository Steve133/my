package cn.center.xlsx;

public class xlsx {

	private String STCode;
	private String RegNo;
	private String SDate;
	private String REF;
	private String Operator;
	private String STime;
	private String Amt;
	private String OrderType;
	private String DevCode;
	private String CardNo;
	private String Balance;
	private String PaymentAmt;
	private String ChangeAmt;
	private String TranTime;
	private String LastAddDate;
	private String LastAddType;
	
	
	@Override
	public String toString() {
		return "xlsx [STCode=" + STCode + ", RegNo=" + RegNo + ", SDate=" + SDate + ", REF=" + REF + ", Operator="
				+ Operator + ", STime=" + STime + ", Amt=" + Amt + ", OrderType=" + OrderType + ", DevCode=" + DevCode
				+ ", CardNo=" + CardNo + ", Balance=" + Balance + ", PaymentAmt=" + PaymentAmt + ", ChangeAmt="
				+ ChangeAmt + ", TranTime=" + TranTime + ", LastAddDate=" + LastAddDate + ", LastAddType=" + LastAddType
				+ "]";
	}
	public xlsx(String sTCode, String regNo, String sDate, String rEF, String operator, String sTime, String amt,
			String orderType, String devCode, String cardNo, String balance, String paymentAmt, String changeAmt,
			String tranTime, String lastAddDate, String lastAddType) {
		super();
		STCode = sTCode;
		RegNo = regNo;
		SDate = sDate;
		REF = rEF;
		Operator = operator;
		STime = sTime;
		Amt = amt;
		OrderType = orderType;
		DevCode = devCode;
		CardNo = cardNo;
		Balance = balance;
		PaymentAmt = paymentAmt;
		ChangeAmt = changeAmt;
		TranTime = tranTime;
		LastAddDate = lastAddDate;
		LastAddType = lastAddType;
	}
	public String getSTCode() {
		return STCode;
	}
	public void setSTCode(String sTCode) {
		STCode = sTCode;
	}
	public String getRegNo() {
		return RegNo;
	}
	public void setRegNo(String regNo) {
		RegNo = regNo;
	}
	public String getSDate() {
		return SDate;
	}
	public void setSDate(String sDate) {
		SDate = sDate;
	}
	public String getREF() {
		return REF;
	}
	public void setREF(String rEF) {
		REF = rEF;
	}
	public String getOperator() {
		return Operator;
	}
	public void setOperator(String operator) {
		Operator = operator;
	}
	public String getSTime() {
		return STime;
	}
	public void setSTime(String sTime) {
		STime = sTime;
	}
	public String getAmt() {
		return Amt;
	}
	public void setAmt(String amt) {
		Amt = amt;
	}
	public String getOrderType() {
		return OrderType;
	}
	public void setOrderType(String orderType) {
		OrderType = orderType;
	}
	public String getDevCode() {
		return DevCode;
	}
	public void setDevCode(String devCode) {
		DevCode = devCode;
	}
	public String getCardNo() {
		return CardNo;
	}
	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	public String getBalance() {
		return Balance;
	}
	public void setBalance(String balance) {
		Balance = balance;
	}
	public String getPaymentAmt() {
		return PaymentAmt;
	}
	public void setPaymentAmt(String paymentAmt) {
		PaymentAmt = paymentAmt;
	}
	public String getChangeAmt() {
		return ChangeAmt;
	}
	public void setChangeAmt(String changeAmt) {
		ChangeAmt = changeAmt;
	}
	public String getTranTime() {
		return TranTime;
	}
	public void setTranTime(String tranTime) {
		TranTime = tranTime;
	}
	public String getLastAddDate() {
		return LastAddDate;
	}
	public void setLastAddDate(String lastAddDate) {
		LastAddDate = lastAddDate;
	}
	public String getLastAddType() {
		return LastAddType;
	}
	public void setLastAddType(String lastAddType) {
		LastAddType = lastAddType;
	}
	
	
}
