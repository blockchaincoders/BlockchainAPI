package com.abs.service;

import com.abs.entity.BlockInfoEntity;
import java.util.List;

public interface BlockInfoServiceApi {

    void createBlockInfo(BlockInfoEntity entity);
    List<BlockInfoEntity> fetchBlockInfoSet();
}
