package com.easyRepair.entityUtils;

import java.util.List;

public interface ICommonService<T> {
     List<T> findAll();

     T getById(long id);

     T deleteById(long id);

     T create(T t);

     T update(T t);

     void deleteAll(long[] ids);
}
