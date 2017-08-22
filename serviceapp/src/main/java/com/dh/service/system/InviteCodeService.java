package com.dh.service.system;

import java.util.HashMap;
import java.util.Map;

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

import com.dh.common.CommonButil;
import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.InviteCode;
import com.dh.entity.User;
import com.dh.repository.InviteCodeDao;

@Component
@Transactional
public class InviteCodeService {

	@Autowired
	private InviteCodeDao inviteCodeDao;
	
	/**
	 * 分页查询邀请码列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@SystemServiceLog(description = "查询邀请码列表")
	public Page<InviteCode> findInviteCodeList(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = buildInviteCodeListPageRequest(pageNumber, pageSize);
		Specification<InviteCode> spe = buildInviteCodeListSpecification("");
		return inviteCodeDao.findAll(spe, pageRequest);
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildInviteCodeListPageRequest(int pageNumber, int pageSize) {
		Sort sort = new Sort(Sort.Direction.DESC, "createTime");
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	/**
	 * 设置查询邀请码列表的条件
	 * 
	 * @param type
	 * @return
	 */
	private Specification<InviteCode> buildInviteCodeListSpecification(String type) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		if (!CommonButil.isEmpty(type)) {
			filters.put("type", new SearchFilter("type", Operator.LIKE, type));
		}
		Specification<InviteCode> spec = DynamicSpecifications.bySearchFilter(filters.values(), InviteCode.class);
		return spec;
	}
	
	/**
	 * 生成邀请码
	 * @param user
	 * @return
	 */
	@SystemServiceLog(description = "生成邀请码")
	public InviteCode createInviteCode(User user) {
		InviteCode ic = new InviteCode();
		ic.setCreateTime(CommonButil.getNowTime());
		ic.setFromUser(user);
		ic.setIsUse(0);
		ic.setCodeNum(createCode());
		inviteCodeDao.save(ic);
		return ic;
	}
	
	/**
	 * 生成邀请码编号
	 * @return
	 */
	public String createCode(){
		String code = CommonButil.getRandomNum(Consts.INVITE_CODE_LENGTH);
		if(!CommonButil.isEmpty(inviteCodeDao.getInviteCode(code))){
			createCode();
		}
		return code;
	}
	
	@SystemServiceLog(description = "获取邀请码")
	public InviteCode findOne(Long id) {
		return inviteCodeDao.findOne(id);
	}
	
	@SystemServiceLog(description = "删除邀请码")
	public void delete(Long id) {
		inviteCodeDao.delete(id);
	}
	
	
	
	
}
