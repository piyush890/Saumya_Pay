package com.Client.pay.handleclick;

import com.Client.pay.model.AepsSettlementModel;
import com.Client.pay.model.OperaterBean;

public interface FragmentClickListenerBean {
    void itemOnClick(AepsSettlementModel aepsmodel,OperaterBean operater, int pos);
}
