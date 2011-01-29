[#ftl]
[#--
* Copyright (c) 2005-2010 Grameen Foundation USA
*  All rights reserved.
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
*  implied. See the License for the specific language governing
*  permissions and limitations under the License.
*
*  See also http://www.apache.org/licenses/LICENSE-2.0.html for an
*  explanation of the license and how it is applied.
--]

[@layout.webflow currentState="createSavingsAccount.flowState.selectCustomer" 
                 states=["createSavingsAccount.flowState.selectCustomer", 
                 		 "createSavingsAccount.flowState.enterAccountInfo", 
                 		 "createSavingsAccount.flowState.reviewAndSubmit"]] 

<h1>Create Savings Account - <span class="standout">Select a customer</span></h1>

<p>Enter a Client or Group or Center name and click Search. Click Cancel to return to Clients & Accounts without submitting information.</p>
<br/>

[@form.errors "savingsAccountFormBean.*"/]
<form action="${flowExecutionUrl}" method="post" class="two-columns">
	<fieldset>
	<div class="row">
		<label for="searchString">Name:</label>
		[@spring.formInput "savingsAccountFormBean.searchString" /]
	</div>
	</fieldset>
	<div class="row">
		<input type="submit" class="submit" value="Search" name="_eventId_searchTermEntered" />
		<input type="submit" class="cancel" value="Cancel" name="_eventId_cancel" />
	</div>
</form>
<br/>

[/@layout.webflow]