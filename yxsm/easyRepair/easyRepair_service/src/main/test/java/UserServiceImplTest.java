import com.easyRepair.dao.DiscountDao;
import com.easyRepair.dao.UserDao;
import com.easyRepair.dao.UserLoginInfoDao;
import com.easyRepair.service.*;
import com.easyRepair.tabEntity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sean on 2017/3/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 注入相关的配置文件：可以写入多个配置文件 **/
@ContextConfiguration(locations="classpath*:applicationContext-dao.xml")
public class UserServiceImplTest {

    @Autowired
    private UserRepairInfoService userRepairInfoService;
    @Autowired
    private UserLoginInfoService userLoginInfoService;
    @Autowired
    private UserLoginInfoDao userLoginInfoDao;
    @Autowired
    private AreasSearchService areasSearchService;

   /* @Test
    public void batchLock() throws Exception {
        List<Long> ids=new ArrayList<Long>();
        ids.add(10L);
        ids.add(11L);
    //  System.out.println(userDao.l(ids));
    }

    @Test
    public void select() throws Exception {
        Double i=discountService.findNumByType(0);
    }

    *//**
     * 服务领域
     * @throws Exception
     *//*
    @Test
    public void serviceAreaService() throws Exception {
        String ids[]={"1","2"};
        List<ServiceArea> serviceAreaList=serviceAreaService.findByIdIn(ids);
        for (ServiceArea serviceArea:serviceAreaList){
            System.out.println(serviceArea);
        }
    }
    *//**
     **/

    /**
     * 推荐工程师
     * @throws Exception
     */
    @Test
    public void userRepair() throws Exception {
        List<Map<String, String>> mapList=userRepairInfoService.findByOrderBySort(114.1222d,30.255d);

    }
    /**
     * 推荐订单
     * @throws Exception
     */
    @Test
    public void order() throws Exception {
      //  List<AreasSearch> list=areasSearchService.indexOrder(114.1222d,30.255d);
        userRepairInfoService.mineAuthentication();
    }

}