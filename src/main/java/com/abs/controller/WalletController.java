package com.abs.controller;

import com.abs.entity.AreaChartBean;
import com.abs.entity.AreaChartMobileData;
import com.abs.entity.BlockInfoBean;
import com.abs.entity.WalletEntity;
import com.abs.service.WalletServiceApi;
import com.abs.utils.AppUtils;
import com.abs.utils.Constant;
import com.abs.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Controller
public class WalletController {

    @Autowired
    private WalletServiceApi walletService;

    private static final int COUNT = 10;
    private Web3j web3j;


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/createWallet", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String createWallet(HttpServletRequest request, String walletAlias, String passphrase)
    {
        Response response = new Response();

        try {

            Integer idCustomer = (Integer) request.getSession(false).getAttribute(Constant.ID_CUSTOMER_KEY);
            //Integer idCustomer = (Integer) request.getAttribute(Constant.ID_CUSTOMER_KEY);
            System.out.println(idCustomer);
//            long idCustomer =(long) request.getSession().getAttribute(Constant.ID_CUSTOMER_KEY);
            String fileNameOrg = createNewWalletAccount(walletAlias,passphrase,idCustomer);

            //create wallet entity
            WalletEntity entity = new WalletEntity();
            entity.setWalletAlias(walletAlias);
            entity.setWalletPassphrase(passphrase);
            String fileName = fileNameOrg.substring(fileNameOrg.lastIndexOf("-") + 1, fileNameOrg.lastIndexOf(".json"));

            entity.setWalletAddress(fileName);
            entity.setWalletFileName(fileNameOrg);
            entity.setIdCustomer(idCustomer);
            //entity creation ends

            walletService.createWallet(entity);

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
    @RequestMapping(value = "/fetchWallets", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String fetchWallets(HttpServletRequest request)
    {
        Response response = new Response();
        try {
            //todo fetch customer id from session
            Integer idCustomer =(Integer) request.getSession(false).getAttribute(Constant.ID_CUSTOMER_KEY);
            List<WalletEntity> walletEntityList = walletService.fetchAllCustomerWallets(idCustomer);

            response.setDataList(walletEntityList);
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
    @RequestMapping(value = "/fetchBalance", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String fetchBalance(String walletAddress)
    {
        Response response = new Response();
        try {

            EthGetBalance ethGetBalance = getEtherBalanceOfWallet(walletAddress);
            BigInteger value = ethGetBalance.getBalance();
            response.setData(Convert.fromWei(String.valueOf(value), Convert.Unit.ETHER) + " Ether (" + value + " Wei)");

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
    @RequestMapping(value = "/fetchEthCoinBase", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String fetchEthCoinBase()
    {
        Response response = new Response();
        try {
            EthCoinbase ethCoinbase = web3j.ethCoinbase().send();
            response.setData(ethCoinbase.getAddress());

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
    @RequestMapping(value = "/transferFunds", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String transferFunds(String fromWallet, String toWallet, Double amount)
    {
        Response response = new Response();
        try {

            WalletEntity walletEntity = walletService.fetchWalletDetails(fromWallet);
            WalletEntity toWalletEntity = walletService.fetchWalletDetails(toWallet);

            String path = "D:\\BlockChain\\WalletFiles\\"+ walletEntity.getWalletFileName();
            String walletPassphrase = walletEntity.getWalletPassphrase();

            TransactionReceipt transactionReceipt = doTransaction(walletPassphrase, path, fromWallet, toWalletEntity.getWalletAddress(), amount);
            response.setData(transactionReceipt);

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
    @RequestMapping(value = "/getBlockInfo", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String getBlockInfo()
    {
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

            List<EthBlock.TransactionResult>  transactionResults = block.getTransactions();
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
        return AppUtils.convertToJson(response);
    }




    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/fetchGraphData", method = { RequestMethod.POST }, produces = Constant.APPLICATION_JSON)
    public String chartData(){

        AreaChartBean bean = new AreaChartBean();

        bean.setElement("morris-area-chart");//todo this element id can be set from angularjs which is a proper method to set chart div

        AreaChartMobileData data[]= new AreaChartMobileData[10];
        AreaChartMobileData info1= new AreaChartMobileData();
        info1.setPeriod("2010 Q1");
        info1.setIphone(2666);
        info1.setIpad(null);
        info1.setItouch(2647);

        AreaChartMobileData info2= new AreaChartMobileData();
        info2.setPeriod("2010 Q2");
        info2.setIphone(2778);
        info2.setIpad(2294);
        info2.setItouch(2441);

        AreaChartMobileData info3= new AreaChartMobileData();
        info3.setPeriod("2010 Q3");
        info3.setIphone(4912);
        info3.setIpad(1969);
        info3.setItouch(2501);


        AreaChartMobileData info4=new AreaChartMobileData();
        info4.setPeriod("2010 Q4");
        info4.setIphone(3767);
        info4.setIpad(3597);
        info4.setItouch(5689);


        AreaChartMobileData info5= new AreaChartMobileData();
        info5.setPeriod("2011 Q1");
        info5.setIphone(6810);
        info5.setIpad(1914);
        info5.setItouch(2293);

        AreaChartMobileData info6= new AreaChartMobileData();
        info6.setPeriod("2011 Q2");
        info6.setIphone(5670);
        info6.setIpad(4293);
        info6.setItouch(1881);

        AreaChartMobileData info7= new AreaChartMobileData();
        info7.setPeriod("2011 Q3");
        info7.setIphone(4820);
        info7.setIpad(3795);
        info7.setItouch(1588);

        AreaChartMobileData info8= new AreaChartMobileData();
        info8.setPeriod("2011 Q4");
        info8.setIphone(15073);
        info8.setIpad(5967);
        info8.setItouch(5175);


        AreaChartMobileData info9= new AreaChartMobileData();
        info9.setPeriod("2012 Q1");
        info9.setIphone(10687);
        info9.setIpad(4460);
        info9.setItouch(2028);


        AreaChartMobileData info10= new AreaChartMobileData();
        info10.setPeriod("2012 Q2");
        info10.setIphone(8432);
        info10.setIpad(5713);
        info10.setItouch(1791);

        data[0]=info1;
        data[1]=info2;
        data[2]=info3;
        data[3]=info4;
        data[4]=info5;
        data[5]=info6;
        data[6]=info7;
        data[7]=info8;
        data[8]=info9;
        data[9]=info10;

        bean.setData(data);
        bean.setXkey(AreaChartMobileData.getXKey());
        bean.setYkeys(AreaChartMobileData.getYKeys());
        bean.setLabels(AreaChartMobileData.getLabels());
        bean.setPointSize(2);
        bean.setHideHover("auto");
        bean.setResize(true);

        Response response = new Response();

        response.setStatusCode("00");
        response.setStatusValue("OK");
        response.setData(bean);
        return AppUtils.convertToJson(response);

    }


    /*public Main() {

    }*/

    /*private void run() throws Exception {
        //simpleFilterExample();
        //blockInfoExample();
        //countingEtherExample();
        //clientVersionExample();
        //getWeiBalanceOfWallet("0x3749c12f81c188665735e0a87ec0026f637f8f13");

        //getEtherBalanceOfWallet("0x4f6010998355b67f4fa5541e70a9d1b273bbb2c2");
        createNewWalletAccount("Lahore123456");

        //getEtherBalanceOfWallet("0x3749c12f81c188665735e0a87ec0026f637f8f13");
        //getEtherBalanceOfWallet("0x7370ced7bbbfe3424128a8b94cabeb77b883b33b");

        //System.out.println("-------------------------------------");
        //transferFunds("Lahore123456", "D:\\BlockChain\\BlockchainUI\\WalletFiles\\UTC--2017-07-18T08-08-46.755000000Z--3749c12f81c188665735e0a87ec0026f637f8f13.json", "0x3749c12f81c188665735e0a87ec0026f637f8f13", "0x7370ced7bbbfe3424128a8b94cabeb77b883b33b");
        //System.out.println("-------------------------------------");

        //getEtherBalanceOfWallet("0x3749c12f81c188665735e0a87ec0026f637f8f13");
        //getEtherBalanceOfWallet("0x7370ced7bbbfe3424128a8b94cabeb77b883b33b");
    }*/

    public TransactionReceipt doTransaction(String password, String path, String fromWallet, String toWallet, Double amount) throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        Credentials credentials = WalletUtils.loadCredentials(password, path);

        TransactionReceipt transactionReceipt = Transfer.sendFunds(
                web3j, credentials, toWallet, BigDecimal.valueOf(amount), Convert.Unit.ETHER);
        System.out.println("transactionReceipt.getBlockNumber(): "+ transactionReceipt.getBlockNumber());
        System.out.println("transactionReceipt.getBlockHash(): "+ transactionReceipt.getBlockHash());
        System.out.println("transactionReceipt.getBlockNumberRaw(): "+ transactionReceipt.getBlockNumberRaw());
        System.out.println("transactionReceipt.getContractAddress(): "+ transactionReceipt.getContractAddress());
        System.out.println("transactionReceipt.getCumulativeGasUsedRaw(): "+ transactionReceipt.getCumulativeGasUsedRaw());
        System.out.println("transactionReceipt.getGasUsedRaw(): "+ transactionReceipt.getGasUsedRaw());
        System.out.println("transactionReceipt.getFrom(): "+ transactionReceipt.getFrom());
        System.out.println("transactionReceipt.getTo(): "+ transactionReceipt.getTo());
        System.out.println("transactionReceipt.getTransactionIndex(): "+ transactionReceipt.getTransactionIndex());

        return transactionReceipt;

/*        // get the next available nonce
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount("0x3749c12f81c188665735e0a87ec0026f637f8f13", DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        // create our transaction
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, BigInteger.ZERO, BigInteger.valueOf(1), toWallet, BigInteger.valueOf(20));

        // sign & send our transaction
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Hex.toHexString(signedMessage);
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction("0x" + hexValue).sendAsync().get();
        System.out.println("Transfer Funds:" + ethSendTransaction.getJsonrpc() + "<===>" + ethSendTransaction.getResult());*/
    }

    private String createNewWalletAccount(String walletAlias,String passphrase , long idCustomer) throws Exception {
        String fileNameOrg = WalletUtils.generateNewWalletFile(passphrase, new File("D:\\BlockChain\\WalletFiles"), true);
        System.out.println(fileNameOrg);
        return fileNameOrg;
    }

    public EthGetBalance getEtherBalanceOfWallet(String walletAddress) throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        EthGetBalance ethGetBalance = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        return ethGetBalance;
    }

/*    public void getWeiBalanceOfWallet(String walletAddress) throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        EthGetBalance ethGetBalance = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        System.out.println("Wei Balance:" + ethGetBalance.getBalance());
    }*/

    /*public static void main(String[] args) throws Exception {
        new Main().run();
    }*/

/*    void simpleFilterExample() throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        Subscription subscription = web3j.blockObservable(false).subscribe(block -> {
            System.out.println("Sweet, block number " + block.getBlock().getNumber() + " has just been created");
        }, Throwable::printStackTrace);

        TimeUnit.MINUTES.sleep(2);
        subscription.unsubscribe();
    }

    void blockInfoExample() throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        CountDownLatch countDownLatch = new CountDownLatch(COUNT);

        System.out.println("Waiting for " + COUNT + " transactions...");
        Subscription subscription = web3j.blockObservable(true)
                .take(COUNT)
                .subscribe(ethBlock -> {
                    EthBlock.Block block = ethBlock.getBlock();
                    LocalDateTime timestamp = Instant.ofEpochSecond(
                            block.getTimestamp().longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime();
                    int transactionCount = block.getTransactions().size();
                    String hash = block.getHash();
                    String parentHash = block.getParentHash();

                    System.out.println(
                            timestamp + " " +
                                    "Tx count: " + transactionCount + ", " +
                                    "Hash: " + hash + ", " +
                                    "Parent hash: " + parentHash
                    );
                    countDownLatch.countDown();
                }, Throwable::printStackTrace);

        countDownLatch.await(10, TimeUnit.MINUTES);
        subscription.unsubscribe();
    }

    void countingEtherExample() throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        CountDownLatch countDownLatch = new CountDownLatch(1);

        System.out.println("Waiting for " + COUNT + " transactions...");
        Observable<BigInteger> transactionValue = web3j.transactionObservable()
                .take(COUNT)
                .map(Transaction::getValue)
                .reduce(BigInteger.ZERO, BigInteger::add);

        Subscription subscription = transactionValue.subscribe(total -> {
            BigDecimal value = new BigDecimal(total);
            System.out.println("Transaction value: " +
                    Convert.fromWei(value, Convert.Unit.ETHER) + " Ether (" + value + " Wei)");
            countDownLatch.countDown();
        }, Throwable::printStackTrace);

        countDownLatch.await(10, TimeUnit.MINUTES);
        subscription.unsubscribe();
    }

    void clientVersionExample() throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Subscription subscription = web3j.web3ClientVersion().observable().subscribe(x -> {
            System.out.println(x.getWeb3ClientVersion());
            countDownLatch.countDown();
        });

        countDownLatch.await();
        subscription.unsubscribe();
    }*/
}
