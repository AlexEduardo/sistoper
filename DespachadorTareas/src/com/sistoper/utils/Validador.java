/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisoper.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author simon
 */
public class Validador {

    public static boolean isPhoneNumber(String valor) {
        return validar("[\\d{2,3}[- ]*]*\\d{6,10}", valor);
    }

    public static boolean isEmail(String valor) {
        return validar("^[\\w-\\.]+\\@[\\w\\.-]+\\.[a-z]{2,4}$", valor);
    }

    public static boolean isNumber(String valor) {
        return validar("[0-9]*", valor);
    }

    public static boolean isDate(String valor) {
        return validar("^[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}$", valor);
    }

    public static boolean isCedula(String valor) {
        return validar("^[0-9]\\.[0-9]{3}\\.[0-9]{3}-[0-9]$", valor);
    }

    public static boolean validar(String expReg, String valor) {
        Pattern patron = Pattern.compile(expReg);
        Matcher encajador = patron.matcher(valor);
        return encajador.matches();
    }
}