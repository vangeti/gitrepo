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

import cgt.dop.model.DashboardUserWidgetMapping;
import cgt.dop.model.DashboardWidgetRoleMappingModel;
import cgt.dop.model.WidgetModel;

@Repository
public class DashboardUserWidgetMappingDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public JSONObject getDashboardWidgetsByUser(Object User_id) throws JSONException{
		Map<Integer, List> data = new LinkedHashMap<Integer, List>();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from DashboardUserWidgetMapping dwr where dwr.user_id=" + User_id);
		List<Object> list = new ArrayList();
		List dashboardWidgetUserMappingModels = query.list();
		JSONObject widget = new JSONObject();
		for (Iterator iterator = dashboardWidgetUserMappingModels.iterator(); iterator.hasNext();) {
			

			DashboardUserWidgetMapping dashboardWidgetRoleMappingModel = (DashboardUserWidgetMapping) iterator
					.next();

			WidgetModel widgetModel = (WidgetModel) session
					.createQuery("from WidgetModel where widget_div_id='" + dashboardWidgetRoleMappingModel.getWidget_id()+"'")
					.uniqueResult();
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", widgetModel.getWidget_div_id());
			map.put("title", widgetModel.getWidget_name());
			/*map.put("widgetid", String.valueOf(widgetModel.getId()));*/
			widget.append(widgetModel.getWidget_div_id(), map);
			/*list.add(widgetModel.getWidget_div_id());
			list.add(widgetModel.getWidget_name());*/

		}
		System.out.println("ggggggggggggggggg "+widget.toString());
		session.close();
		return widget;
		
	}
	
	
	public void addWidgetForHide(int user_id, String widget_id) {
		  Session session = sessionFactory.openSession();
		  org.hibernate.Transaction tx = session.beginTransaction();
		  DashboardUserWidgetMapping  dashboardUserWidgetMapping = new DashboardUserWidgetMapping();

		  dashboardUserWidgetMapping.setUser_id(user_id);
		  dashboardUserWidgetMapping.setWidget_id(widget_id);

		  session.save(dashboardUserWidgetMapping);
		  tx.commit();
		  session.close();

		  System.out.println("data saved successfully");

		 }
	
	public void deleteWidgetForDisplay(int user_id, String widget_id) {
		Session session = sessionFactory.openSession();
		  org.hibernate.Transaction tx=session.beginTransaction();
		  String hql = "DELETE FROM DashboardUserWidgetMapping "+
		    "WHERE user_id = :user_id and widget_id = :widget_id";
		    Query query =  session.createQuery(hql);
		    query.setParameter("user_id", user_id);
		    query.setParameter("widget_id", widget_id);
		    int result =  query.executeUpdate();
		    System.out.println("Rows Affected: " + result);
		    
		    
		//  session.delete(id);
		  tx.commit();
		  session.close();
		  
		  System.out.println("data deleted successfully");

		 }
	

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
	
	
	public Map<Object, List<List<Object>>> getDashboardWidgetsByRoleDemo(Object role_id){
		Map<Integer, List> data = new LinkedHashMap<Integer, List>();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from DashboardWidgetRoleMappingModel dwr where dwr.role_id=" + role_id);

		Map<Object, List<List<Object>>> map = new HashMap<Object, List<List<Object>>>();
		List<List<Object>> lists=new ArrayList();
		
		List dashboardWidgetRoleMappingModels = query.list();
		for (Iterator iterator = dashboardWidgetRoleMappingModels.iterator(); iterator.hasNext();) {
			List<Object> list = new ArrayList();

			DashboardWidgetRoleMappingModel dashboardWidgetRoleMappingModel = (DashboardWidgetRoleMappingModel) iterator
					.next();

			list.add(dashboardWidgetRoleMappingModel.getWidget_id());
			list.add(dashboardWidgetRoleMappingModel.getRole_id());
/*
			RoleModel role = (RoleMode) session
					.createQuery("from RoleModel where user_role_id=" + dashboardWidgetRoleMappingModel.getRole_id())
					.uniqueResult();*/
			//list.add(role.getUser_role_name());

			WidgetModel widgetModel = (WidgetModel) session
					.createQuery("from WidgetModel where id=" + dashboardWidgetRoleMappingModel.getWidget_id())
					.uniqueResult();
			list.add(widgetModel.getWidget_name());
			list.add(widgetModel.getWidget_div_id());

			lists.add(list);

		}
		map.put(role_id, lists);
		session.close();

		System.out.println(map);
		return map;
		
		
	}


}
