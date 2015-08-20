package com.ibm.cnxdev;

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

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The servlet receives data and inverts the data which is sent back. 
 */
@WebServlet("/DataServlet")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * the call to the super class HttpServlet
     * @see HttpServlet#HttpServlet()
     */
    public DataServlet() {
        super();
    }

	/**
	 * process the GET's details 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST");
		response.addHeader("Access-Control-Allow-Headers","Origin, Content-Type, Accept");
		
		PrintWriter write = response.getWriter();
		write.println("doGet API for 3rd Party Call : successful");
		write.flush();
		
	}

	@Override
	protected void doHead(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST");
		response.addHeader("Access-Control-Allow-Headers","Origin, Content-Type, Accept");
		
	}

	/**
	 * receives the POST and converts it to a GET
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	/**
	 * set the options for Cross-Origin-Resource-Sharing
	 * @see HttpServlet#doOptions(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST");
		response.addHeader("Access-Control-Allow-Headers","Origin, Content-Type, Accept");
	}

}
