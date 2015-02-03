package org.grestler.dbutilities.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper utility class parses the named parameters from a SQL query.
 */
final class SqlNamedParameterParser {

    private SqlNamedParameterParser() {
    }

    /**
     * Parses a SQL query with named parameters. Returns a revised query ready for JDBC plus the arguments in order of
     * substitution.
     *
     * @param sqlQuery the SQL query with named parameters in Mustache format {{param}}.
     * @param args     the named argument values to substitute into the query in order.
     *
     * @return the revised SQL and the arguments in an ordered list.
     */
    static ParseResult parseSqlParameters( String sqlQuery, Map<String, Object> args ) {

        StringBuilder sql = new StringBuilder();
        List<Object> arguments = new ArrayList<>();

        Matcher matcher = SqlNamedParameterParser.SQL_PARAM_REGEX.matcher( sqlQuery );

        // Loop through the named arguments in the query.
        int lastIndex = 0;
        while ( matcher.find( lastIndex ) ) {
            // Replace named parameter placeholders by "?" ...
            sql.append( sqlQuery.substring( lastIndex, matcher.start( 0 ) ) );
            sql.append( "?" );
            lastIndex = matcher.end( 0 );

            // List out the arguments in the order seen.
            arguments.add( args.get( matcher.group( 1 ) ) );
        }
        sql.append( sqlQuery.substring( lastIndex ) );

        // Package the two results.
        return new ParseResult( sql.toString(), arguments );

    }

    /** Regeular expression for matching SQL statement named parameters. */
    private static final Pattern SQL_PARAM_REGEX = Pattern.compile( "\\{\\{([a-zA-Z0-9]*)\\}\\}" );

    /**
     * Pair of results from a parse.
     */
    static final class ParseResult {

        @SuppressWarnings( "AssignmentToCollectionOrArrayFieldFromParameter" )
        ParseResult( String sql, List<Object> arguments ) {
            this.sql = sql;
            this.arguments = arguments;
        }

        public final List<Object> arguments;

        public final String sql;

    }

}
