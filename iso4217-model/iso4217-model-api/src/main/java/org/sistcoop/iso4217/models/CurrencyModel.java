package org.sistcoop.iso4217.models;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyModel extends Model {

    String getId();

    String getEntity();

    void setEntity(String entity);

    String getCurrency();

    void setCurrency(String currency);

    String getAlphabeticCode();

    String getNumericCode();

    int getMinorUnit();

    void setMinorUnit(int minorUnit);

    DenominationModel addDenomination(BigDecimal value);

    boolean removeDenomination(DenominationModel denominationModel);

    List<DenominationModel> getDenominations();

}