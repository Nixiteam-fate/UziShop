/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service.impl;

import com.iskyshop.core.dao.IGenericDAO;
import com.iskyshop.core.query.GenericPageList;
import com.iskyshop.core.query.PageObject;
import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.StorePartner;
import com.iskyshop.foundation.service.IStorePartnerService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StorePartnerServiceImpl implements IStorePartnerService {

    @Resource(name = "storePartnerDAO")
    private IGenericDAO<StorePartner> storePartnerDao;

    public boolean save(StorePartner storePartner) {
        try {
            this.storePartnerDao.save(storePartner);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public StorePartner getObjById(Long id) {
        StorePartner storePartner = (StorePartner) this.storePartnerDao.get(id);
        if (storePartner != null) {
            return storePartner;
        }
        return null;
    }

    public boolean delete(Long id) {
        try {
            this.storePartnerDao.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean batchDelete(List<Serializable> storePartnerIds) {
        for (Serializable id : storePartnerIds) {
            delete((Long) id);
        }
        return true;
    }

    public IPageList list(IQueryObject properties) {
        if (properties == null) {
            return null;
        }
        String query = properties.getQuery();
        Map params = properties.getParameters();
        GenericPageList pList = new GenericPageList(StorePartner.class, query, params, this.storePartnerDao);
        if (properties != null) {
            PageObject pageObj = properties.getPageObj();
            if (pageObj != null)
                pList.doList((pageObj.getCurrentPage() == null) ? 0 : pageObj.getCurrentPage().intValue(),
                        (pageObj.getPageSize() == null) ? 0 : pageObj.getPageSize().intValue());
        } else {
            pList.doList(0, -1);
        }
        return pList;
    }

    public boolean update(StorePartner storePartner) {
        try {
            this.storePartnerDao.update(storePartner);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<StorePartner> query(String query, Map params, int begin, int max) {
        return this.storePartnerDao.query(query, params, begin, max);
    }
}