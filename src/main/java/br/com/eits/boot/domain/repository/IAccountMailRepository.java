package br.com.eits.boot.domain.repository;
import java.util.concurrent.Future;

import br.com.eits.boot.domain.entity.account.Pessoa;

/**
 *
 */
public interface IAccountMailRepository
{
    /*-------------------------------------------------------------------
     *                          BEHAVIORS
     *-------------------------------------------------------------------*/
    /**
     * @param user
     */
    Future<Void> sendNewUserAccount( Pessoa user );

    /**
     *
     */
    Future<Void> sendPasswordReset( Pessoa user );

    /**
     *
     */
    Future<Void> sendPasswordResetNotice( Pessoa user );
}