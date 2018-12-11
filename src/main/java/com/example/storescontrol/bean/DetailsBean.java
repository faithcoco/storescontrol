package com.example.storescontrol.bean;

import java.util.ArrayList;
import java.util.List;

public class DetailsBean {


        private String Resultcode;
        private String ResultMessage;
        private List<Data> data=new ArrayList<>();
        public void setResultcode(String Resultcode) {
            this.Resultcode = Resultcode;
        }
        public String getResultcode() {
            return Resultcode;
        }

        public void setResultMessage(String ResultMessage) {
            this.ResultMessage = ResultMessage;
        }
        public String getResultMessage() {
            return ResultMessage;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
        public List<Data> getData() {
            return data;
        }

    public class Data {

        private String cposition;
        private String cInvCode;
        private String cInvName;
        private String cInvStd;
        private String cBatch;
        private String iQuantity;
        public void setCposition(String cposition) {
            this.cposition = cposition;
        }
        public String getCposition() {
            return cposition;
        }

        public void setCInvCode(String cInvCode) {
            this.cInvCode = cInvCode;
        }
        public String getCInvCode() {
            return cInvCode;
        }

        public void setCInvName(String cInvName) {
            this.cInvName = cInvName;
        }
        public String getCInvName() {
            return cInvName;
        }

        public void setCInvStd(String cInvStd) {
            this.cInvStd = cInvStd;
        }
        public String getCInvStd() {
            return cInvStd;
        }

        public void setCBatch(String cBatch) {
            this.cBatch = cBatch;
        }
        public String getCBatch() {
            return cBatch;
        }

        public void setIQuantity(String iQuantity) {
            this.iQuantity = iQuantity;
        }
        public String getIQuantity() {
            return iQuantity;
        }

    }

}
