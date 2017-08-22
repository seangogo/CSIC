package com.dh.service.system.socket;

import com.sunwayland.util.likong.ef21ws.*;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import java.math.BigInteger;
import java.util.*;

public class Ef21WsApi implements AutoCloseable {
	

	private String host;
	
	private int port;
	
	private String dbCommAddress;
	
	private int dbCommPort;
	
	private DbCommServicePortType service;
	
	private BigInteger handle;
	
	private Map<String, Tagparid> pointNameIds = new HashMap<>();
	
	public Ef21WsApi(String host, int port, String dbCommAddress, int dbCommPort) {
		this.host = host;
		this.port = port;
		this.dbCommAddress = dbCommAddress;
		this.dbCommPort = dbCommPort;
	}

	public boolean connect(long timeout) throws Exception {
		DbCommService db = new DbCommService();
		service = db.getDbCommService();
		((BindingProvider)service).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://" + host + ":" + port);
		handle = service.createDbCommHandle(dbCommAddress, dbCommPort);
		Thread.sleep(timeout);
		Integer status = service.getDbCommStatus(handle);
		this.getAllPointNameAndDesc();
		return status != null && status.intValue() == 0;
	}

	public boolean isConnected() throws Exception {
		Integer status = service.getDbCommStatus(handle);
		return status != null && status.intValue() == 0;
	}

	public Map<String, String> getAllPointNameAndDesc() throws Exception {
		pointNameIds.clear();
		
		Taginfoarray tags = service.getAllDbTagInfo(handle);
		if (tags != null && tags.getItem() != null && tags.getItem().size() > 0) {
			Map<String, String> map = new LinkedHashMap<>();
			Tagparnamearray names = new Tagparnamearray();
			for (Taginfo tag : tags.getItem()) {
				String name = tag.getSzTagName().getValue();
				names.getItem().add(name);
				map.put(name, null);
			}

			Tagparidarray ids = service.getDbTagParNo(handle, names);
			int i = 0;
			for (Tagparid id : ids.getItem()) {
				pointNameIds.put(names.getItem().get(i), id);
				i++;
			}
			System.out.println("#########获取结果为："+CommUtils.getParamJson(map));
			return map;
		} else {
			return null;
		}
	}

	public double getPointValue(String pointName) throws Exception {
		return this.getPointValues(Arrays.asList(pointName)).get(pointName);
	}

	public Map<String, Double> getPointValues(Collection<String> pointNames)
			throws Exception {
		Tagparidarray ids = new Tagparidarray();
		for (String pointName : pointNames) {
			ids.getItem().add(pointNameIds.get(pointName));
		}
		Realtimedataarray par = service.getDbTagParData(handle, ids);
		if (par != null && par.getItem() != null && par.getItem().size() > 0) {
			int i = 0;
			Map<String, Double> map = new LinkedHashMap<>();
			for (String pointName : pointNames) {
				Realtimedata vv = par.getItem().get(i);
				Double v = vv.getDbVal();
				map.put(pointName, v);
				i++;
			}
			System.out.println("###########根据名称组成key-value结果为："+CommUtils.getParamJson(map));
			return map;
		} else {
			return null;
		}
	}
	
	public Map<String, Map<Date, Double>> getHistoryValues(String[] pointNames,
                                                           Date start, Date end) throws Exception {
		XMLGregorianCalendar time1 = null;
		if (start != null) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(start);
			time1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		}
		XMLGregorianCalendar time2 = null;
		if (end != null) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(end);
			time2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		}
		Map<String, Map<Date, Double>> values = new LinkedHashMap<>();
		for (String pointName : pointNames) {
			Tagparid id = pointNameIds.get(pointName);
			if (id == null) {
				continue;
			}
			Hisdatarawreq req = new Hisdatarawreq();
			req.setLTagId(id.getLTagId());
			req.setShParId(id.getShParId());
			req.setTStartTime(time1);
			req.setTEndTime(time2);
			Hisdatarawarray list = service.getHisDataRaw(handle, req);
			if (list != null && list.getItem() != null && list.getItem().size() > 0) {
				Map<Date, Double> map = new LinkedHashMap<>();
				for (Hisdataraw data : list.getItem()) {
					map.put(data.getTTime().toGregorianCalendar().getTime(), data.getDbVal());
				}
				values.put(pointName, map);
			} else {
				values.put(pointName, null);
			}
		}
		return values;
	}

	public void setPointValue(String pointName, double value) throws Exception {
		Tagpardata data = new Tagpardata();
		data.setEmvaltype(Valtype.VAL_DOUBLE);
		data.setDbVal(value);
		Tagparid id = pointNameIds.get(pointName);
		data.setLTagId(id.getLTagId());
		data.setShParId(id.getShParId());
		Tagpardataarray datas = new Tagpardataarray();
		datas.getItem().add(data);
		service.setDbTagParData(handle, datas);
	}

	@Override
	public void close() throws Exception {
		if (service != null) {
			service.destoryDbCommHandle(handle);
		}
	}
	
	public Map<String, Object> getAllPointNameAndValue() throws Exception {
		Map<String, Object> retMap = new HashMap<>();
		Taginfoarray tags = service.getAllDbTagInfo(handle);
		if (tags != null && tags.getItem() != null && tags.getItem().size() > 0) {
			Tagparnamearray names = new Tagparnamearray();
			for (Taginfo tag : tags.getItem()) {
				names.getItem().add(tag.getSzTagName().getValue());
			}
			retMap.put("names", names);
			System.out.println("#####获取所有的名称为："+CommUtils.getParamJson(names.getItem()));

			Tagparidarray ids = service.getDbTagParNo(handle, names);
			if(ids != null && ids.getItem() != null && ids.getItem().size()>0){
				System.out.println("#####获取所有的名称对应的ID为："+CommUtils.getParamJson(ids.getItem()));
				Realtimedataarray par = service.getDbTagParData(handle, ids);
				
				if(par != null && par.getItem() != null && par.getItem().size()>0){
					retMap.put("par", par);
					System.out.println("#####获取所有的值为："+CommUtils.getParamJson(par.getItem()));
				}else{
					retMap.put("par", null);
					System.out.println("#####获取所有的值为空");
				}
			}else{
				retMap.put("par", null);
				System.out.println("#####获取所有的名称对应的ID为空，所以获取的value也都为空");
			}
			return retMap;
		} else {
			return null;
		}
	}

	public Map<String,List<Double>> getHisData(String[] pointNames, Date startDate, Date endDate, long count) throws Exception {
		this.getAllPointNameAndDesc();
		Map<String,List<Double>> retMap = new HashMap<String,List<Double>>();
		for (String pointName : pointNames) {
			Tagparid id = pointNameIds.get(pointName);
			GregorianCalendar cal1 = new GregorianCalendar();
			cal1.setTime(startDate);
			XMLGregorianCalendar time1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
			GregorianCalendar cal2 = new GregorianCalendar();
			cal2.setTime(endDate);
			XMLGregorianCalendar time2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal2);
			Hisdatareq req = new Hisdatareq();
			req.setTStartTime(time1);
			req.setTEndTime(time2);
			req.setLHisCount(count);
			Tagparidarray parIdArray = new Tagparidarray();
			parIdArray.getItem().add(id);
			Hisdataarray list = service.getHisData(handle, req,parIdArray);
			if (list != null && list.getItem() != null && list.getItem().size() > 0) {
				retMap.put(pointName, list.getItem());
				System.out.println("java243行，当前点位名称："+pointName+"###点位值长度："+list.getItem().size()+"##原始长度："+count);
			} else {
				retMap.put(pointName, null);
				System.out.println("java246行，当前点位名称："+pointName+"###点位值为空");
			}
		}
		return retMap;
	}
}
