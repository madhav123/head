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
[#import "spring.ftl" as spring]
[#import "newblueprintmacros.ftl" as mifos]
[@mifos.header "title" /]
[@mifos.topNavigationNoSecurity currentTab="Admin" /]
<div class="colmask leftmenu">
    <div class="colleft">
        <div class="col1wrap">
            <div class="col1">
            <div class="main_content">
                <span id="page.id" title="view_question_groups_details"/></span>
                [#if error_message_code??]
                    [@spring.message error_message_code/]
                [#else]
                    [#assign breadcrumb = {"admin":"AdminAction.do?method=load", "questionnaire.view.question.groups":"viewQuestionGroups.ftl",Request.questionGroupDetail.title:""}/]
                    [@mifos.crumbpairs breadcrumb/]
                <div class="content_panel">
                        <div class="marginLeft30">
                            [#assign boolean_text_yes][@spring.message "questionnaire.yes"/][/#assign]
                            [#assign boolean_text_no][@spring.message "questionnaire.no"/][/#assign]
                            <h1 id="questionGroup.title">
                                ${Request.questionGroupDetail.title}
                            </h1>
                            <div id="questionGroup.appliesTo" class="marginTop15">
                                [@spring.message "questionnaire.questionGroupAppliesTo"/]: ${Request.eventSources[Request.questionGroupDetail.eventSourceId]}
                            </div>
                            <div id="questionGroup.editable">
                                [@spring.message "questionnaire.editable"/]: ${Request.questionGroupDetail.editable?string(boolean_text_yes, boolean_text_no)}
                            </div>
                            <div id="questionGroup.sections" class="marginTop15">
                                [#list Request.questionGroupDetail.sections as section]
                                    <b>${section.name}</b><br/>
                                    <table id="sections.table.${section.name}" name="sections.table.${section.name}">
                                     <tr>
                                         <td class="drawtablehd" width="50%">[@spring.message "questionnaire.question.name"/]</td>
                                         <td class="drawtablehd" width="50%">[@spring.message "questionnaire.question.mandatory"/]</td>
                                     </tr>
                                    [#list section.sectionQuestions as sectionQuestion]
                                         <tr>
                                             <td class="drawtablerow" width="50%">${sectionQuestion.title}</td>
                                             <td class="drawtablerow" width="50%">
                                                 [#if sectionQuestion.mandatory]
                                                     [@spring.message "questionnaire.yes"/]
                                                 [#else]
                                                     [@spring.message "questionnaire.no"/]
                                                 [/#if]
                                             </td>
                                         </tr>
                                    [/#list]
                                    </table>
                                [/#list]
                            </div>
                        </div>
                    [/#if]
                </div>
            </div>
         </div>
        </div>
        <div class="col2">
            <div class="side_bar">
                [#include "newadminLeftPane.ftl" /]
            </div>
        </div>
    </div>
</div>
[@mifos.footer/]