package com.abs.dao;

import com.abs.entity.BlockInfoEntity;

import java.util.List;

public interface BlockInfoDaoApi {

    void createBlockInfo(BlockInfoEntity entity);
    List<BlockInfoEntity> fetchBlockInfoSet();
}
