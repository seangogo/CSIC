package com.dh.service.system;

import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.CodeNum;
import com.dh.repository.CodeNumDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Transactional
public class CodeNumService {

	@Autowired
	private CodeNumDao codeNumDao;

	/**
	 * 根据标示获取编号
	 * @param codeParam
	 * @return “1”
	 */
	@SystemServiceLog(description = "查询指定标示的编号")
	public Long createCodeNum(String codeParam) {
		// 获取该标示的code对象
		CodeNum code = codeNumDao.findByCodeParam(codeParam);
		String timeStr = new SimpleDateFormat("yyyyMM").format(new Date());
		// 若code不存在则根据标示 新建一个
		if (null == code) {
			CodeNum c = new CodeNum();
			c.setCodeParam(codeParam);
			c.setCodeNum(1L);
			c.setCodeTime(timeStr);
			codeNumDao.save(c);
			return 1L;
		} else {
			// code存在 
			//则判断codeTime 是否是本月 若不是：则修改codeTime为本月时间 重置编码为1
			if (!timeStr.equals(code.getCodeTime())) {
				code.setCodeTime(timeStr);
				code.setCodeNum(1L);
				codeNumDao.save(code);
				return 1L;
			} else {
				//是本月 获取code编码 +1 
				Long num = code.getCodeNum()+1;
				code.setCodeNum(num);
				codeNumDao.save(code);
				return num;
			}
		}
	}

}
