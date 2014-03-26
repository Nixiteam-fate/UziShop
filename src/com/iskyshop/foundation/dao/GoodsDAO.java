package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Goods;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("goodsDAO")
public class GoodsDAO extends GenericDAO<Goods> {
}