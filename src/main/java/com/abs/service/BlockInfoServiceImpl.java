package com.abs.service;

import com.abs.dao.BlockInfoDaoApi;
import com.abs.entity.BlockInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public class BlockInfoServiceImpl implements BlockInfoServiceApi {

    @Autowired
    private BlockInfoDaoApi blockInfoDao;

    @Transactional
    @Override
    public void createBlockInfo(BlockInfoEntity entity) {
        blockInfoDao.createBlockInfo(entity);
    }

    @Transactional
    @Override
    public List<BlockInfoEntity> fetchBlockInfoSet() {
        return blockInfoDao.fetchBlockInfoSet();
    }

}
