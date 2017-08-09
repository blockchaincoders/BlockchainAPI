package com.abs.controller;

import com.abs.entity.TransactionHistory;
import com.abs.utils.AppUtils;
import com.abs.utils.Constant;
import com.abs.utils.Response;
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

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/fetchTxnHistory", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String fetchTxnHistory(HttpServletRequest request) {

        Response response = new Response();
        try {

            List<TransactionHistory> txnHistory=new ArrayList<>();

            for(int i=0; i<=10; i++){
                TransactionHistory txn=new TransactionHistory();
                txn.setTxnType("Funds Transfer");
                txn.setFromAccAlias("Hass"+new Random().nextInt(10));
                txn.setToAccAlias("Ali"+new Random().nextInt(10));
                txn.setAmount("1");
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
