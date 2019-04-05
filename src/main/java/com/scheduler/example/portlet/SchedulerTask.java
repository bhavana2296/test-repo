package com.scheduler.example.portlet;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;

@Component(
		  immediate = true,property={"cron.expression=0 0 0 * * ?"},
		  service = SchedulerTask.class
		)
public class SchedulerTask extends BaseSchedulerEntryMessageListener{

	public static final Log logger=LogFactoryUtil.getLog(SchedulerTask.class);
	@Reference
	private SchedulerEngineHelper _SchedulerEngineHelper;
	public SchedulerTask() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		// TODO Auto-generated method stub
		
		 logger.info("This is a simple scheduler class");
		 Date currentDate = new Date();
	        SimpleDateFormat dateFomat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	        logger.info("This is RcBlogScheduler - " + dateFomat.format(currentDate));
		 /*SchedulerEntryImpl.setTrigger (
				 TriggerFactoryUtil.createTrigger(jobName, groupName, cronExpression)*/
	}
	
	
	@Activate
	  @Modified
	  protected void activate(Map<String,Object> properties) throws SchedulerException {

	    // extract the cron expression from the properties
	    String cronExpression = GetterUtil.getString(properties.get("cron.expression"), jobCronPattern);

	    // create a new trigger definition for the job.
	    String listenerClass = getEventListenerClass();
	    Trigger jobTrigger = _triggerFactory.createTrigger(listenerClass, listenerClass, new Date(), null, cronExpression);
	    logger.info(jobTrigger);
	    // wrap the current scheduler entry in our new wrapper.
	    // use the persisted storaget type and set the wrapper back to the class field.
	    schedulerEntryImpl = new StorageTypeAware(schedulerEntryImpl, StorageType.PERSISTED);

	    // update the trigger for the scheduled job.
	    schedulerEntryImpl.setTrigger(jobTrigger);

	    // if we were initialized (i.e. if this is called due to CA modification)
	    if (_initialized) {
	      // first deactivate the current job before we schedule.
	      deactivate();
	    }
	    
	    _schedulerEngineHelper.register(this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);
	    _schedulerEngineHelper.delete("job1", "group1", StorageType.PERSISTED);
	    // set the initialized flag.
	    _initialized = true;
	  }
	
		
	
	@Deactivate
    protected void deactivate() {
      // if we previously were initialized
      if (_initialized) {
        // unschedule the job so it is cleaned up
        try {
          _schedulerEngineHelper.unschedule(schedulerEntryImpl, getStorageType());
        } catch (SchedulerException se) {
          if (logger.isWarnEnabled()) {
            logger.warn("Unable to unschedule trigger", se);
          }
        }

        // unregister this listener
        _schedulerEngineHelper.unregister(this);
      }
      
      // clear the initialized flag
      _initialized = false;
    }

    protected StorageType getStorageType() {
        if (schedulerEntryImpl instanceof StorageTypeAware) {
          return ((StorageTypeAware) schedulerEntryImpl).getStorageType();
        }
        
        return StorageType.MEMORY_CLUSTERED;
      } 
    
    @Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
    protected void setModuleServiceLifecycle(ModuleServiceLifecycle moduleServiceLifecycle) {
    }

    @Reference(unbind = "-")
    protected void setTriggerFactory(TriggerFactory triggerFactory) {
      _triggerFactory = triggerFactory;
    }

    @Reference(unbind = "-")
    protected void setSchedulerEngineHelper(SchedulerEngineHelper schedulerEngineHelper) {
      _schedulerEngineHelper = schedulerEngineHelper;
    }
    
    
    
    
    // register the scheduled task
    





 private static final String jobCronPattern = SchedulerExamplePortlet.class.getName();
 
 private volatile boolean _initialized;
  private TriggerFactory _triggerFactory;
  private SchedulerEngineHelper _schedulerEngineHelper;
@Override
protected void doReceive(Message message) throws Exception {
	// TODO Auto-generated method stub
	
}



	

}
