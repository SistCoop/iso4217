package org.sistcoop.iso4217.representations.idm;

import java.io.Serializable;
import java.math.BigDecimal;

public class DenominationRepresentation implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
