/*
 * Copyright (c) 2005-2011 Grameen Foundation USA
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

package org.mifos.test.acceptance.savingsproduct;

import org.joda.time.DateTime;
import org.mifos.framework.util.DbUnitUtilities;
import org.mifos.test.acceptance.framework.MifosPage;
import org.mifos.test.acceptance.framework.UiTestCaseBase;
import org.mifos.test.acceptance.framework.account.AccountStatus;
import org.mifos.test.acceptance.framework.account.EditAccountStatusParameters;
import org.mifos.test.acceptance.framework.savings.CreateSavingsAccountSearchParameters;
import org.mifos.test.acceptance.framework.savings.CreateSavingsAccountSubmitParameters;
import org.mifos.test.acceptance.framework.savings.DepositWithdrawalSavingsParameters;
import org.mifos.test.acceptance.framework.savings.SavingsAccountDetailPage;
import org.mifos.test.acceptance.framework.savingsproduct.DefineNewSavingsProductConfirmationPage;
import org.mifos.test.acceptance.framework.savingsproduct.SavingsProductParameters;
import org.mifos.test.acceptance.framework.testhelpers.BatchJobHelper;
import org.mifos.test.acceptance.framework.testhelpers.NavigationHelper;
import org.mifos.test.acceptance.framework.testhelpers.SavingsAccountHelper;
import org.mifos.test.acceptance.framework.testhelpers.SavingsProductHelper;
import org.mifos.test.acceptance.framework.util.UiTestUtils;
import org.mifos.test.acceptance.remote.DateTimeUpdaterRemoteTestingService;
import org.mifos.test.acceptance.remote.InitializeApplicationRemoteTestingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(locations={"classpath:ui-test-context.xml"})
@Test(sequential=true, groups={"savingsproduct","acceptance", "ui", "smoke"})
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public class DefineNewSavingsProductTest extends UiTestCaseBase {

    private SavingsProductHelper savingsProductHelper;

    @Autowired
    private DbUnitUtilities dbUnitUtilities;
    @Autowired
    private DriverManagerDataSource dataSource;
    @Autowired
    private InitializeApplicationRemoteTestingService initRemote;

    private SavingsAccountHelper savingsAccountHelper;
    private NavigationHelper navigationHelper;

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException") // one of the dependent methods throws Exception
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        DateTimeUpdaterRemoteTestingService dateTimeUpdaterRemoteTestingService = new DateTimeUpdaterRemoteTestingService(selenium);
        dateTimeUpdaterRemoteTestingService.resetDateTime();

        savingsProductHelper = new SavingsProductHelper(selenium);
        savingsAccountHelper = new SavingsAccountHelper(selenium);
        navigationHelper = new NavigationHelper(selenium);
    }

    @AfterMethod
    public void logOut() {
        (new MifosPage(selenium)).logout();
    }

    // http://mifosforge.jira.com/browse/MIFOSTEST-139
    @Test(enabled=true)
    public void createVoluntarySavingsProductForCenters() throws Exception {
        SavingsProductParameters params = savingsProductHelper.getGenericSavingsProductParameters(SavingsProductParameters.VOLUNTARY,SavingsProductParameters.CENTERS);
        DefineNewSavingsProductConfirmationPage confirmationPage = savingsProductHelper.createSavingsProduct(params);

        confirmationPage.navigateToSavingsProductDetails();
        createSavingAccountWithCreatedProduct("Default Center", params.getProductInstanceName(), "7777.8");
    }

    // http://mifosforge.jira.com/browse/MIFOSTEST-137
    @Test(enabled=true)
    public void createVoluntarySavingsProductForGroups() throws Exception {

        SavingsProductParameters params = savingsProductHelper.getGenericSavingsProductParameters(SavingsProductParameters.VOLUNTARY,SavingsProductParameters.GROUPS);
        DefineNewSavingsProductConfirmationPage confirmationPage = savingsProductHelper.createSavingsProduct(params);

        confirmationPage.navigateToSavingsProductDetails();
        createSavingAccountWithCreatedProduct("Default Group", params.getProductInstanceName(), "234.0");
    }

    // http://mifosforge.jira.com/browse/MIFOSTEST-1093
    @Test(enabled=true)
    public void createVoluntarySavingsProductForClients() throws Exception {

        SavingsProductParameters params = savingsProductHelper.getGenericSavingsProductParameters(SavingsProductParameters.VOLUNTARY,SavingsProductParameters.CLIENTS);
        DefineNewSavingsProductConfirmationPage confirmationPage = savingsProductHelper.createSavingsProduct(params);

        confirmationPage.navigateToSavingsProductDetails();
        createSavingAccountWithCreatedProduct("Client - Mary Monthly",params.getProductInstanceName(),"200.0");
    }

    // http://mifosforge.jira.com/browse/MIFOSTEST-1094
    @Test(enabled=true)
    public void createMandatorySavingsProductForGroups() throws Exception {

        SavingsProductParameters params = savingsProductHelper.getGenericSavingsProductParameters(SavingsProductParameters.MANDATORY,SavingsProductParameters.GROUPS);
        DefineNewSavingsProductConfirmationPage confirmationPage = savingsProductHelper.createSavingsProduct(params);

        confirmationPage.navigateToSavingsProductDetails();
        createSavingAccountWithCreatedProduct("Default Group",params.getProductInstanceName(),"534.0");
    }

    // http://mifosforge.jira.com/browse/MIFOSTEST-138
    @Test(enabled=true)
    public void createMandatorySavingsProductForClients() throws Exception {

        SavingsProductParameters params = savingsProductHelper.getGenericSavingsProductParameters(SavingsProductParameters.MANDATORY,SavingsProductParameters.CLIENTS);
        DefineNewSavingsProductConfirmationPage confirmationPage = savingsProductHelper.createSavingsProduct(params);

        confirmationPage.navigateToSavingsProductDetails();
        createSavingAccountWithCreatedProduct("Client - Mary Monthly",params.getProductInstanceName(),"248.0");
    }

    // http://mifosforge.jira.com/browse/MIFOSTEST-1095
    @Test(enabled=true)
    public void createMandatorySavingsProductForCenters() throws Exception {

        SavingsProductParameters params = savingsProductHelper.getGenericSavingsProductParameters(SavingsProductParameters.MANDATORY,SavingsProductParameters.CENTERS);
        DefineNewSavingsProductConfirmationPage confirmationPage = savingsProductHelper.createSavingsProduct(params);

        confirmationPage.navigateToSavingsProductDetails();
        createSavingAccountWithCreatedProduct("Default Center",params.getProductInstanceName(),"7777.8");
    }

    //http://mifosforge.jira.com/browse/MIFOSTEST-712
    //TODO batch job SavingsIntPostingTaskJob not calculate interests in automated Tests
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")// one of the dependent methods throws Exception
    @Test(enabled=false)
    public void savingsAccountWithDailyInterestMandatoryDeposits() throws Exception {
        //Given
        DateTimeUpdaterRemoteTestingService dateTimeUpdaterRemoteTestingService = new DateTimeUpdaterRemoteTestingService(selenium);
        DateTime targetTime = new DateTime(2011,2,10,13,0,0,0);
        dateTimeUpdaterRemoteTestingService.setDateTime(targetTime);
        initRemote.dataLoadAndCacheRefresh(dbUnitUtilities, "acceptance_small_008_dbunit.xml", dataSource, selenium);

        //When
        SavingsProductParameters params = savingsProductHelper.getMandatoryClientsMinimumBalanceSavingsProductParameters();
        DefineNewSavingsProductConfirmationPage confirmationPage = savingsProductHelper.createSavingsProduct(params);
        confirmationPage.navigateToSavingsProductDetails();     //"Stu1233266079799 Client1233266079799"
        SavingsAccountDetailPage savingsAccountDetailPage = createSavingAccountWithCreatedProduct("Stu1233266079799 Client1233266079799", params.getProductInstanceName(), "100000.0");
        String savingsId = savingsAccountDetailPage.getAccountId();

        EditAccountStatusParameters editAccountStatusParameters =new EditAccountStatusParameters();
        editAccountStatusParameters.setAccountStatus(AccountStatus.SAVINGS_ACTIVE);
        editAccountStatusParameters.setNote("change status to active");
        savingsAccountHelper.changeStatus(savingsId, editAccountStatusParameters);

        DepositWithdrawalSavingsParameters depositParams = new DepositWithdrawalSavingsParameters();

        targetTime = new DateTime(2011,2,14,13,0,0,0);
        depositParams=makeDefaultDeposit(targetTime,depositParams,savingsId);

        targetTime = new DateTime(2011,2,21,13,0,0,0);
        depositParams=makeDefaultDeposit(targetTime,depositParams,savingsId);

        targetTime = new DateTime(2011,2,28,13,0,0,0);
        depositParams=makeDefaultDeposit(targetTime,depositParams,savingsId);

        depositParams = setDepositParams(depositParams, "01", "03", "2011");
        dateTimeUpdaterRemoteTestingService.setDateTime(targetTime);

        //Then
        targetTime = new DateTime(2011,3,1,13,0,0,0);
        dateTimeUpdaterRemoteTestingService.setDateTime(targetTime);

        navigationHelper.navigateToSavingsAccountDetailPage(savingsId);

        navigationHelper.navigateToAdminPage();
        runBatchJobsForSavingsIntPosting();
        UiTestUtils.sleep(10000);

        navigationHelper.navigateToSavingsAccountDetailPage(savingsId);
        Assert.assertEquals(selenium.getTable("recentActivityForDetailPage.1.2"),"57.4");

        //When
        targetTime = new DateTime(2011,3,7,13,0,0,0);
        depositParams=makeDefaultDeposit(targetTime,depositParams,savingsId);

        targetTime = new DateTime(2011,3,14,13,0,0,0);
        depositParams=makeDefaultDeposit(targetTime,depositParams,savingsId);

        targetTime = new DateTime(2011,3,21,13,0,0,0);
        depositParams=makeDefaultDeposit(targetTime,depositParams,savingsId);

        targetTime = new DateTime(2011,3,28,13,0,0,0);
        depositParams=makeDefaultDeposit(targetTime,depositParams,savingsId);

        //Then
        targetTime = new DateTime(2011,4,1,13,0,0,0);
        dateTimeUpdaterRemoteTestingService.setDateTime(targetTime);

        navigationHelper.navigateToSavingsAccountDetailPage(savingsId);

        navigationHelper.navigateToAdminPage();
        runBatchJobsForSavingsIntPosting();
        UiTestUtils.sleep(10000);

        navigationHelper.navigateToSavingsAccountDetailPage(savingsId);
        Assert.assertEquals(selenium.getTable("recentActivityForDetailPage.1.2"),"402.7");
    }
    //http://mifosforge.jira.com/browse/MIFOSTEST-141
    //TODO batch job SavingsIntPostingTaskJob not calculate interests in automated Tests
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")// one of the dependent methods throws Exception
    @Test(enabled=false)
    public void savingsAccountWith3monthInterestVoluntaryDeposits() throws Exception {
        //Given
        DateTimeUpdaterRemoteTestingService dateTimeUpdaterRemoteTestingService = new DateTimeUpdaterRemoteTestingService(selenium);
        DateTime targetTime = new DateTime(2011,2,15,13,0,0,0);
        dateTimeUpdaterRemoteTestingService.setDateTime(targetTime);
        initRemote.dataLoadAndCacheRefresh(dbUnitUtilities, "acceptance_small_008_dbunit.xml", dataSource, selenium);

        //When
        SavingsProductParameters params = savingsProductHelper.getVoluntaryClients3MonthCalculactionPostingProductParameters();

        DefineNewSavingsProductConfirmationPage confirmationPage = savingsProductHelper.createSavingsProduct(params);
        confirmationPage.navigateToSavingsProductDetails();
        SavingsAccountDetailPage savingsAccountDetailPage = createSavingAccountWithCreatedProduct("Stu1233266079799 Client1233266079799",params.getProductInstanceName(),"100000.0");
        String savingsId = savingsAccountDetailPage.getAccountId();

        EditAccountStatusParameters editAccountStatusParameters =new EditAccountStatusParameters();
        editAccountStatusParameters.setAccountStatus(AccountStatus.SAVINGS_ACTIVE);
        editAccountStatusParameters.setNote("change status to active");
        savingsAccountHelper.changeStatus(savingsId, editAccountStatusParameters);

        DepositWithdrawalSavingsParameters depositParams = new DepositWithdrawalSavingsParameters();

        depositParams=makeDefaultDeposit(targetTime,depositParams,savingsId);

        //Then
        targetTime = new DateTime(2011,5,15,22,0,0,0);
        dateTimeUpdaterRemoteTestingService.setDateTime(targetTime);

        navigationHelper.navigateToSavingsAccountDetailPage(savingsId);
        navigationHelper.navigateToAdminPage();
        runBatchJobsForSavingsIntPosting();
        selenium.click("logout_link");
        UiTestUtils.sleep(10000);

        //selenium.setSpeed("1500");
        navigationHelper.navigateToSavingsAccountDetailPage(savingsId);
        Assert.assertEquals(selenium.getTable("recentActivityForDetailPage.1.2"),"602.7");

        targetTime = new DateTime(2011,9,1,22,0,0,0);
        dateTimeUpdaterRemoteTestingService.setDateTime(targetTime);

        navigationHelper.navigateToSavingsAccountDetailPage(savingsId);
        navigationHelper.navigateToAdminPage();
        runBatchJobsForSavingsIntPosting();
        selenium.click("logout_link");
        UiTestUtils.sleep(10000);

        navigationHelper.navigateToSavingsAccountDetailPage(savingsId);
        UiTestUtils.sleep(5000);
        Assert.assertEquals(selenium.getTable("recentActivityForDetailPage.1.2"),"1254.1");
    }


    private DepositWithdrawalSavingsParameters makeDefaultDeposit(DateTime date, DepositWithdrawalSavingsParameters depositParams, String savingsId) throws Exception {
        DateTimeUpdaterRemoteTestingService dateTimeUpdaterRemoteTestingService = new DateTimeUpdaterRemoteTestingService(selenium);
        dateTimeUpdaterRemoteTestingService.setDateTime(date);
        DepositWithdrawalSavingsParameters depositParamsReturn = setDepositParams(depositParams, String.valueOf(date.getDayOfMonth()), String.valueOf(date.getMonthOfYear()), String.valueOf(date.getYear()));
        savingsAccountHelper.makeDepositOrWithdrawalOnSavingsAccount(savingsId, depositParams);
        return depositParamsReturn;
    }

    private DepositWithdrawalSavingsParameters setDepositParams(DepositWithdrawalSavingsParameters depositParams,String dd, String mm, String yy){
        depositParams.setTrxnDateMM(dd);
        depositParams.setTrxnDateDD(mm);
        depositParams.setTrxnDateYYYY(yy);
        depositParams.setAmount("100000.0");
        depositParams.setPaymentType(DepositWithdrawalSavingsParameters.CASH);
        depositParams.setTrxnType(DepositWithdrawalSavingsParameters.DEPOSIT);
        return depositParams;
    }

    private void runBatchJobsForSavingsIntPosting() {
        List<String> jobsToRun = new ArrayList<String>();
        jobsToRun.add("SavingsIntPostingTaskJob");
        new BatchJobHelper(selenium).runSomeBatchJobs(jobsToRun);
    }

    private SavingsAccountDetailPage createSavingAccountWithCreatedProduct(String client, String productName, String amount){
        CreateSavingsAccountSearchParameters searchParameters = new CreateSavingsAccountSearchParameters();
        searchParameters.setSearchString(client);
        searchParameters.setSavingsProduct(productName);

        CreateSavingsAccountSubmitParameters submitAccountParameters = new CreateSavingsAccountSubmitParameters();
        submitAccountParameters.setAmount(amount);
        SavingsAccountDetailPage savingsAccountPage = savingsAccountHelper.createSavingsAccount(searchParameters, submitAccountParameters);
        savingsAccountPage.verifyPage();
        savingsAccountPage.verifySavingsAmount(submitAccountParameters.getAmount());
        savingsAccountPage.verifySavingsProduct(searchParameters.getSavingsProduct());
        return savingsAccountPage;
    }
}