package com.vodia.api.dashboard.domain1.agentsStats;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AgentStatsController {

	private static final Logger log = LoggerFactory.getLogger(AgentStatsController.class);
	
	@Autowired
	private AgentStatsService ass;

	
	@GetMapping("/getAgentStatsToDashboard")
	@CrossOrigin(origins = "*")
	private AgentStatsToDashboard getAgentStatsToDashboard(HttpServletRequest request) {
		//List<ACD_WallBoard_QueueWise> acd_wallboard_queue_list;
		
		log.debug("calling agentStatsToDashboard");
		return ass.agentStatsToDashboard(request);
		
	}
	
	
	/*
	 [
   {
      "agents":[
         {
            "agent":"203",
            "calls":2,
            "missed":0,
            "ivr":224,
            "ring":15,
            "talk":855,
            "hold":487,
            "idle":{
               "calls":2,
               "duration":250
            }
         },
         {
            "agent":"208",
            "calls":3,
            "missed":1,
            "ivr":215,
            "ring":32,
            "talk":993,
            "hold":0,
            "idle":{
               "calls":3,
               "duration":22396
            }
         },
         {
            "agent":"211",
            "calls":4,
            "missed":1,
            "ivr":216,
            "ring":25,
            "talk":1848,
            "hold":1128,
            "idle":{
               "calls":4,
               "duration":568157
            }
         },
         {
            "agent":"223",
            "calls":3,
            "missed":0,
            "ivr":317,
            "ring":16,
            "talk":789,
            "hold":0,
            "idle":{
               "calls":3,
               "duration":166
            }
         }
      ],
      "qid":"300"
   },
   {
      "agents":[
         {
            "agent":"218",
            "calls":6,
            "missed":0,
            "ivr":1921,
            "ring":131,
            "talk":5442,
            "hold":295,
            "idle":{
               "calls":6,
               "duration":187703
            }
         },
         {
            "agent":"219",
            "calls":3,
            "missed":0,
            "ivr":504,
            "ring":28,
            "talk":517,
            "hold":3,
            "idle":{
               "calls":3,
               "duration":139275
            }
         }
      ],
      "qid":"303"
   },
   {
      "agents":[
         {
            "agent":"270",
            "calls":15,
            "missed":14,
            "ivr":715,
            "ring":153,
            "talk":4257,
            "hold":0,
            "idle":{
               "calls":15,
               "duration":1110
            }
         },
         {
            "agent":"272",
            "calls":23,
            "missed":0,
            "ivr":389,
            "ring":277,
            "talk":4845,
            "hold":1164,
            "idle":{
               "calls":23,
               "duration":9217
            }
         },
         {
            "agent":"274",
            "calls":14,
            "missed":4,
            "ivr":529,
            "ring":478,
            "talk":4010,
            "hold":545,
            "idle":{
               "calls":14,
               "duration":17898
            }
         }
      ],
      "qid":"305"
   },
   {
      "agents":[
         {
            "agent":"203",
            "calls":2,
            "missed":0,
            "ivr":505,
            "ring":17,
            "talk":447,
            "hold":187,
            "idle":{
               "calls":2,
               "duration":38056
            }
         },
         {
            "agent":"208",
            "calls":2,
            "missed":0,
            "ivr":479,
            "ring":5,
            "talk":775,
            "hold":0,
            "idle":{
               "calls":2,
               "duration":6816
            }
         },
         {
            "agent":"223",
            "calls":4,
            "missed":0,
            "ivr":2514,
            "ring":19,
            "talk":1447,
            "hold":112,
            "idle":{
               "calls":4,
               "duration":387186
            }
         }
      ],
      "qid":"306"
   },
   {
      "agents":[
         {
            "agent":"203",
            "calls":3,
            "missed":0,
            "ivr":30,
            "ring":14,
            "talk":299,
            "hold":0,
            "idle":{
               "calls":3,
               "duration":166
            }
         },
         {
            "agent":"208",
            "calls":3,
            "missed":0,
            "ivr":96,
            "ring":7,
            "talk":465,
            "hold":4,
            "idle":{
               "calls":3,
               "duration":108741
            }
         },
         {
            "agent":"223",
            "calls":13,
            "missed":1,
            "ivr":3,
            "ring":144,
            "talk":1165,
            "hold":0,
            "idle":{
               "calls":13,
               "duration":1637
            }
         }
      ],
      "qid":"307"
   },
   {
      "agents":[
         {
            "agent":"209",
            "calls":1,
            "missed":0,
            "ivr":0,
            "ring":46,
            "talk":4,
            "hold":0,
            "idle":{
               "calls":1,
               "duration":42477
            }
         },
         {
            "agent":"210",
            "calls":3,
            "missed":0,
            "ivr":354,
            "ring":5,
            "talk":123,
            "hold":0,
            "idle":{
               "calls":3,
               "duration":83419
            }
         }
      ],
      "qid":"301"
   },
   {
      "agents":[
         {
            "agent":"203",
            "calls":36,
            "missed":0,
            "ivr":5744,
            "ring":383,
            "talk":12324,
            "hold":1866,
            "idle":{
               "calls":36,
               "duration":100842
            }
         },
         {
            "agent":"205",
            "calls":12,
            "missed":0,
            "ivr":393,
            "ring":90,
            "talk":8369,
            "hold":635,
            "idle":{
               "calls":12,
               "duration":41
            }
         },
         {
            "agent":"208",
            "calls":27,
            "missed":0,
            "ivr":3971,
            "ring":210,
            "talk":12398,
            "hold":1713,
            "idle":{
               "calls":27,
               "duration":19309
            }
         },
         {
            "agent":"211",
            "calls":14,
            "missed":0,
            "ivr":4627,
            "ring":85,
            "talk":5711,
            "hold":574,
            "idle":{
               "calls":14,
               "duration":91229
            }
         },
         {
            "agent":"221",
            "calls":3,
            "missed":0,
            "ivr":170,
            "ring":23,
            "talk":2662,
            "hold":353,
            "idle":{
               "calls":3,
               "duration":166
            }
         },
         {
            "agent":"223",
            "calls":12,
            "missed":0,
            "ivr":2224,
            "ring":59,
            "talk":2702,
            "hold":10,
            "idle":{
               "calls":12,
               "duration":92803
            }
         },
         {
            "agent":"232",
            "calls":1,
            "missed":0,
            "ivr":393,
            "ring":2,
            "talk":464,
            "hold":0,
            "idle":{
               "calls":1,
               "duration":500
            }
         }
      ],
      "qid":"302"
   },
   {
      "agents":[
         {
            "agent":"218",
            "calls":1,
            "missed":0,
            "ivr":11,
            "ring":36,
            "talk":265,
            "hold":0,
            "idle":{
               "calls":1,
               "duration":500
            }
         },
         {
            "agent":"219",
            "calls":2,
            "missed":0,
            "ivr":22,
            "ring":15,
            "talk":1732,
            "hold":450,
            "idle":{
               "calls":2,
               "duration":250
            }
         }
      ],
      "qid":"304"
   }
]
	 */
	/*
	 * #https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/acd/306@compassoffices.ak1.cloudpbx.net.nz/stat?date=20200902&days=1 -- 

	 * {"agents":[{"agent":"203","calls":3,"missed":2,"ivr":2301,"ring":1795,"talk":
	 * 2016,"hold":944,"idle":{"calls":3,"duration":860552}},{"agent":"205","calls":
	 * 3,"missed":1,"ivr":1550,"ring":21,"talk":3808,"hold":579,"idle":{"calls":3,
	 * "duration":285095}},{"agent":"208","calls":1,"missed":1,"ivr":438,"ring":6,
	 * "talk":71,"hold":0,"idle":{"calls":1,"duration":443889}},{"agent":"221",
	 * "calls":2,"missed":0,"ivr":200,"ring":17,"talk":1415,"hold":761,"idle":{
	 * "calls":2,"duration":505343}},{"agent":"223","calls":2,"missed":0,"ivr":371,
	 * "ring":7,"talk":581,"hold":0,"idle":{"calls":2,"duration":56441}}],
	 * "redirect_waiting":0,"redirect_ringing":0,"redirect_anonymous":0,
	 * "hangup_waiting":3,"hangup_ringing":1,"user_interaction":0,
	 * "admin_interaction":0,"max_calls":0,"soap":0,"other":6}
	 */
	/*
	 * https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds
	 * [{"name":"300","display":"Business-Q","agents":["203","205","208","221"],
	 * "calls":0},{"name":"303","display":"SD_Q","agents":["219","218"],"calls":0},{
	 * "name":"305","display":"Activata","agents":["270","273"],"calls":1},{"name":
	 * "306","display":"Res-Q","agents":["223","203","205","208","211","212"],
	 * "calls":1},{"name":"307","display":"Mobile_CB-Q","agents":["203","205","208",
	 * "211","212"],"calls":1},{"name":"308","display":"","agents":[],"calls":0}]
	 */
}
