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
        this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    public List<BlockInfoEntity> fetchBlockInfoSet() {
        String queryCount= new StringBuilder().append("select count(*) from BlockInfoEntity as we ").append("").toString();
        long hQueryCount = (long) this.sessionFactory.getCurrentSession().createQuery(queryCount).uniqueResult();

        String query= new StringBuilder().append("from BlockInfoEntity as we ").append("").toString();
        Query hQuery = this.sessionFactory.getCurrentSession().createQuery(query);
        hQuery.setFirstResult((int)hQueryCount - 10);
        hQuery.setMaxResults((int)hQueryCount);
        return hQuery.list();
    }

}
