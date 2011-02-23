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

package org.mifos.dto.domain;

import java.util.List;

public class SavingsAccountCreationDto {

    private final Integer productId;
    private final Integer customerId;
    private final Short accountState;
    private final String recommendedOrMandatoryAmount;
    private final List<CustomFieldDto> customFields;

    public SavingsAccountCreationDto(Integer productId, Integer customerId, Short accountState,
            String recommendedOrMandatoryAmount, List<CustomFieldDto> customFields) {
        this.productId = productId;
        this.customerId = customerId;
        this.accountState = accountState;
        this.recommendedOrMandatoryAmount = recommendedOrMandatoryAmount;
        this.customFields = customFields;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public Short getAccountState() {
        return this.accountState;
    }

    public String getRecommendedOrMandatoryAmount() {
        return this.recommendedOrMandatoryAmount;
    }

    public List<CustomFieldDto> getCustomFields() {
        return this.customFields;
    }

}