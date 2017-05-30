package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by eline on 08.04.2017.
 */

@Root(strict=false)
public class Symbol {

    @Attribute
    int number;
    @Attribute
    int numberEx;
    @Attribute
    String name;
    @Attribute
    String var;

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getVar() {
        return var;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public int getNumberEx() {
        return numberEx;
    }

    public void setNumberEx(int numberEx) {
        this.numberEx = numberEx;
    }
}
