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
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
            List<BarChartBean> barChartBeanList= new ArrayList<>();

            //bar chart beans
            BarChartBean blockDifficultyBarChart = new BarChartBean();
            BarChartBean blockTxnBarChart = new BarChartBean();

            //setting card div id's
            blockDifficultyBarChart.setElement("block-difficulty-bar-chart");
            blockTxnBarChart.setElement("block-txn-cont-bar-chart");

            //creating data list to populate the data[] in the bar chart
            List<BarChartBlockDifficultyData> barChartBlockDifficultyBeansData= new ArrayList<>();
            List<BarChartBlockTxnCountData> barChartBlockTxnCountDataData = new ArrayList<>();

            //parse the data to the appropriate beans
            for(BlockInfoEntity entity: blockInfoEntityList){

                BarChartBlockDifficultyData blockDifficultyBean= new BarChartBlockDifficultyData();
                BarChartBlockTxnCountData blockTxnBean= new BarChartBlockTxnCountData();

                blockDifficultyBean.setBlockNo(entity.getNumber());
                blockDifficultyBean.setDifficultyLevel(Integer.parseInt(entity.getDifficulty()));
                barChartBlockDifficultyBeansData.add(blockDifficultyBean);

                blockTxnBean.setBlockNo(entity.getNumber());
                blockTxnBean.setTxnCount(Integer.parseInt(entity.getTransactionSize()));
                barChartBlockTxnCountDataData.add(blockTxnBean);
            }

            //set data of blockDifficultyBarChart
            blockDifficultyBarChart.setData(barChartBlockDifficultyBeansData.toArray());
            blockDifficultyBarChart.setXkey(BarChartBlockDifficultyData.getXKey());
            blockDifficultyBarChart.setYkeys(BarChartBlockDifficultyData.getYKeys());
            blockDifficultyBarChart.setLabels(BarChartBlockDifficultyData.getLabels());
            blockDifficultyBarChart.setHideHover("auto");
            blockDifficultyBarChart.setResize(true);

            //set data of blockTxnBarChart
            blockTxnBarChart.setData(barChartBlockTxnCountDataData.toArray());
            blockTxnBarChart.setXkey(BarChartBlockTxnCountData.getXKey());
            blockTxnBarChart.setYkeys(BarChartBlockTxnCountData.getYKeys());
            blockTxnBarChart.setLabels(BarChartBlockTxnCountData.getLabels());
            blockTxnBarChart.setHideHover("auto");
            blockTxnBarChart.setResize(true);

            //add to bar char list
            barChartBeanList.add(blockDifficultyBarChart);
            barChartBeanList.add(blockTxnBarChart);

            response.setStatusCode("00");
            response.setStatusValue("OK");

            response.setData(barChartBeanList);

        }catch (Exception e) {
            response.setStatusCode("99");
            response.setStatusValue("Error:"+e.getMessage());
        }
        return AppUtils.convertToJson(response);
    }

}
