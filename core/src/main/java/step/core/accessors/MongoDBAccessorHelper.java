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
package step.core.accessors;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.JacksonMapper;

import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.fasterxml.jackson.datatype.jsr353.JSR353Module;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import step.commons.conf.Configuration;

// TODO rewrite this ugly class without static
public class MongoDBAccessorHelper {

	private static MongoDBAccessorHelper INSTANCE = new MongoDBAccessorHelper();

	private String host;
	
	private Integer port;
	
	String user;
	
	String pwd;
	
	String db;
	
	public MongoDBAccessorHelper() {
		super();
		
		host = Configuration.getInstance().getProperty("db.host");
		port = Configuration.getInstance().getPropertyAsInteger("db.port",27017);
		user = Configuration.getInstance().getProperty("db.username");
		pwd = Configuration.getInstance().getProperty("db.password");
		db = Configuration.getInstance().getProperty("db.database","step");
	}

	public static MongoDBAccessorHelper getInstance() {
		return INSTANCE;
	}
	
	public static MongoClient getClient() {
		return getInstance().getMongoClient();
	}
	
	public MongoClient getMongoClient() {
		ServerAddress address = new ServerAddress(host, port);
		List<MongoCredential> credentials = new ArrayList<>();
		if(user!=null) {
			MongoCredential credential = MongoCredential.createMongoCRCredential(user, db, pwd.toCharArray());
			credentials.add(credential);
		}
		return new MongoClient(address, credentials);
	}
	
	public static MongoCollection getCollection(MongoClient client, String collectionName) {
		return getInstance().getMongoCollection(client, collectionName);
	}
	
	public MongoCollection getMongoCollection(MongoClient client, String collectionName) {
		DB db = client.getDB(this.db);
		
		Jongo jongo = new Jongo(db,new JacksonMapper.Builder()
			      .registerModule(new JSR353Module())
			      .registerModule(new JsonOrgModule()).build());
		MongoCollection collection = jongo.getCollection(collectionName);
		
		return collection;
	}
	
	public static com.mongodb.client.MongoCollection<Document> getMongoCollection_(MongoClient client, String collectionName) {		
		MongoDatabase db_ = getInstance().getMongoDatabase(client);
		return db_.getCollection(collectionName);
	}

	public MongoDatabase getMongoDatabase(MongoClient client) {
		MongoDatabase db_ = client.getDatabase(getInstance().db);
		return db_;
	}
	
}
