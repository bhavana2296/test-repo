package com.scheduler.example.portlet;

import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;

public class StorageTypeAware extends SchedulerEntryImpl implements SchedulerEntry,com.liferay.portal.kernel.scheduler.StorageTypeAware {

	public StorageTypeAware(final SchedulerEntryImpl schedulerEntry) {
		// TODO Auto-generated constructor stub
		super();

	    _schedulerEntry = schedulerEntry;

	    // use the same default that Liferay uses.
	    _storageType = StorageType.MEMORY_CLUSTERED;
	}

	public StorageTypeAware(final SchedulerEntryImpl schedulerEntry, final StorageType storageType) {
	    super();

	    _schedulerEntry = schedulerEntry;
	    _storageType = storageType;
	  }
	
	@Override
	  public String getDescription() {
	    return _schedulerEntry.getDescription();
	  }

	  @Override
	  public String getEventListenerClass() {
	    return _schedulerEntry.getEventListenerClass();
	  }

	  @Override
	  public StorageType getStorageType() {
	    return _storageType;
	  }

	  @Override
	  public Trigger getTrigger() {
	    return _schedulerEntry.getTrigger();
	  }

	  public void setDescription(final String description) {
	    _schedulerEntry.setDescription(description);
	  }
	  public void setTrigger(final Trigger trigger) {
	    _schedulerEntry.setTrigger(trigger);
	  }
	  public void setEventListenerClass(final String eventListenerClass) {
	    _schedulerEntry.setEventListenerClass(eventListenerClass);
	  }
	  

	
	private SchedulerEntryImpl _schedulerEntry;
	  private StorageType _storageType;

	
	

}
