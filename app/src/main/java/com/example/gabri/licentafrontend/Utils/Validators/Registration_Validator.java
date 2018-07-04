package com.example.gabri.licentafrontend.Utils.Validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gabri on 7/4/2018.
 */

public class Registration_Validator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_REGEX =
            Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE);
    Matcher matcher_email;
    Matcher matcher_phone;

    public void validator(String first_name, String last_name,
                          String email, String phone, String password) throws Exception{
        String error = "";

        if(first_name.equals(""))
            error += "Completați câmpul corespunzător numelui \n";

        if(last_name.equals(""))
            error += "Completați câmpul corespunzător prenumelui \n";

        if(email.equals(""))
            error += "Completați câmpul corespunzător adresei de email \n";

        if(password.equals(""))
            error += "Completați câmpul corespunzător parolei \n";

        if(phone.equals(""))
            error += "Completați câmpul corespunzător numărului de telefon \n";
        else {
            this.matcher_phone = VALID_PHONE_REGEX.matcher(phone);
            if (matcher_phone.find() == false)
                error += "Numărul de telefon este invalid \n";
        }
        if(email.equals(""))
            error += "Completați câmpul corespunzător adresei de email \n";
         else {
            this.matcher_email = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            if (matcher_email.find() == false)
                error += "Adresa de email nu este validă \n";
        }

        if(!error.equals(""))
            throw new Exception(error);
    }
}
