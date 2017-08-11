package com.abs.controller;

import com.abs.entity.*;
import com.abs.service.BlockInfoServiceApi;
import com.abs.utils.AppUtils;
import com.abs.utils.Constant;
import com.abs.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class BlockInfoController {

    @Autowired
    private BlockInfoServiceApi blockInfoService;

    private static final int COUNT = 10;
    private Web3j web3j;


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/createBlockInfo", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String createBlockInfo(HttpServletRequest request)
    {
        Response response = new Response();

        try {

            if(web3j==null) {
                web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
            }

            EthBlock.Block block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"), true).send().getBlock();

            //create Block Info entity
            BlockInfoEntity entity = new BlockInfoEntity();
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

        }catch (Exception e) {
            response.setStatusCode("99");
            response.setStatusValue("Error:"+e.getMessage());
        }
        return AppUtils.convertToJson(response);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/fetchBlockInfoChartsData", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String fetchBlockInfoSet(HttpServletRequest request)
    {
        Response response = new Response();
        try {
            List<BlockInfoEntity> blockInfoEntityList = blockInfoService.fetchBlockInfoSet();

            //main chart list
            List<Object> chartBeanList= new ArrayList<>();

            //bar chart beans
            BarChartBean blockTxnBarChart = new BarChartBean();

            //create area chart
            AreaChartBean blockDifficultyChart = new AreaChartBean();
            AreaChartBean blockCreationTimeChart=new AreaChartBean();

            //setting card div id's
            blockCreationTimeChart.setElement("block-creation-time-area-chart");
            blockDifficultyChart.setElement("block-difficulty-bar-chart");
            blockTxnBarChart.setElement("block-txn-cont-bar-chart");

            //creating data list to populate the data[] in the bar chart
            List<BarChartBlockTxnCountData> barChartBlockTxnCountDataData = new ArrayList<>();

            List<AreaChartBlockDifficultyData> areaChartBlockDifficultyBeansData= new ArrayList<>();
            List<AreaChartBlockCreationTimeData> areaChartBlockCreationTimeData=new ArrayList<>();

            Date lastBlockCreationTime=null;
            //parse the data to the appropriate beans
            for(BlockInfoEntity entity: blockInfoEntityList){

                BarChartBlockTxnCountData blockTxnBean= new BarChartBlockTxnCountData();

                blockTxnBean.setBlockNo(entity.getNumber());
                blockTxnBean.setTxnCount(Integer.parseInt(entity.getTransactionSize()));
                barChartBlockTxnCountDataData.add(blockTxnBean);

                //creating dummy data
                AreaChartBlockDifficultyData blockDifficultyBean= new AreaChartBlockDifficultyData();
                blockDifficultyBean.setBlockNo(entity.getNumber());
                blockDifficultyBean.setDifficultyLevel(Integer.parseInt(entity.getDifficulty()));
                areaChartBlockDifficultyBeansData.add(blockDifficultyBean);

                AreaChartBlockCreationTimeData blockCreationTimeBean=new AreaChartBlockCreationTimeData();
                blockCreationTimeBean.setBlockNo(entity.getNumber());

                Date currentDummyCreationTime=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(entity.getTimeStamp());

                blockCreationTimeBean.setCreationTime(lastBlockCreationTime!=null?""+((currentDummyCreationTime.getTime()-lastBlockCreationTime.getTime())/1000):"0");
                areaChartBlockCreationTimeData.add(blockCreationTimeBean);

                lastBlockCreationTime=currentDummyCreationTime;
                //area charts creation ends
            }

            //set data of blockTxnBarChart
            blockTxnBarChart.setData(barChartBlockTxnCountDataData.toArray());
            blockTxnBarChart.setXkey(BarChartBlockTxnCountData.getXKey());
            blockTxnBarChart.setYkeys(BarChartBlockTxnCountData.getYKeys());
            blockTxnBarChart.setLabels(BarChartBlockTxnCountData.getLabels());
            blockTxnBarChart.setHideHover("auto");
            blockTxnBarChart.setResize(true);


            //set data of blockDifficultyBarChart
            blockDifficultyChart.setData(areaChartBlockDifficultyBeansData.toArray());
            blockDifficultyChart.setXkey(AreaChartBlockDifficultyData.getXKey());
            blockDifficultyChart.setYkeys(AreaChartBlockDifficultyData.getYKeys());
            blockDifficultyChart.setLabels(AreaChartBlockDifficultyData.getLabels());
            blockDifficultyChart.setHideHover("auto");
            blockDifficultyChart.setResize(true);
            blockDifficultyChart.setPointSize(2);

            blockCreationTimeChart.setData(areaChartBlockCreationTimeData.toArray());
            blockCreationTimeChart.setXkey(AreaChartBlockCreationTimeData.getXKey());
            blockCreationTimeChart.setYkeys(AreaChartBlockCreationTimeData.getYKeys());
            blockCreationTimeChart.setLabels(AreaChartBlockCreationTimeData.getLabels());
            blockCreationTimeChart.setHideHover("auto");
            blockCreationTimeChart.setResize(true);
            blockCreationTimeChart.setPointSize(2);

            //add to chart list 0
            chartBeanList.add(blockDifficultyChart);
            //1
            chartBeanList.add(blockTxnBarChart);
            //2
            chartBeanList.add(blockCreationTimeChart);


            response.setStatusCode("00");
            response.setStatusValue("OK");

            response.setData(chartBeanList);

        }catch (Exception e) {
            response.setStatusCode("99");
            response.setStatusValue("Error:"+e.getMessage());
        }
        return AppUtils.convertToJson(response);
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getBlockTxnDetails", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String getBlockTxnDetails(long blockNumber)
    {
        Response response = new Response();
        try {
            if(web3j==null) {
                web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
            }

            List<Transaction> transactions = new ArrayList<Transaction>();
            long i = 0l;
            while (true) {
                try {

                    BigInteger index = BigInteger.valueOf(i);
                    EthTransaction ethTransaction = web3j.ethGetTransactionByBlockNumberAndIndex(
                            DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)), index).send();
                    System.out.println(ethTransaction.getTransaction().isPresent());
                    Transaction transaction = ethTransaction.getTransaction().get();
                    transactions.add(transaction);
                    i=i+1;
                } catch (Exception e) {
                    break;
                }
            }
            response.setData(transactions);

            response.setStatusCode("00");
            response.setStatusValue("OK");

        }catch (Exception e) {
            response.setStatusCode("99");
            response.setStatusValue("Error:"+e.getMessage());
        }
        return AppUtils.convertToJson(response);
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getBasicDetails", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String getBasicDetails()
    {
        Response response = new Response();
        try {
            if(web3j==null) {
                web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
            }

            BasicInfoBean basicInfoBean = new BasicInfoBean();

            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            basicInfoBean.setWeb3ClientVersion(web3ClientVersion.getWeb3ClientVersion());

            NetVersion netVersion = web3j.netVersion().send();
            basicInfoBean.setNetVersion(netVersion.getNetVersion());

            NetPeerCount netPeerCount = web3j.netPeerCount().send();
            basicInfoBean.setPeerCount(netPeerCount.getQuantity().toString());

            EthCoinbase ethCoinbase = web3j.ethCoinbase().send();
            basicInfoBean.setEthCoinBase(ethCoinbase.getAddress());

            EthMining ethMining = web3j.ethMining().send();
            basicInfoBean.setEthMining(ethMining.getResult().toString());

            EthProtocolVersion ethProtocolVersion = web3j.ethProtocolVersion().send();
            basicInfoBean.setEthProtocol(ethProtocolVersion.getProtocolVersion());

            response.setData(basicInfoBean);

            response.setStatusCode("00");
            response.setStatusValue("OK");

        }catch (Exception e) {
            response.setStatusCode("99");
            response.setStatusValue("Error:"+e.getMessage());
        }
        return AppUtils.convertToJson(response);
    }

}
