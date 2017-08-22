package com.dh.service.system;

import com.dh.common.CommonButil;
import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.Banner;
import com.dh.repository.BannerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BannerService {

	@Autowired
	private BannerDao bannerDao;

	/**
	 * 分页查询banner列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@SystemServiceLog(description = "查询幻灯片列表")
	public Page<Banner> findBannerList(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = buildBannerListPageRequest(pageNumber, pageSize);
		// Specification<News> spe = buildBannerListSpecification(newsTitle);
		return bannerDao.findAll(pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildBannerListPageRequest(int pageNumber, int pageSize) {
		Sort sort = new Sort(Sort.Direction.DESC, "isShow").and(new Sort(Sort.Direction.DESC, "orderBy"))
				.and(new Sort(Sort.Direction.DESC, "createTime"));
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	/**
	 * 根据id查找banner
	 * 
	 * @param id
	 * @return
	 */
	@SystemServiceLog(description = "查询幻灯片")
	public Banner findBannerById(Long id) {
		return bannerDao.findOne(id);
	}

	/**
	 * 保存banner
	 * 
	 * @param banner
	 * @return
	 */
	public Banner save(Banner banner) {
		banner.setUpdateTime(CommonButil.getNowTime());
		return bannerDao.save(banner);
	}

	/**
	 * 获取已展示banner的数量
	 * 
	 * @return
	 */
	@SystemServiceLog(description = "查询已展示的幻灯片数量")
	public Integer findBannerShowCount() {
		return bannerDao.findBannerShowCount();
	}

	/**
	 * 删除banner
	 * 
	 * @param id
	 */
	@SystemServiceLog(description = "删除幻灯片")
	public void delete(Long id) {
		bannerDao.delete(id);
	}

	/**
	 * 查询出有该新闻关联的banner数
	 * 
	 * @param newsId
	 * @return
	 */
	@SystemServiceLog(description = "查询新闻关联幻灯片的数量")
	public Integer findBannerCountByNewsId(Long newsId) {
		return bannerDao.findBannerCountByNewsId(newsId);
	}

}
