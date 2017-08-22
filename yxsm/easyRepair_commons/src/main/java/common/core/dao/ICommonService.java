package common.core.dao;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ICommonService<T> {
     List<T> findAll();

     Page<T> find(int pageNum, int pageSize);

     Page<T> find(int pageNum);

     T getById(long id);

     T deleteById(long id);

     T create(T t);

     T update(T t);

     void deleteAll(long[] ids);
}
