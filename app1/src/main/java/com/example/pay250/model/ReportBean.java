package com.Client.pay.model;

public class ReportBean {
//    private ArrayList<TranstionData> transtionlist = new ArrayList();
//
//    public ArrayList<TranstionData> getTranstionlist() {
//        return transtionlist;
//    }
//
//    public void setTranstionlist(ArrayList<TranstionData> transtionlist) {
//        this.transtionlist = transtionlist;
//    }

//    public class TranstionData {
        private String id;
        private String created_at;
        private String number;
        private String txnid;
        private String amount;
        private String profit;
        private String total_balance;
        private String status_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTxnid() {
            return txnid;
        }

        public void setTxnid(String txnid) {
            this.txnid = txnid;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        public String getTotal_balance() {
            return total_balance;
        }

        public void setTotal_balance(String total_balance) {
            this.total_balance = total_balance;
        }

        public String getStatus_id() {
            return status_id;
        }

        public void setStatus_id(String status_id) {
            this.status_id = status_id;
        }
//    }
}
