package com.Client.pay.handleclick;

import com.Client.pay.model.AepsSettlementModel;
import com.Client.pay.model.OperaterBean;

public interface ItemClickListener {
void itemOnClick(AepsSettlementModel aepsSettlementModel,OperaterBean operater, int pos);
}
