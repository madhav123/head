/*
 * Copyright (c) 2005-2009 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.test.acceptance.admin;

import org.mifos.framework.util.DbUnitUtilities;
import org.mifos.test.acceptance.framework.AppLauncher;
import org.mifos.test.acceptance.framework.HomePage;
import org.mifos.test.acceptance.framework.MifosPage;
import org.mifos.test.acceptance.framework.UiTestCaseBase;
import org.mifos.test.acceptance.framework.admin.AdminPage;
import org.mifos.test.acceptance.framework.admin.ViewFundsPage;
import org.mifos.test.acceptance.remote.InitializeApplicationRemoteTestingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "classpath:ui-test-context.xml" })
@Test(sequential = true, groups = {"acceptance","ui"})
public class ViewFundsTest extends UiTestCaseBase {
    private AppLauncher appLauncher;

    @Autowired
    private DriverManagerDataSource dataSource;
    @Autowired
    private DbUnitUtilities dbUnitUtilities;
    @Autowired
    private InitializeApplicationRemoteTestingService initRemote;
    
    
    private static String dataFileName = "acceptance_small_003_dbunit.xml.zip";
    
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    // one of the dependent methods throws Exception
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();
        appLauncher = new AppLauncher(selenium);
    }

    @AfterMethod
    public void tearDown() {
        (new MifosPage(selenium)).logout();
    }

    public void verifyViewFundsTest() {
        AdminPage adminPage = loginAndGoToAdminPage();
        ViewFundsPage viewFundsPage = adminPage.navigateToViewFundsPage();
        viewFundsPage.verifyPage();
    }
    
    
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void verifyViewFundsTableContentsTest() throws Exception {        
        initRemote.dataLoadAndCacheRefresh(dbUnitUtilities, 
                                           dataFileName,
                                           dataSource, selenium);        

        AdminPage adminPage = loginAndGoToAdminPage();
        ViewFundsPage viewFundsPage = adminPage.navigateToViewFundsPage();
        
        String[] expectedFundNames = new String[]{
                "Non Donor",
                "Funding Org A",
                "Funding Org B",
                "Funding Org C",
                "Funding Org D"               
        };

        String[] expectedFundCodes = new String[]{
                "00",
                "00",
                "00",
                "00",
                "00",
        };

        viewFundsPage.verifyFundName(expectedFundNames);
        viewFundsPage.verifyFundCode(expectedFundCodes);
    }
  

    private AdminPage loginAndGoToAdminPage() {
        HomePage homePage = appLauncher.launchMifos().loginSuccessfullyUsingDefaultCredentials();
        homePage.verifyPage();
        AdminPage adminPage = homePage.navigateToAdminPage();
        adminPage.verifyPage();
        return adminPage;
    }
}
