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

[#-- FIXME: these macros are copied from newblueprintmacros.ftl. They've been moved here for better organization. Delete from newblueprintmacros.ftl. --]

[#-- header macro --]
[#-- Generate the Mifos header with tabbed navigation --]
[#-- currentTab values: "Home","ClientsAndAccounts","Reports","Admin" --]
[#-- usage   [@mifos.topNavigation currentTab="Home" /] --]
[#macro topNavigation currentTab]
<div id="header">
    <div class="topAlign append-1">
        <span class="logo"></span>
        <a href="yourSettings.do?method=get" title="[@spring.message "yourSettings"/]">[@spring.message "yourSettings"/]</a>&nbsp;|&nbsp;
        <a id="logout_link" href="j_spring_security_logout" title="[@spring.message "logout"/]">[@spring.message "logout"/]</a>
    </div>
    <div>
        <span class="menu">
          <a id="header.link.home" href="custSearchAction.do?method=getHomePage" class="[#if currentTab == "Home"]taborange[#else]tablightorange[/#if]" title="[@spring.message "tab.Home" /]">[@spring.message "tab.Home" /]</a>
          <a id="header.link.clientsAndAccounts" href="custSearchAction.do?method=loadMainSearch" class="[#if currentTab == "ClientsAndAccounts"]taborange[#else]tablightorange[/#if]" title="[@spring.message "tab.ClientsAndAccounts" /]">[@spring.message "tab.ClientsAndAccounts" /]</a>
          <a id="header.link.reports" href="reportsAction.do?method=load" class="[#if currentTab == "Reports"]taborange[#else]tablightorange[/#if]" title="[@spring.message "tab.Reports" /]">[@spring.message "tab.Reports" /]</a>
          [@security.authorize ifAllGranted="ROLE_ADMIN"]
          <a id="header.link.admin" href="AdminAction.do?method=load" class="[#if currentTab == "Admin"]taborange[#else]tablightorange[/#if]" title="[@spring.message "tab.Admin" /]">[@spring.message "tab.Admin" /]</a></span>
          [/@security.authorize]
        </span>
    </div>
</div>
[/#macro]

[#-- TODO: add documentation --]
[#macro topNavigationNoSecurity currentTab]
<div id="header">
    <div class="site_logo">
        <span class="logo"></span>
        <a href="yourSettings.do?method=get" title="[@spring.message "yourSettings"/]">[@spring.message "yourSettings"/]</a>&nbsp;|&nbsp;
        <a id="logout_link" href="j_spring_security_logout" title="[@spring.message "logout"/]">[@spring.message "logout"/]</a>
    </div>
    <div class="top_menu">
        <ul>
         <li><a id="header.link.home" href="custSearchAction.do?method=getHomePage" class="[#if currentTab == "Home"]taborange[#else]tablightorange[/#if]" title="[@spring.message "tab.Home" /]">[@spring.message "tab.Home" /]</a>
         </li> <li> <a id="header.link.clientsAndAccounts" href="custSearchAction.do?method=loadMainSearch" class="[#if currentTab == "ClientsAndAccounts"]taborange[#else]tablightorange[/#if]" title="[@spring.message "tab.ClientsAndAccounts" /]">[@spring.message "tab.ClientsAndAccounts" /]</a>
         </li> <li> <a id="header.link.reports" href="reportsAction.do?method=load" class="[#if currentTab == "Reports"]taborange[#else]tablightorange[/#if]" title="[@spring.message "tab.Reports" /]">[@spring.message "tab.Reports" /]</a>
          </li> <li><a id="header.link.admin" href="AdminAction.do?method=load" class="[#if currentTab == "Admin"]taborange[#else]tablightorange[/#if]" title="[@spring.message "tab.Admin" /]">[@spring.message "tab.Admin" /]</a></li>
     </ul>
    </div>

</div>
[/#macro]
