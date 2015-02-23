//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions;

/**
 * Enumeration of possible transaction writeability values..
 */
public enum ETransactionWriteability {

    /**
     * This transaction and any nested transactions cannot make changes.
     */
    READ_ONLY,

    /**
     * This transaction can make changes.
     */
    READ_WRITE,

    /**
     * This transaction cannot make changes. However, a nested transaction can make changes that will be committed by
     * this outer transaction.
     */
    READ_WITH_NESTED_WRITES;

    /**
     * @return whether this is the READ_ONLY instance of the enumeration.
     */
    boolean isReadOnly() {
        return this == ETransactionWriteability.READ_ONLY;
    }

}
