package com.abs.dao;

import com.abs.entity.BlockInfoEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BlockInfoDaoImpl implements BlockInfoDaoApi {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createBlockInfo(BlockInfoEntity entity) {
        this.sessionFactory.getCurrentSession().save(entity);
    }

    public List<BlockInfoEntity> fetchBlockInfoSet() {
        String query= new StringBuilder().append("from BlockInfoEntity as we ").append("").toString();
        Query hQuery = this.sessionFactory.getCurrentSession().createQuery(query);
        hQuery.setFirstResult(0);
        hQuery.setMaxResults(10);
        return hQuery.list();
    }

}
