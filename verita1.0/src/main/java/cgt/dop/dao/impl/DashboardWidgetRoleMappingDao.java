package cgt.dop.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.model.DashboardWidgetRoleMappingModel;
import cgt.dop.model.WidgetModel;

@Repository
public class DashboardWidgetRoleMappingDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public JSONObject getDashboardWidgetsByRole(Object role_id,Object project_id) throws JSONException{
		Map<Integer, List> data = new LinkedHashMap<Integer, List>();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from DashboardWidgetRoleMappingModel dwr where dwr.role_id=" + role_id+" and dwr.project_id="+project_id);
		List<Object> list = new ArrayList();
		List dashboardWidgetRoleMappingModels = query.list();
		JSONObject widget = new JSONObject();
		for (Iterator iterator = dashboardWidgetRoleMappingModels.iterator(); iterator.hasNext();) {
			

			DashboardWidgetRoleMappingModel dashboardWidgetRoleMappingModel = (DashboardWidgetRoleMappingModel) iterator
					.next();
			System.out.println("from WidgetModel where id='" + dashboardWidgetRoleMappingModel.getWidget_id()+"'");
			WidgetModel widgetModel = (WidgetModel) session
					.createQuery("from WidgetModel where id='" + dashboardWidgetRoleMappingModel.getWidget_id()+"'")
					.uniqueResult();
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", widgetModel.getWidget_div_id());
			map.put("title", widgetModel.getWidget_name());
			/*map.put("widgetid", String.valueOf(widgetModel.getId()));*/
			
			widget.append(widgetModel.getWidget_div_id(), map);
			/*list.add(widgetModel.getWidget_div_id());
			list.add(widgetModel.getWidget_name());*/

		}
		System.out.println(widget.toString());
		session.close();
		return widget;
		
	}
	/*
	public void deleteWidgetByUser(int user_id,int widget_id) throws JSONException{
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("delete from DashboardWidgetRoleMappingModel dwr where dwr.user_id=" + role_id);
	}*/
	
	

/*	public List<Object> getDashboardWidgets(Object role_id){
		Map<Integer, List> data = new LinkedHashMap<Integer, List>();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from DashboardWidgetRoleMappingModel dwr where dwr.role_id=" + role_id);
		List<Object> list = new ArrayList<>();
		List dashboardWidgetRoleMappingModels = query.list();
		for (Iterator iterator = dashboardWidgetRoleMappingModels.iterator(); iterator.hasNext();) {
			

			DashboardWidgetRoleMappingModel dashboardWidgetRoleMappingModel = (DashboardWidgetRoleMappingModel) iterator
					.next();

			WidgetModel widgetModel = (WidgetModel) session
					.createQuery("from WidgetModel where id=" + dashboardWidgetRoleMappingModel.getWidget_id())
					.uniqueResult();
			
			list.add(widgetModel.getWidget_div_id());

		}
		session.close();
		return list;
		
	}*/
	
	



}
