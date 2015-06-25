/*
* © Copyright IBM Corp. 2015
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
* implied. See the License for the specific language governing
* permissions and limitations under the License.
*/
package com.ibm.cnxdevs.cloud.activitystreams.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.ibm.commons.util.io.json.JsonJavaObject;
import com.ibm.sbt.services.client.ClientServicesException;
import com.ibm.sbt.services.client.Response;
import com.ibm.sbt.services.rest.RestClient;

/**
 * 
 * @author Paul Bastide <pbastide@us.ibm.com> 
 */
public class App 
{
	static Properties props = new Properties();
	
	static{
		
		System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		//Can Set DEBUG
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG"); 
		
		
		try {
			props.load(App.class.getResourceAsStream("env.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get value
	 * 
	 * @param name
	 * @return
	 */
	private static String value(String name){
		return props.getProperty(name);
	}

	public static void main(String[] args) {
		try {
			/*
			 * Post the Single Event with Gadget
			 */
			InputStream entryStream = App.class.getResourceAsStream(value("ENTRY_JSON"));
			StringWriter writer = new StringWriter();
			IOUtils.copy(entryStream, writer, "UTF-8");
			String entryJson = writer.toString();
			
			entryJson = entryJson.replace("${GADGET}", value("BASE") + value("CLOUD_EE"));
			entryJson = entryJson.replace("${URL}", value("URL_THRIDPARTY"));
			
			String apiAll = value("BASE") + value("API_ALL");
			Response<JsonJavaObject> responseJson = RestClient.post(apiAll)
					.basicAuth(value("USER"), value("PASS")).body(entryJson, value("CONTENT_TYPE_JSON")).asJson();
			JsonJavaObject json = responseJson.getData();
			String entryId = json.getAsObject("entry").getAsString("id");

			System.out.println("Entry id : " + entryId);

			Thread.currentThread();
			Thread.sleep(10000l);

			/*
			 * Gets the Single Event
			 */
			String eventUrl = (value("BASE") + value("API_ENTRY")).replace("{VALUE}", entryId);
			Response<JsonJavaObject> response = RestClient.get(eventUrl)
					.basicAuth(value("USER"), value("PASS")).asJson();

			System.out.println("" + response.getData().toString());
			
			json = response.getData();
			String responseData = json.getAsObject("entry")
					.getAsObject("openSocial").getAsObject("embed")
					.getAsString("gadget");

			System.out
					.println("--------------------------------------------------");
			System.out.println("Gadget: " + responseData);
			
			System.out
			.println("--------------------------------------------------");
			System.out.println("Expected: " + value("CLOUD_EE"));
			

		} catch (ClientServicesException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
