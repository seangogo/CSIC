package com.dh.service.system;

import com.dh.common.CommonButil;
import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.User;
import com.dh.entity.WordBook;
import com.dh.repository.WordBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class WordBookService {

	@Autowired
	private WordBookDao wordDao;

	/**
	 * 根据index 和类型查找 对应的value
	 * 
	 * @param index
	 * @param type
	 * @return
	 */
	@SystemServiceLog(description = "字典查询")
	public String findByIndex(Integer index, String type) {
		if (CommonButil.isEmpty(index) || CommonButil.isEmpty(index)) {
			return "";
		}
		String value = wordDao.findByIndex(index, type);
		if (CommonButil.isEmpty(value)) {
			return "";
		}
		return value;
	}
	/**
	 * 根据index 和类型查找 对应的value
	 * 
	 * @param index
	 * @param type
	 * @return
	 */
	@SystemServiceLog(description = "字典查询")
	public String findByIndex(Integer index, String type,Long id) {
		if (CommonButil.isEmpty(index) || CommonButil.isEmpty(index)) {
			return "";
		}
		String value = "";
		if(CommonButil.isEmpty(id)){
			 value = wordDao.findByIndex(index, type);
		}else{
			value = wordDao.findByIndex(index, type,id);
		}
		
		if (CommonButil.isEmpty(value)) {
			return "";
		}
		return value;
	}

	/**
	 * 根据type 获取 index 和value
	 * 
	 * @param type
	 * @return
	 */
	@SystemServiceLog(description = "根据类型查询字典")
	public List<WordBook> findByType(String type) {

		return wordDao.findByType(type);
	}

	public List<WordBook> findWordBookList() {
		return (List<WordBook>) wordDao.findAll();
	}

	/**
	 * 分页查询字典列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@SystemServiceLog(description = "查询字典列表")
	public Page<WordBook> findWordBookList(Integer pageNumber, Integer pageSize, String type) {
		PageRequest pageRequest = buildWordBookListPageRequest(pageNumber, pageSize);
		Specification<WordBook> spe = buildWordBookListSpecification(type);
		return wordDao.findAll(spe, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildWordBookListPageRequest(int pageNumber, int pageSize) {
		Sort sort = new Sort(Sort.Direction.ASC, "type")
				.and(new Sort(Sort.Direction.DESC, "orderBy")
						.and(new Sort(Sort.Direction.ASC, "wordIndex")));
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	/**
	 * 设置查询字典列表的条件
	 * 
	 * @param type
	 * @return
	 */
	private Specification<WordBook> buildWordBookListSpecification(String type) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		if (!CommonButil.isEmpty(type)) {
			filters.put("type", new SearchFilter("type", Operator.LIKE, type));
		}
		Specification<WordBook> spec = DynamicSpecifications.bySearchFilter(filters.values(), WordBook.class);
		return spec;
	}

	/**
	 * 保存字典
	 * @param wordBook
	 * @param user
	 * @return
	 */
	@SystemServiceLog(description = "新增/更新字典")
	public WordBook saveWordBook(WordBook wordBook, User user) {
		wordBook.setUpdater(user);
		wordBook.setUpdateTime(CommonButil.getNowTime());
		return wordDao.save(wordBook);
	}

	/**
	 * 根据id查找字典
	 * @param id
	 * @return
	 */
	@SystemServiceLog(description = "查询字典")
	public WordBook findOne(Long id) {
		return wordDao.findOne(id);
	}

	/**
	 * 删除字典
	 * @param id
	 */
	@SystemServiceLog(description = "查询字典")
	public void delete(Long id) {
		wordDao.delete(id);
	}
}
