package com.abs.jobs;

/**
 * Created by imran.khan on 8/7/2017.
 */

import com.abs.entity.BlockInfoBean;
import com.abs.utils.Response;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Component("anotherBean")
public class AnotherBean {

    private Web3j web3j;

    public void printAnotherMessage(){
        System.out.println("Job Started");
        Response response = new Response();
        try {

            if(web3j==null) {
                web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
            }

            EthBlock.Block block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"), true).send().getBlock();

            BlockInfoBean blockInfoBean = new BlockInfoBean();
            blockInfoBean.setNumber(block.getNumber().toString());
            blockInfoBean.setHash(block.getHash());
            blockInfoBean.setParentHash(block.getParentHash());
            blockInfoBean.setTransactionSize(String.valueOf(block.getTransactions().size()));
            blockInfoBean.setTransactionsRoot(block.getTransactionsRoot());
            blockInfoBean.setTimeStamp(Instant.ofEpochSecond(block.getTimestamp().longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime().toString());
            blockInfoBean.setMiner(block.getMiner());
            blockInfoBean.setExtraData(block.getExtraData());
            blockInfoBean.setNonceRaw(block.getNonceRaw());
            blockInfoBean.setDifficulty(block.getDifficulty().toString());
            blockInfoBean.setGasLimit(block.getGasLimit().toString());
            blockInfoBean.setLogsBloom(block.getLogsBloom());
            blockInfoBean.setGasUsed(block.getGasUsed().toString());
            blockInfoBean.setMixHash(block.getMixHash());
            blockInfoBean.setReceiptsRoot(block.getReceiptsRoot());
            blockInfoBean.setSizeRaw(block.getSizeRaw());

            List<EthBlock.TransactionResult> transactionResults = block.getTransactions();
            for (int i=0; i< transactionResults.size(); i++) {
                EthBlock.TransactionResult transactionResult = transactionResults.get(i);
                blockInfoBean.getTransactionResults().add(transactionResult.get().toString());
            }

            response.setData(blockInfoBean);
            response.setStatusCode("00");
            response.setStatusValue("OK");

        }catch (Exception e) {
            response.setStatusCode("99");
            response.setStatusValue("Error:"+e.getMessage());
        }







        System.out.println("Job Ended");
    }

}
