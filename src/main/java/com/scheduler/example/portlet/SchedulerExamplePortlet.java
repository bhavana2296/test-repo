package com.scheduler.example.portlet;



import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.search.suggest.SuggesterResult.Entry;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.scheduler.example.constants.SchedulerExamplePortletKeys;

/**
 * @author inexture
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=SchedulerExample Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SchedulerExamplePortletKeys.SchedulerExample,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SchedulerExamplePortlet extends MVCPortlet {
	
	private static final Log _log = LogFactoryUtil.getLog(SchedulerExamplePortlet.class);
	
	public void addEntry(ActionRequest request, ActionResponse response) {
		
		int day= ParamUtil.getInteger(request, "year");
		int month= ParamUtil.getInteger(request, "month");
		int year = ParamUtil.getInteger(request, "year");
		int hh = ParamUtil.getInteger(request, "hh");
		int mm = ParamUtil.getInteger(request, "mm");
		int ss = ParamUtil.getInteger(request, "ss");
		
Calendar startCalendar = new GregorianCalendar(year , month, day, hh, mm, ss);
String jobCronPattern = SchedulerEngineHelperUtil.getCronText(startCalendar, false);


		String portletId= (String)request.getAttribute(WebKeys.PORTLET_ID);
        
	    String jobName= SchedulerTask.class.getName();
	    String groupName=SchedulerTask.class.getName();
	           
	    /*Calendar startCalendar = new GregorianCalendar(year , month, day, hh, mm, ss);
	    String jobCronPattern = SchedulerEngineHelperUtil.getCronText(startCalendar, false);
	    */                            //Calendar object & flag for time zone sensitive calendar
	     
	    Trigger trigger=TriggerFactoryUtil.createTrigger(jobName, groupName, jobCronPattern);
	            
	    Message message=new Message();
	    message.put("message","hello this is reminder");
	    message.put("email","6@gmail.com");
	    
	    try {
	          SchedulerEngineHelperUtil.schedule(
	                trigger,StorageType.PERSISTED,"Message_Desc",DestinationNames.SCHEDULER_DISPATCH,
	                message,0);                    
	         } catch (SchedulerException e) 
	                {
	                    e.printStackTrace();
	                }	
	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	    throws PortletException, IOException {

	   SchedulerExamplePortlet.class.getName(); 
	   
	    super.render(renderRequest, renderResponse);
	}
	
	
}