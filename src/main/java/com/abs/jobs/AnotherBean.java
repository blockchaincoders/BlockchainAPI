package com.abs.jobs;

/**
 * Created by imran.khan on 8/7/2017.
 */

import com.abs.entity.BlockInfoBean;
import com.abs.entity.BlockInfoEntity;
import com.abs.service.BlockInfoServiceApi;
import com.abs.utils.AppUtils;
import com.abs.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BlockInfoServiceApi blockInfoService;

    private static String blockNumber;

    private Web3j web3j;

    public void printAnotherMessage() {
        System.out.println("Job Started");
        Response response = new Response();
        try {

            if (web3j == null) {
                web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
            }

            BlockInfoEntity entity = null;
            EthBlock.Block block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"), true).send().getBlock();

            if (block.getNumber().toString().equals(blockNumber)) {
                return;
            }

            blockNumber = block.getNumber().toString();
            //create Block Info entity
            entity = new BlockInfoEntity();
            entity.setNumber(block.getNumber().toString());
            entity.setTransactionSize(String.valueOf(block.getTransactions().size()));
            entity.setHash(block.getHash());
            entity.setParentHash(block.getParentHash());
            entity.setNonceRaw(block.getNonceRaw());
            entity.setTimeStamp(Instant.ofEpochSecond(block.getTimestamp().longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime().toString());
            entity.setMiner(block.getMiner());
            entity.setDifficulty(block.getDifficulty().toString());
            entity.setGasLimit(block.getGasLimit().toString());
            entity.setGasUsed(block.getGasUsed().toString());
            //entity creation ends


            blockInfoService.createBlockInfo(entity);


            response.setStatusCode("00");
            response.setStatusValue("OK");

        } catch (Exception e) {
            response.setStatusCode("99");
            response.setStatusValue("Error:" + e.getMessage());
        }

        System.out.println("Job Ended");
    }


    public BlockInfoServiceApi getBlockInfoService() {
        return blockInfoService;
    }

    public void setBlockInfoService(BlockInfoServiceApi blockInfoService) {
        this.blockInfoService = blockInfoService;
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }


}
