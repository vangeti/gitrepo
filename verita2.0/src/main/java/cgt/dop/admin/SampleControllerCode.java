package cgt.dop.admin;
/*package cgt.dop.admin;

import java.util.List;

import jdk.nashorn.internal.objects.annotations.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cgt.dop.dao.ClientDAO;
import cgt.dop.dao.ClientProgramDAO;
import cgt.dop.dao.NotificationDAO;
import cgt.dop.model.Client;
import cgt.dop.model.ClientProgram;
import cgt.dop.model.Notification;
import cgt.dop.rest.uri.constants.EmpRestURIConstants;

*//**
 * Handles requests for the Employee service.
 *//*
@Controller
public class SunCoastController {

	private static final Logger logger = LoggerFactory
			.getLogger(SunCoastController.class);
	ClassPathXmlApplicationContext context = null;

	public SunCoastController() {
		// TODO Auto-generated constructor stub
		context = new ClassPathXmlApplicationContext("spring.xml");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	String getIndex()
	{
		return "index";
	}


	
	 *  Getting All Clients Info
	 
	@CrossOrigin
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_CLIENTS, method = RequestMethod.GET)
	public @ResponseBody List<Client> getClientList(@PathVariable("id") int id,
			@PathVariable("type") String type) {
		System.out.println("aksjdaslldjasjldlasjkd");
		HttpHeaders header=new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		logger.info("Start getClient = " + id);
		ClientDAO clientDAO = context.getBean(ClientDAO.class);
		List<Client> clientList = clientDAO.getClientList(id, type);
		return clientList;
	}

	
	 *  Posting Client Info
	 
	@CrossOrigin
	@RequestMapping(value = EmpRestURIConstants.POST_CLIENT, method = RequestMethod.POST, headers = { "Content-type=application/json" })
	@ResponseBody
	public boolean addPerson(@RequestBody Client client) {
		HttpHeaders header=new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		logger.info("Start post Client = " + client.getId());
		ClientDAO clientDAO = context.getBean(ClientDAO.class);
		boolean clientPost = clientDAO.postClient(client);
		return clientPost;
	}
	
	
	 *  Getting All Notifications Info
	 
	@CrossOrigin
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_NOTIFICATIONS, method = RequestMethod.GET)
	public @ResponseBody List<Notification> getNotificationList(){
		HttpHeaders header=new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		logger.info("Start getAllNotifications.");
		NotificationDAO notificationDAO = context.getBean(NotificationDAO.class);
		return notificationDAO.getNotificationsList();
	}
	
	
	 *  Getting All ClientPrograms Info
	 
	@CrossOrigin
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_CLIENTPROGRAMS, method = RequestMethod.GET)
	public @ResponseBody List<ClientProgram> getClientProgramList(){
		HttpHeaders header=new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		logger.info("Start getClientProgramList.");
		ClientProgramDAO clientProgramDAO = context.getBean(ClientProgramDAO.class);
		return clientProgramDAO.getClientProgramList();
	}
	
	
	 *  Posting ClientProgram Info
	 
	 @CrossOrigin
	 @RequestMapping(value = EmpRestURIConstants.POST_CLIENTPROGRAM, method = RequestMethod.POST, headers = { "Content-type=application/json" })
	 @ResponseBody
	 public boolean addClientProgram(@RequestBody ClientProgram clientProgram) {
	  HttpHeaders header=new HttpHeaders();
	  System.out.println("client ID: "+clientProgram.getClientId());
	  header.add("Access-Control-Allow-Origin", "*");
	  logger.info("Start post Client = " + clientProgram.getId());
	  ClientProgramDAO clientProgramDAO = context.getBean(ClientProgramDAO.class);
	  boolean clientProgramPost = clientProgramDAO.postClientProgram(clientProgram);
	  return clientProgramPost;
	 }
}
*/