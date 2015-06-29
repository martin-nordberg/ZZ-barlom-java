//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ISqlConstraint;
import org.grestler.domain.sqlmodel.api.elements.ISqlTable;
import org.grestler.domain.sqlmodel.api.elements.ISqlTableColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * A constraint on the columns of a table.
 */
public abstract class SqlConstraint
    extends SqlNamedModelElement
    implements ISqlConstraint {

    /**
     * Constructs a new constraint.
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    SqlConstraint(
        SqlTable parent,
        String name,
        String description,
        List<ISqlTableColumn> constrainedColumns
    ) {
        super( parent, name, description );

        this.constrainedColumns = new ArrayList<>( constrainedColumns );
    }

    @Override
    public List<ISqlTableColumn> getConstrainedColumns() {
        return this.constrainedColumns;
    }

    @Override
    public ISqlTable getParent() {
        return (ISqlTable) super.getParent();
    }

    /**
     * Makes a list from one column
     *
     * @param column the column to put in the list.
     */
    static List<ISqlTableColumn> makeListOfOneColumn( ISqlTableColumn column ) {
        List<ISqlTableColumn> result = new ArrayList<>();
        result.add( column );
        return result;
    }

    /**
     * Makes a list from two columns
     *
     * @param column1 the first column to put in the list
     * @param column2 the second column to put in the list
     */
    static List<ISqlTableColumn> makeListOfTwoColumns( ISqlTableColumn column1, ISqlTableColumn column2 ) {
        List<ISqlTableColumn> result = new ArrayList<>();
        result.add( column1 );
        result.add( column2 );
        return result;
    }

    private final List<ISqlTableColumn> constrainedColumns;

}
