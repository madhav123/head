package org.mifos.accounts.loan.business.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mifos.accounts.loan.business.service.validators.InstallmentFormatValidator;
import org.mifos.accounts.loan.business.service.validators.InstallmentRulesValidator;
import org.mifos.accounts.loan.business.service.validators.InstallmentValidationContext;
import org.mifos.accounts.loan.business.service.validators.InstallmentsValidator;
import org.mifos.accounts.loan.business.service.validators.InstallmentsValidatorImpl;
import org.mifos.accounts.loan.business.service.validators.ListOfInstallmentsValidator;
import org.mifos.accounts.loan.util.helpers.RepaymentScheduleInstallment;
import org.mifos.accounts.loan.util.helpers.RepaymentScheduleInstallmentBuilder;
import org.mifos.accounts.productdefinition.business.VariableInstallmentDetailsBO;
import org.mifos.application.master.business.MifosCurrency;
import org.mifos.config.FiscalCalendarRules;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.mifos.framework.util.CollectionUtils.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class InstallmentsValidatorTest {

    private RepaymentScheduleInstallmentBuilder installmentBuilder;

    private MifosCurrency rupeeCurrency;

    private Locale locale;

    @Mock
    private InstallmentFormatValidator installmentFormatValidator;

    @Mock
    private ListOfInstallmentsValidator listOfInstallmentsValidator;

    @Mock
    private InstallmentRulesValidator installmentRulesValidator;

    private InstallmentsValidator installmentsValidator;

    @Before
    public void setUp() throws Exception {
        locale = new Locale("en", "GB");
        installmentBuilder = new RepaymentScheduleInstallmentBuilder(locale);
        rupeeCurrency = new MifosCurrency(Short.valueOf("1"), "Rupee", BigDecimal.valueOf(1), "INR");
        installmentsValidator = new InstallmentsValidatorImpl(installmentFormatValidator, listOfInstallmentsValidator, installmentRulesValidator);
    }

    @Test
    public void validateShouldCallFormatListOfAndRulesValidators() throws Exception {
        RepaymentScheduleInstallment installment1 = installmentBuilder.reset(locale).withInstallment(1).withDueDateValue("01-Nov-2010").build();
        RepaymentScheduleInstallment installment2 = installmentBuilder.reset(locale).withInstallment(2).withDueDateValue("06-Nov-2010").build();
        RepaymentScheduleInstallment installment3 = installmentBuilder.reset(locale).withInstallment(3).withDueDateValue("08-Nov-2010").build();

        List<RepaymentScheduleInstallment> installments = asList(installment1, installment2, installment3);
        installmentsValidator.validate(installments, getValidationContext(null));
        for (RepaymentScheduleInstallment installment : installments) {
            Mockito.verify(installmentFormatValidator).validateDueDateFormat(installment);
            Mockito.verify(installmentFormatValidator).validateTotalAmountFormat(installment);
        }
        Mockito.verify(listOfInstallmentsValidator).validateDuplicateDueDates(installments);
        Mockito.verify(listOfInstallmentsValidator).validateOrderingOfDueDates(installments);

        Mockito.verify(installmentRulesValidator).validateForDisbursementDate(eq(installments), any(Date.class));
        Mockito.verify(installmentRulesValidator).validateForVariableInstallments(eq(installments), any(VariableInstallmentDetailsBO.class));
        Mockito.verify(installmentRulesValidator).validateForHolidays(eq(installments), any(FiscalCalendarRules.class));
    }

    private InstallmentValidationContext getValidationContext(Date disbursementDate) {
        return new InstallmentValidationContext(disbursementDate, new VariableInstallmentDetailsBO(), 
                                    new FiscalCalendarRules());
    }
}
