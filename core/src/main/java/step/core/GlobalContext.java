/*******************************************************************************
 * (C) Copyright 2016 Jerome Comte and Dorian Cransac
 *  
 * This file is part of STEP
 *  
 * STEP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * STEP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *  
 * You should have received a copy of the GNU Affero General Public License
 * along with STEP.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package step.core;

import step.core.Controller.ServiceRegistrationCallback;
import step.core.access.UserAccessor;
import step.core.artefacts.ArtefactAccessor;
import step.core.artefacts.reports.ReportNodeAccessor;
import step.core.execution.ExecutionLifecycleManager;
import step.core.execution.model.ExecutionAccessor;
import step.core.plugins.PluginManager;
import step.core.repositories.RepositoryObjectManager;
import step.core.scheduler.ExecutionTaskAccessor;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class GlobalContext extends AbstractContext {
	
	private PluginManager pluginManager;
	
	private RepositoryObjectManager repositoryObjectManager;
	
	private MongoClient mongoClient;
	
	private MongoDatabase mongoDatabase;
	
	private ExecutionAccessor executionAccessor;
	
	private ArtefactAccessor artefactAccessor;
	
	private ReportNodeAccessor reportAccessor;
	
	private ExecutionTaskAccessor scheduleAccessor;
	
	private UserAccessor userAccessor;
	
	private ExecutionLifecycleManager executionLifecycleManager;
	
	private ServiceRegistrationCallback serviceRegistrationCallback;

	public GlobalContext() {
		super();
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public MongoDatabase getMongoDatabase() {
		return mongoDatabase;
	}

	public void setMongoDatabase(MongoDatabase mongoDatabase) {
		this.mongoDatabase = mongoDatabase;
	}

	public ExecutionAccessor getExecutionAccessor() {
		return executionAccessor;
	}

	public void setExecutionAccessor(ExecutionAccessor executionAccessor) {
		this.executionAccessor = executionAccessor;
	}

	public ArtefactAccessor getArtefactAccessor() {
		return artefactAccessor;
	}

	public void setArtefactAccessor(ArtefactAccessor artefactAccessor) {
		this.artefactAccessor = artefactAccessor;
	}

	public ReportNodeAccessor getReportAccessor() {
		return reportAccessor;
	}

	public void setReportAccessor(ReportNodeAccessor reportAccessor) {
		this.reportAccessor = reportAccessor;
	}

	public ExecutionTaskAccessor getScheduleAccessor() {
		return scheduleAccessor;
	}

	public void setScheduleAccessor(ExecutionTaskAccessor scheduleAccessor) {
		this.scheduleAccessor = scheduleAccessor;
	}

	public UserAccessor getUserAccessor() {
		return userAccessor;
	}

	public void setUserAccessor(UserAccessor userAccessor) {
		this.userAccessor = userAccessor;
	}

	public PluginManager getPluginManager() {
		return pluginManager;
	}

	public void setPluginManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}

	public RepositoryObjectManager getRepositoryObjectManager() {
		return repositoryObjectManager;
	}

	public void setRepositoryObjectManager(
			RepositoryObjectManager repositoryObjectManager) {
		this.repositoryObjectManager = repositoryObjectManager;
	}

	public ExecutionLifecycleManager getExecutionLifecycleManager() {
		return executionLifecycleManager;
	}

	public void setExecutionLifecycleManager(
			ExecutionLifecycleManager executionLifecycleManager) {
		this.executionLifecycleManager = executionLifecycleManager;
	}
	
	public ServiceRegistrationCallback getServiceRegistrationCallback() {
		return serviceRegistrationCallback;
	}

	public void setServiceRegistrationCallback(
			ServiceRegistrationCallback serviceRegistrationCallback) {
		this.serviceRegistrationCallback = serviceRegistrationCallback;
	}
	
}
