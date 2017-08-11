package com.abs.controller;

import com.abs.entity.TransactionHistory;
import com.abs.entity.TransactionHistoryEntity;
import com.abs.service.TransactionHistoryServiceApi;
import com.abs.utils.AppUtils;
import com.abs.utils.Constant;
import com.abs.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class TransactionController {

    @Autowired
    private TransactionHistoryServiceApi transactionHistoryService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/fetchTxnHistory", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String fetchTxnHistory(HttpServletRequest request) {

        Response response = new Response();
        try {
            Integer idCustomer =(Integer) request.getSession(false).getAttribute(Constant.ID_CUSTOMER_KEY);
//			Integer idCustomer =Integer.valueOf(183);

            List<TransactionHistoryEntity> entityList=transactionHistoryService.fetchAllTxnHistoryByCustomerId(idCustomer);

            List<TransactionHistory> txnHistory=new ArrayList<>();

            for(TransactionHistoryEntity entity:entityList){

                TransactionHistory txn=new TransactionHistory();
                txn.setFromAccAlias(entity.getFromAccAlias());
                txn.setFromAccAddress(entity.getFromAccAddress());
                txn.setToAccAlias(entity.getToAccAlias());
                txn.setToAccAddress(entity.getToAccAddress());
                txn.setAmount(entity.getTxnAmount());
                txn.setStatus(entity.getTxnStatus().equals("00")?"Success":"Failed");
                txn.setTxnDate(entity.getTxnDate().toString());
                txnHistory.add(txn);
            }

            response.setDataList(txnHistory);
            response.setStatusCode("00");
            response.setStatusValue("OK");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppUtils.convertToJson(response);
    }


}
