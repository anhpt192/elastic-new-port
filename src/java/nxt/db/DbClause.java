package nxt.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DbClause {

    private final String clause;

    protected DbClause(String clause) {
        this.clause = clause;
    }

    final String getClause() {
        return clause;
    }

    protected abstract int set(PreparedStatement pstmt, int index) throws SQLException;

    public static final DbClause EMPTY_CLAUSE = new FixedClause(" TRUE ");

    public static final class FixedClause extends DbClause {

        public FixedClause(String clause) {
            super(clause);
        }

        @Override
        protected int set(PreparedStatement pstmt, int index) throws SQLException {
            return index;
        }

    }

    public static final class StringClause extends DbClause {

        private final String value;

        public StringClause(String columnName, String value) {
            super(" " + columnName + " = ? ");
            this.value = value;
        }

        protected int set(PreparedStatement pstmt, int index) throws SQLException {
            pstmt.setString(index, value);
            return index + 1;
        }

    }

    public static final class LongClause extends DbClause {

        private final long value;

        public LongClause(String columnName, long value) {
            super(" " + columnName + " = ? ");
            this.value = value;
        }

        protected int set(PreparedStatement pstmt, int index) throws SQLException {
            pstmt.setLong(index, value);
            return index + 1;
        }

    }

    public static final class IntClause extends DbClause {

        private final int value;

        public IntClause(String columnName, int value) {
            super(" " + columnName + " = ? ");
            this.value = value;
        }

        protected int set(PreparedStatement pstmt, int index) throws SQLException {
            pstmt.setInt(index, value);
            return index + 1;
        }

    }

}
