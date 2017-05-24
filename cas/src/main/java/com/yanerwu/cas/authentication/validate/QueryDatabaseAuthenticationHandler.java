package com.yanerwu.cas.authentication.validate;

import com.yanerwu.cas.authentication.utils.MD5Code;
import com.yanerwu.cas.authentication.yanzhengma.UsernamePasswordCaptchaCredential;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;

/**
 * @Author Zuz
 * @Date 2017/5/21 13:21
 * @Description 数据库验证
 */
public class QueryDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

    private final String key = "7jKXi6%V";
    @NotNull
    private String sql;

    /**
     * {@inheritDoc}
     */
    @Override
    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCaptchaCredential credential)
            throws GeneralSecurityException, PreventedException {

        final String username = credential.getUsername();
//	        final String serviceBrand = credential.getServiceBrand();
//	        final String encryptedPassword = this.getPasswordEncoder().encode(credential.getPassword());
        final String encryptedPassword = MD5Code.instance().getMD5ofStr(credential.getPassword());
//        final String encryptedPassword = getMd5Pwd(credential.getUsername(), credential.getPassword());
        try {
//	            final String dbPassword = getJdbcTemplate().queryForObject(this.sql, String.class, username,serviceBrand);
            final String dbPassword = getJdbcTemplate().queryForObject(this.sql, String.class, username);
            if (!dbPassword.equals(encryptedPassword)) {
                throw new FailedLoginException("Password does not match value on record.");
            }
        } catch (final IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                throw new AccountNotFoundException(username + " not found with SQL query");
            } else {
                throw new FailedLoginException("Multiple records found for " + username);
            }
        } catch (final DataAccessException e) {
            throw new PreventedException("SQL exception while executing query for " + username, e);
        }

        return createHandlerResult(credential, this.principalFactory.createPrincipal(username), null);
    }

    /**
     * @param sql The sql to set.
     */
    public void setSql(final String sql) {
        this.sql = sql;
    }

}
