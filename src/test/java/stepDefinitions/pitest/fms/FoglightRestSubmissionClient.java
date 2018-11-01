/*
 * Copyright 2018 Dell Inc.
 * ALL RIGHTS RESERVED.
 *
 * This software is the confidential and proprietary information of
 * Dell Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered
 * into with Dell Inc.
 *
 * DELL INC. MAKES NO REPRESENTATIONS OR WARRANTIES
 * ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. DELL SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 */
package stepDefinitions.pitest.fms;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import config.TestContext;

/**
 * @author tli1
 */
public class FoglightRestSubmissionClient {
    private static FoglightRestSubmissionClient restfulClient;

    private static final String FOGLIGHT_TOPOLOGY_PUSH_DATA_PATH = "topology/pushData";
    private static final String FOGLIAHT_SECURITY_LOGIN_PATH = "security/login";
    private final String foglightHostName;
    private final Integer port;
    private Client client;
    private JsonParser parser;
    private BaseRestfulContext baseRestfulContext;
    private ExecutorService executorServices;
    private final String accessToken;
    private final int FOGLIGHT_SUBMISSION_THREAD_SIZE = 20;
    private String repositoryHostName;

    private FoglightRestSubmissionClient() {
        //this.mServiceProvider = serviceProvider;
        this.foglightHostName = TestContext.getProperty("foglightHostName");
        this.port = Integer.parseInt(TestContext.getProperty("foglightPort"));
        this.accessToken = TestContext.getProperty("foglightAccessToken");
        init();
    }

    public static FoglightRestSubmissionClient getInstance() {
        if (restfulClient == null) {
            synchronized (FoglightRestSubmissionClient.class) {
                if (restfulClient == null) {
                    restfulClient = new FoglightRestSubmissionClient();
                }
            }
        }
        return restfulClient;
    }

    private void init() {

        baseRestfulContext = new BaseRestfulContext(foglightHostName, port);
        client = ClientBuilder.newClient();
        parser = new JsonParser();


        this.executorServices = Executors.newFixedThreadPool(FOGLIGHT_SUBMISSION_THREAD_SIZE, new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "FOGLIGHT-REST-SUBMISSION" + threadNumber.getAndIncrement());
            }
        });
    }

    public String getAccessToken(String userName, String password) throws Exception {
        WebTarget target = client.target(baseRestfulContext.getURL()).path(FOGLIAHT_SECURITY_LOGIN_PATH);
        Form form = new Form();
        if (userName != null && password != null) {
            form.param("username", userName);
            form.param("pwd", password);
        }
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form,
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        String responseText = response.readEntity(String.class);

        if (response.getStatus() != 200) {
            throw new Exception("login failed: " + response.getStatus() + ", result:" + responseText);
        }

        JsonObject obj = (JsonObject) parser.parse(responseText);
        String token = obj.getAsJsonObject("data").get("access-token").getAsString();
        //LogUtils.debug(mServiceProvider.getLogService(), "successful acquired token: %s from foglight", token);
        return token;
    }

    private void submitTopologyData(String dataJson) throws Exception {
    	String accessToken = TestContext.getProperty("foglightAccessToken");
    	if(accessToken!=null&&accessToken.length()>0) {
    		//System.out.println("Submitting data with token: "+accessToken);
	        WebTarget target = client.target(baseRestfulContext.getURL()).path(FOGLIGHT_TOPOLOGY_PUSH_DATA_PATH);

	        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).header("Auth-Token", accessToken).post(Entity.json(dataJson));

	        String responseText = response.readEntity(String.class);
	        //LogUtils.debug(mServiceProvider.getLogService(), "submit topology json data to %s result: %s", target.getUri(), responseText);
	        if (response.getStatus() != 200) {
	            throw new Exception("submit Topology Data failed, status: " + response.getStatus() + " detail" + responseText);
	        }
    	}
    	else {
    		throw new Exception("FMS access token is empty");
    	}
    }

    private class BaseRestfulContext {
        private String protocol = "http";
        private int port;
        private String serverIPAddress;
        private String FOGLIGHT_RESTFUL_PATH_PREFIX = "/api/v1";

        private BaseRestfulContext(String serverIPAddress, int port) {
            this.port = port;
            this.serverIPAddress = serverIPAddress;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        String getURL() {
            return protocol + "://" + serverIPAddress + ":" + port + FOGLIGHT_RESTFUL_PATH_PREFIX;
        }
    }


    public void submitSubmissionUsageData2Foglight(final String instanceName, final long startTime,
                                                   final String timeline, final String dimessionName, final String viewName, final long responseTime) {

        executorServices.execute(new Runnable() {
            @Override
            public void run() {
                PISubmissionData piSubmissionData = new PISubmissionData("PIUIAnalyzeAgent", startTime, System.currentTimeMillis());
                piSubmissionData.addData(instanceName, timeline, dimessionName, viewName, responseTime);
                Gson gson = new Gson();
                String jsonData = gson.toJson(piSubmissionData);
                try {
                    submitTopologyData(jsonData);
                } catch (Exception e) {
                    //LogUtils.warn(mServiceProvider.getLogService(), e, "Failed to submit table %s analyze data to FMS", tableName);
                }
            }
        });
    }

    public void submitSubmissionUsageData2Foglight1(final String instanceName, final long startTime,
            final String timeline, final String dimessionName, final String viewName, final long responseTime) {


		PISubmissionData piSubmissionData = new PISubmissionData("PIUIAnalyzeAgent", startTime, System.currentTimeMillis());
		piSubmissionData.addData(instanceName, timeline, dimessionName, viewName, responseTime);
		Gson gson = new Gson();
		String jsonData = gson.toJson(piSubmissionData);
		try {
		submitTopologyData(jsonData);
		} catch (Exception e) {
		//LogUtils.warn(mServiceProvider.getLogService(), e, "Failed to submit table %s analyze data to FMS", tableName);
		}

	}

    public void submitRoot(String type, String name) {
    	executorServices.execute(new Runnable() {
            @Override
            public void run() {
            	Date startTime = new Date();
                PISubmissionData1 piSubmissionData = new PISubmissionData1("PIUIAnalyzeAgent", startTime.getTime(), System.currentTimeMillis());
                piSubmissionData.addData(type,name);
                Gson gson = new Gson();
                String jsonData = gson.toJson(piSubmissionData);
                try {
                    submitTopologyData(jsonData);
                } catch (Exception e) {
                    //LogUtils.warn(mServiceProvider.getLogService(), e, "Failed to submit table %s analyze data to FMS", tableName);
                }
            }
        });

    }

//    public static void main(String[] args) {
//    	FoglightRestSubmissionClient client = FoglightRestSubmissionClient.getInstance();
//		String token;
//		try {
//			token = client.getAccessToken("foglight", "foglight");
//			//Reporter.addStepLog("FMS access token: "+token);
//			TestContext.setProperty("foglightAccessToken", token);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	FoglightRestSubmissionClient.getInstance().submitRoot("AlternativeUIPerformanceRoot", "AlternativeUIPerformanceRoot");
//    	FoglightRestSubmissionClient.getInstance().submitRoot("AlternativeInstancePerformance", "10.30.168.227-10.30.168.73");
////    	FoglightRestSubmissionClient.getInstance().submitRoot("AlternativeInstancePerformance", "10.30.168.117-10.30.169.32");
//    }


    public void stop() {

        if (executorServices != null) {
            executorServices.shutdownNow();
        }
        restfulClient = null;
    }
}
