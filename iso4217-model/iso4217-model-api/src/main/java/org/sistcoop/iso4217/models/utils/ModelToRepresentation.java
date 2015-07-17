package org.sistcoop.iso4217.models.utils;

import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.DenominationModel;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;
import org.sistcoop.iso4217.representations.idm.DenominationRepresentation;

public class ModelToRepresentation {

    public static CurrencyRepresentation toRepresentation(CurrencyModel model) {
        if (model == null)
            return null;

        CurrencyRepresentation rep = new CurrencyRepresentation();

        rep.setEntity(model.getEntity());
        rep.setCurrency(model.getCurrency());
        rep.setAlphabeticCode(model.getAlphabeticCode());
        rep.setNumericCode(model.getNumericCode());
        rep.setMinorUnit(model.getMinorUnit());

        return rep;
    }

    public static DenominationRepresentation toRepresentation(DenominationModel model) {
        if (model == null)
            return null;
        DenominationRepresentation rep = new DenominationRepresentation();
        rep.setValue(model.getValue());

        return rep;
    }

}
