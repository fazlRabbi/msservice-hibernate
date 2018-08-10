package no.hib.logic.interfaces;

import no.hib.models.AdminUser;
import no.hib.models.BankIdUser;

public interface LoginService {

    boolean validUser(BankIdUser bankIdUser);
    boolean validUser(AdminUser adminUser);
}
