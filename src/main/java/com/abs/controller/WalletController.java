package com.abs.controller;

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
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import rx.Observable;
import rx.Subscription;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

            Integer idCustomer = 1;
//            long idCustomer =(long) request.getSession().getAttribute(Constant.ID_CUSTOMER_KEY);
            String walletAddress = createNewWalletAccount(walletAlias,passphrase,idCustomer);

            //create wallet entity
            WalletEntity entity = new WalletEntity();
            entity.setWalletAlias(walletAlias);
            entity.setWalletPassphrase(passphrase);
            entity.setWalletAddress(walletAddress);
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
            Integer idCustomer = 1;
//            long idCustomer =(long) request.getSession().getAttribute(Constant.ID_CUSTOMER_KEY);

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

    public void transferFunds(String password, String path, String fromWallet, String toWallet) throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        Credentials credentials = WalletUtils.loadCredentials(password, path);

        TransactionReceipt transactionReceipt = Transfer.sendFunds(
                web3j, credentials, toWallet, BigDecimal.valueOf(10), Convert.Unit.ETHER);
        System.out.println("transactionReceipt.getBlockNumber(): "+ transactionReceipt.getBlockNumber());
        System.out.println("transactionReceipt.getBlockHash(): "+ transactionReceipt.getBlockHash());
        System.out.println("transactionReceipt.getBlockNumberRaw(): "+ transactionReceipt.getBlockNumberRaw());
        System.out.println("transactionReceipt.getContractAddress(): "+ transactionReceipt.getContractAddress());
        System.out.println("transactionReceipt.getCumulativeGasUsedRaw(): "+ transactionReceipt.getCumulativeGasUsedRaw());
        System.out.println("transactionReceipt.getGasUsedRaw(): "+ transactionReceipt.getGasUsedRaw());
        System.out.println("transactionReceipt.getFrom(): "+ transactionReceipt.getFrom());
        System.out.println("transactionReceipt.getTo(): "+ transactionReceipt.getTo());
        System.out.println("transactionReceipt.getTransactionIndex(): "+ transactionReceipt.getTransactionIndex());


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
//        String fileName = WalletUtils.generateNewWalletFile(passphrase, new File("D:\\BlockChain\\BlockchainUI\\WalletFiles\\"), true);
        //System.out.println(fileName);

        String fileName = "0xadsfjhadslaklumeluxmcliu1324ujdlfaduj1";
        String digitStr = fileName.substring(0,fileName.length()-1);

        return fileName;
    }

    public void getEtherBalanceOfWallet(String walletAddress) throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        EthGetBalance ethGetBalance = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger value = ethGetBalance.getBalance();
        System.out.println("Transaction value: " + Convert.fromWei(String.valueOf(value), Convert.Unit.ETHER) + " Ether (" + value + " Wei)");
    }

    public void getWeiBalanceOfWallet(String walletAddress) throws Exception {

        if(web3j==null) {
            web3j = Web3j.build(new HttpService("http://192.168.18.188:8545"));  // defaults to http://localhost:8545/
        }

        EthGetBalance ethGetBalance = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        System.out.println("Wei Balance:" + ethGetBalance.getBalance());
    }

    /*public static void main(String[] args) throws Exception {
        new Main().run();
    }*/

    void simpleFilterExample() throws Exception {

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
    }
}
