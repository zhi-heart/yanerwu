package com.yanerwu.cas.authentication.validate;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractJdbcUsernamePasswordAuthenticationHandler
		extends AbstractUsernamePasswordAuthenticationHandler {

	@NotNull
    private JdbcTemplate jdbcTemplate;

    @NotNull
    private DataSource dataSource;

    /**
     * Method to set the datasource and generate a JdbcTemplate.
     *
     * @param dataSource the datasource to use.
     */
    public final void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    /**
     * Method to return the jdbcTemplate.
     *
     * @return a fully created JdbcTemplate.
     */
    protected final JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    protected final DataSource getDataSource() {
        return this.dataSource;
    }

}
