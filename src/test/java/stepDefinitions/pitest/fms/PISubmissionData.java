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

import java.util.ArrayList;
import java.util.List;

/**
 * @author tli1
 */
public class PISubmissionData {
    private List<PISubmission> data = new ArrayList<PISubmission>();
    private long startTime;
    private long endTime;
    private String agentName;

    public PISubmissionData(String agentName, long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.agentName = agentName;
    }

    public void addData(String instanceName, String timeline, String dimessionName, String viewName, long responseTime) {
        PISubmission piSubmission = new PISubmission("AlternativeInstanceViewPerformance");
        piSubmission.addProperties(instanceName, timeline, dimessionName, viewName, responseTime);
        data.add(piSubmission);
    }


    private class PISubmission {
        private String typeName;
        private Properties properties;

        PISubmission(String typeName) {
            this.typeName = typeName;
        }

        void addProperties(String instanceName, String timeline, String dimessionName, String viewName, long responseTime) {
            this.properties = new Properties(instanceName, timeline, dimessionName, viewName, responseTime);
        }

        private class Properties {
            private String instanceName;
            private String timeline;
            private String dimessionName;
            private String viewName;
            private long responseTime;

            Properties(String instanceName, String timeline, String dimessionName, String viewName, long responseTime) {
                this.instanceName = instanceName;
                this.timeline = timeline;
                this.dimessionName = dimessionName;
                this.viewName = viewName;
                this.responseTime = responseTime;
            }
        }
    }
}
